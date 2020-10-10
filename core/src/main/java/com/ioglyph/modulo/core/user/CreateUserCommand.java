package com.ioglyph.modulo.core.user;

import java.time.OffsetDateTime;
import java.util.UUID;

public class CreateUserCommand {
    private String username;
    private String email;

    public CreateUserCommand(String username, String email){
        this.username = username;
        this.email = email;
    }

    public User execute(){
        OffsetDateTime now = OffsetDateTime.now();
        User user = new User(UUID.randomUUID(), username, email, now, now, true, null);
        return user;
    }
}
