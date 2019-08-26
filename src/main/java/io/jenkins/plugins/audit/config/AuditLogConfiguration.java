package io.jenkins.plugins.audit.config;

import hudson.EnvVars;
import hudson.Extension;
import hudson.util.ListBoxModel;
import jenkins.model.GlobalConfiguration;
import org.apache.logging.log4j.core.LoggerContext;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

import java.nio.file.Paths;
import java.util.Objects;

@Extension
public class AuditLogConfiguration extends GlobalConfiguration {
    private String logDestination;
    private String appenderType;
    private String syslogHost;
    private int syslogPort;
    private int enterpriseNumber;

    public AuditLogConfiguration() {
        load();
        reloadLogger();
    }

    public String getAppenderType() {
        return appenderType;
    }

    @DataBoundSetter
    public void setAppenderType(String appenderType) {
        this.appenderType = appenderType;
        save();
        reloadLogger();
    }

    public String getLogDestination() {
        String jenkinsHome = EnvVars.masterEnvVars.get("JENKINS_HOME");

        if(jenkinsHome == null){
            jenkinsHome = System.getProperty("JENKINS_HOME");
        }

        if(jenkinsHome == null){
            jenkinsHome = Paths.get(".").toAbsolutePath().normalize().toString();
        }

        this.logDestination = jenkinsHome + "/logs/audit.log";
        return this.logDestination;
    }

    @DataBoundSetter
    public void setLogDestination(String logDestination) {
        if(this.logDestination != null && !logDestination.equals("")){
            this.logDestination = logDestination;
        }
        save();
        reloadLogger();
    }

    public String getSyslogHost() {
        return syslogHost;
    }

    @DataBoundSetter
    public void setSyslogHost(String syslogHost) {
        if(this.syslogHost != null && !syslogHost.equals("")){
            this.syslogHost = syslogHost;
        }
        save();
        reloadLogger();
    }

    public int getSyslogPort() {
        return syslogPort;
    }

    @DataBoundSetter
    public void setSyslogPort(int syslogPort) {
        if(this.syslogPort != 0){
            this.syslogPort = syslogPort;
        }
        save();
        reloadLogger();
    }

    public int getEnterpriseNumber() {
        return enterpriseNumber;
    }

    @DataBoundSetter
    public void setEnterpiseNumber(int enterpriseNumber) {
        if(this.enterpriseNumber != 0){
            this.enterpriseNumber = enterpriseNumber;
        }
        save();
        reloadLogger();

    }

    private void reloadLogger() {
        if(this.logDestination != null && !this.logDestination.equals("")){
            System.setProperty("auditFileName", this.logDestination);
        } else {
            System.clearProperty("auditFileName");
        }

        if(this.appenderType != null){
            System.setProperty("appenderType", this.appenderType);
        } else {
            System.clearProperty("appenderType");
        }

        if(this.syslogHost != null && !this.syslogHost.equals("")){
            System.setProperty("syslogHost", this.syslogHost);
        } else {
            System.clearProperty("syslogHost");
        }

        if(this.syslogPort != 0){
            System.setProperty("syslogPort", String.valueOf(this.syslogPort));
        } else {
            System.clearProperty("syslogPort");
        }

        if(this.enterpriseNumber != 0){
            System.setProperty("enterpriseNumber", String.valueOf(this.enterpriseNumber));
        } else {
            System.clearProperty("enterpriseNumber");
        }

        LoggerContext.getContext(false).reconfigure();
    }

    public ListBoxModel doFillAppenderTypeItems() {
        ListBoxModel items = new ListBoxModel();
        items.add("jsonLayout");
        items.add("syslogLayout");
        return items;
    }
}
