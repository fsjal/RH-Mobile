package com.project.rhmobile.entities;

import android.os.Parcel;
import android.os.Parcelable;

public final class MenuItem implements Parcelable {

    private final Service service;
    private final int image;
    private final String content;

    public MenuItem(Service service, String content, int image) {
        this.service = service;
        this.content = content;
        this.image = image;
    }

    public Service getService() {
        return service;
    }

    public int getImage() {
        return image;
    }

    public String getContent() {
        return content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.service == null ? -1 : this.service.ordinal());
        dest.writeInt(this.image);
        dest.writeString(this.content);
    }

    protected MenuItem(Parcel in) {
        int tmpService = in.readInt();
        this.service = tmpService == -1 ? null : Service.values()[tmpService];
        this.image = in.readInt();
        this.content = in.readString();
    }

    public static final Parcelable.Creator<MenuItem> CREATOR = new Parcelable.Creator<MenuItem>() {
        @Override
        public MenuItem createFromParcel(Parcel source) {
            return new MenuItem(source);
        }

        @Override
        public MenuItem[] newArray(int size) {
            return new MenuItem[size];
        }
    };
}
