package com.ioglyph.modulo.core.user;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Command class for adding a new user to the system.
 */
public class CreateUserCommand {
    private final String username;
    private final String email;
    private final String ip;

    public CreateUserCommand(String username, String email, String ip){
        this.username = username;
        this.email = email;
        this.ip = ip;
    }

    /**
     * Returns a new user that can then be persisted.
     */
    public User execute(){
        OffsetDateTime now = OffsetDateTime.now();
        return new User(UUID.randomUUID(), username, email, ip, now, now, true, null);
    }
}
