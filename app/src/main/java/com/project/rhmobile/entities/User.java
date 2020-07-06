package com.project.rhmobile.entities;

import android.os.Parcel;
import android.os.Parcelable;

public final class User implements Parcelable {

    private final int id;
    private final String lastName;
    private final String firstName;
    private final String email;
    private final String password;
    private final String phone;

    public User(int id, String lastName, String firstName, String email, String password, String phone) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.id = 0;
        this.lastName = "";
        this.firstName = "";
        this.phone = "";
    }

    public int getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.lastName);
        dest.writeString(this.firstName);
        dest.writeString(this.email);
        dest.writeString(this.password);
        dest.writeString(this.phone);
    }

    protected User(Parcel in) {
        this.id = in.readInt();
        this.lastName = in.readString();
        this.firstName = in.readString();
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
