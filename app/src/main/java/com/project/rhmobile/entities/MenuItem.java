package com.project.rhmobile.entities;

public final class MenuItem {

    private final Service id;
    private final int image;
    private final String content;

    public MenuItem(Service id, String content, int image) {
        this.id = id;
        this.content = content;
        this.image = image;
    }

    public Service getId() {
        return id;
    }

    public int getImage() {
        return image;
    }

    public String getContent() {
        return content;
    }
}
