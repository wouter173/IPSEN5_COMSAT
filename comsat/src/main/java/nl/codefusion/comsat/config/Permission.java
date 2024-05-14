package nl.codefusion.comsat.config;

import lombok.Getter;

public enum Permission {
    EDIT(1),
    READ_TEMPLATE(2),
    EDIT_TEMPLATE(4),
    MANAGE_USERS(8);;

    @Getter
    private final int value;

    Permission(int value) {
        this.value = value;
    }


}


//TODO: Add more permissions


