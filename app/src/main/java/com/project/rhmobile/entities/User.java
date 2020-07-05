package com.project.rhmobile.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    private int id;
    private String name;
    private String prename;
    private String email;
    private String password;
    private String phone;

    public User(int id, String name, String prename, String email, String password, String phone) {
        this.id = id;
        this.name = name;
        this.prename = prename;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public User(String name, String prename, String email, String password, String phone) {
        this.name = name;
        this.prename = prename;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public User(String name, String prename, String email, String password) {
        this.name = name;
        this.prename = prename;
        this.email = email;
        this.password = password;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrename() {
        return prename;
    }

    public void setPrename(String prename) {
        this.prename = prename;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.prename);
        dest.writeString(this.email);
        dest.writeString(this.password);
        dest.writeString(this.phone);
    }

    protected User(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.prename = in.readString();
        this.email = in.readString();
        this.password = in.readString();
        this.phone = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
