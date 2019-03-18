package io.jenkins.plugins.audit.listeners;

import hudson.Extension;
import hudson.ExtensionList;
import hudson.model.Item;
import hudson.model.User;
import io.jenkins.plugins.audit.event.*;
import org.apache.logging.log4j.audit.LogEventFactory;

import javax.annotation.Nonnull;
import java.util.Date;

/**
 * Listener notified of any CRUD operations on items.
 */
@Extension
public class ItemChangeListener extends hudson.model.listeners.ItemListener{

    /**
     * Fired when a new job is created and added to Jenkins, before the initial configuration page is provided.
     *
     * @param item is a job that is created
     */
    @Override
    public void onCreated(@Nonnull Item item) {
        CreateItem itemCreateEvent = LogEventFactory.getEvent(CreateItem.class);

        itemCreateEvent.setItemName(item.getName());
        itemCreateEvent.setItemUri(item.getUrl());
        itemCreateEvent.setTimestamp(new Date().toString());
        User user = User.current();
        if(user != null)
            itemCreateEvent.setUserId(user.getId());
        else
            itemCreateEvent.setUserId(null);

        itemCreateEvent.logEvent();

    }

    /**
     * Fired when a new job is created by copying from an existing job
     *
     * @param src is the source item that the new one was copied from.
     * @param item is the newly created item.
     */
    @Override
    public void onCopied(Item src, Item item) {
        CopyItem itemCopyEvent = LogEventFactory.getEvent(CopyItem.class);

        itemCopyEvent.setItemName(item.getName());
        itemCopyEvent.setItemUri(item.getUrl());
        itemCopyEvent.setTimestamp(new Date().toString());
        User user = User.current();
        if(user != null)
            itemCopyEvent.setUserId(user.getId());
        else
            itemCopyEvent.setUserId(null);

        itemCopyEvent.logEvent();

    }

    /**
     * Fired right before a job is going to be deleted
     *
     * @param item is item to be deleted.
     */
    @Override
    public void onDeleted(Item item) {
        DeleteItem itemDeleteEvent = LogEventFactory.getEvent(DeleteItem.class);

        itemDeleteEvent.setItemName(item.getName());
        itemDeleteEvent.setItemUri(item.getUrl());
        itemDeleteEvent.setTimestamp(new Date().toString());
        User user = User.current();
        if(user != null)
            itemDeleteEvent.setUserId(user.getId());
        else
            itemDeleteEvent.setUserId(null);

        itemDeleteEvent.logEvent();

    }

    /**
     * Fired after a job is renamed
     *
     * @param item is job being renamed.
     * @param oldName is old name of the job.
     * @param newName is new name of the job.
     */
    @Override
    public void onRenamed(Item item, String oldName, String newName) {
        RenameItem itemRenameEvent = LogEventFactory.getEvent(RenameItem.class);

        itemRenameEvent.setItemName(newName);
        itemRenameEvent.setItemUri(item.getUrl());
        itemRenameEvent.setTimestamp(new Date().toString());
        User user = User.current();
        if(user != null)
            itemRenameEvent.setUserId(user.getId());
        else
            itemRenameEvent.setUserId(null);

        itemRenameEvent.logEvent();
    }

    /**
     * Fired when a job has its configuration updated.
     *
     * @param item is job being updated.
     */
    @Override
    public void onUpdated(Item item) {
        UpdateItem itemUpdateEvent = LogEventFactory.getEvent(UpdateItem.class);

        itemUpdateEvent.setItemName(item.getName());
        itemUpdateEvent.setItemUri(item.getUrl());
        itemUpdateEvent.setTimestamp(new Date().toString());
        User user = User.current();
        if(user != null)
            itemUpdateEvent.setUserId(user.getId());
        else
            itemUpdateEvent.setUserId(null);

        itemUpdateEvent.logEvent();
    }

    /**
     * Returns a registered {@link ItemChangeListener} instance.
     */
    public static ExtensionList<ItemChangeListener> getInstance() {
        return ExtensionList.lookup(ItemChangeListener.class);
    }

}
