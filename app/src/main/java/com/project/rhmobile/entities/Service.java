package com.project.rhmobile.entities;

public class Service {

    private final ServiceType type;
    private final String name;
    private final String address;
    private final String phone;
    private final String imageUrl;
    private final float latitude;
    private final float longitude;

    public Service(ServiceType type, String name, String address, String phone, String imageUrl,
                   float latitude, float longitude) {
        this.type = type;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.imageUrl = imageUrl;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public ServiceType getType() {
        return type;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }
}
