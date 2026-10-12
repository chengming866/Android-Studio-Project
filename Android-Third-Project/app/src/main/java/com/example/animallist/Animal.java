package com.example.animallist;

public final class Animal {
    private final long id;
    private final String name;
    private final int imageResId;

    public Animal(long id, String name, int imageResId) {
        this.id = id;
        this.name = name;
        this.imageResId = imageResId;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getImageResId() {
        return imageResId;
    }
}

