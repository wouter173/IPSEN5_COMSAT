package nl.codefusion.comsat.config;

import lombok.Getter;


@Getter
public enum Permission {
    CREATE_BATCH(1),
    READ_BATCH(2),
    DELETE_BATCH(4),
    UPDATE_BATCH(8),
    SEND_BATCH(16),

    CREATE_CONTACT(32),
    READ_CONTACT(64),
    READ_CONTACT_DETAILS(128),
    UPDATE_CONTACT(256),
    DELETE_CONTACT(512),

    CREATE_TEMPLATE(1024),
    READ_TEMPLATE(2048),
    UPDATE_TEMPLATE(4096),
    DELETE_TEMPLATE(8192),
    SUGGEST_TEMPLATE(16384),

    CREATE_USER(32768),
    READ_USER(65536),
    UPDATE_USER(131072),
    DELETE_USER(262144),

    READ_REPORT(524288),
    EXPORT_REPORT(1048576);

    private final int value;

    Permission(int value) {
        this.value = value;
    }
}


