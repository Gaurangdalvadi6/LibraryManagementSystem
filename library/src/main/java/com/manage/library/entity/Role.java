package com.manage.library.entity;

// RoleEnum.java
public enum Role {

    ADMIN("ADMIN"),
    LIBRARIAN("LIBRARIAN"),
    BORROWER("BORROWER");

    private final String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}