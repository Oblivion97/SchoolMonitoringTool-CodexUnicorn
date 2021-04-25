/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.analyseAndTest;

import java.util.List;

/**
 * Created by Mir Ferdous on 03/11/2017.
 */

public class MessageEvent {

    public int messageID;
    private String messageStatus;
    public String displayMsg;
    public String message;
    private String btnLabel;
    private boolean btnEnabled;
    private long bytes;
    private String whichSyncComplete;
    public String lastSyncTime;
    Object object1;
    Object object2;
    public boolean success;//sync or something success test
    public List<Object> objectList;

    public MessageEvent(Object object1) {
        this.object1 = object1;
    }

    public MessageEvent(Object object1, Object object2) {
        this.object1 = object1;
        this.object2 = object2;
    }

    public MessageEvent(String message) {
        this.message = message;
    }

    public MessageEvent() {

    }


    public MessageEvent(int messageID) {
        this.messageID = messageID;
    }

    public MessageEvent(int messageID, String message, boolean success, boolean btnEnabled, String lastSyncTime) {
        this.messageID = messageID;
        this.message = message;
        this.success = success;
        this.btnEnabled = btnEnabled;
        this.lastSyncTime = lastSyncTime;
    }

    public boolean isBtnEnabled() {
        return btnEnabled;
    }

    public MessageEvent(boolean success) {
        this.success = success;
    }

    public void setBtnEnabled(boolean btnEnabled) {
        this.btnEnabled = btnEnabled;
    }

    public long getBytes() {
        return bytes;
    }

    public String getBtnLabel() {
        return btnLabel;
    }

    public String getLastSyncTime() {
        return lastSyncTime;
    }

    public void setLastSyncTime(String lastSyncTime) {
        this.lastSyncTime = lastSyncTime;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setBtnLabel(String btnLabel) {
        this.btnLabel = btnLabel;
    }

    public int getMessageID() {
        return messageID;
    }

    public void setMessageID(int messageID) {
        this.messageID = messageID;
    }

    public String getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(String messageStatus) {
        this.messageStatus = messageStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObject1() {
        return object1;
    }

    public void setObject1(Object object1) {
        this.object1 = object1;
    }

    public Object getObject2() {
        return object2;
    }

    public void setObject2(Object object2) {
        this.object2 = object2;
    }

    public void setBytes(long bytes) {
        this.bytes = this.bytes + bytes;
    }

    public String getWhichSyncComplete() {
        return whichSyncComplete;
    }

    public void setWhichSyncComplete(String whichSyncComplete) {
        this.whichSyncComplete = whichSyncComplete;
    }

    @Override
    public String toString() {
        return "MessageEvent{" +
                "messageID=" + messageID +
                ", messageStatus='" + messageStatus + '\'' +
                ", displayMsg='" + displayMsg + '\'' +
                ", message='" + message + '\'' +
                ", btnLabel='" + btnLabel + '\'' +
                ", btnEnabled=" + btnEnabled +
                ", bytes=" + bytes +
                ", whichSyncComplete='" + whichSyncComplete + '\'' +
                ", lastSyncTime='" + lastSyncTime + '\'' +
                ", object1=" + object1 +
                ", object2=" + object2 +
                ", success=" + success +
                ", objectList=" + objectList +
                '}';
    }
}