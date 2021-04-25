/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.common;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mir Ferdous on 10/21/2017.
 */

public class InfoMdl implements Parcelable {
    private String title,schName,eduType,dateRep,syncStatus;
    private int syncIMG,icon;

    public String getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getSyncIMG() {
        return syncIMG;
    }

    public void setSyncIMG(int syncIMG) {
        this.syncIMG = syncIMG;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSchName() {
        return schName;
    }

    public void setSchName(String schName) {
        this.schName = schName;
    }

    public String getEduType() {
        return eduType;
    }

    public void setEduType(String eduType) {
        this.eduType = eduType;
    }

    public String getDateRep() {
        return dateRep;
    }

    public void setDateRep(String dateRep) {
        this.dateRep = dateRep;
    }

    @Override
    public String toString() {
        return "InfoMdl{" +
                "title='" + title + '\'' +
                ", schName='" + schName + '\'' +
                ", eduType='" + eduType + '\'' +
                ", dateRep='" + dateRep + '\'' +
                '}';
    }

    //================================= parcel ===================================================================

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.schName);
        dest.writeString(this.eduType);
        dest.writeString(this.dateRep);
        dest.writeInt(this.syncIMG);
        dest.writeInt(this.icon);
    }

    public InfoMdl() {
    }

    protected InfoMdl(Parcel in) {
        this.title = in.readString();
        this.schName = in.readString();
        this.eduType = in.readString();
        this.dateRep = in.readString();
        this.syncIMG = in.readInt();
        this.icon = in.readInt();
    }

    public static final Parcelable.Creator<InfoMdl> CREATOR = new Parcelable.Creator<InfoMdl>() {
        @Override
        public InfoMdl createFromParcel(Parcel source) {
            return new InfoMdl(source);
        }

        @Override
        public InfoMdl[] newArray(int size) {
            return new InfoMdl[size];
        }
    };
}
