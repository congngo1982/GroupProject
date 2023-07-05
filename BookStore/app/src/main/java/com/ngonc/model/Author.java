package com.ngonc.model;

public class Author {
    private int id;
    private String name;
    private String gender;
    private String description;
    private String birthday;

    public Author() {
    }

    public Author(int id, String name, String gender, String description, String birthday) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.description = description;
        this.birthday = birthday;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String toString() {
        return id + " - " + name + " - " + gender + " - " + birthday + " - " + description;
    }
}
