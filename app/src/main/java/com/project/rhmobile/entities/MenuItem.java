package com.project.rhmobile.entities;

import android.os.Parcel;
import android.os.Parcelable;

public final class MenuItem implements Parcelable {

    private final ServiceType serviceType;
    private final int image;
    private final String content;
    private final String title;
    private final String serverParam;

    public MenuItem(ServiceType serviceType, String title, int image, String content, String serverParam) {
        this.serviceType = serviceType;
        this.image = image;
        this.content = content;
        this.title = title;
        this.serverParam = serverParam;
    }

    public String getServerParam() {
        return serverParam;
    }

    public String getTitle() {
        return title;
    }

    public ServiceType getServiceType() {
        return serviceType;
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
        dest.writeInt(this.serviceType == null ? -1 : this.serviceType.ordinal());
        dest.writeInt(this.image);
        dest.writeString(this.content);
        dest.writeString(this.title);
        dest.writeString(this.serverParam);
    }

    protected MenuItem(Parcel in) {
        int tmpServiceType = in.readInt();
        this.serviceType = tmpServiceType == -1 ? null : ServiceType.values()[tmpServiceType];
        this.image = in.readInt();
        this.content = in.readString();
        this.title = in.readString();
        this.serverParam = in.readString();
    }

    public static final Creator<MenuItem> CREATOR = new Creator<MenuItem>() {
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
