package com.ioglyph.modulo.core.user;

import java.time.OffsetDateTime;
import java.util.UUID;

public class CreateUserCommand {
    private String username;
    private String email;
    private String ip;

    public CreateUserCommand(String username, String email, String ip){
        this.username = username;
        this.email = email;
        this.ip = ip;
    }

    public User execute(){
        OffsetDateTime now = OffsetDateTime.now();
        return new User(UUID.randomUUID(), username, email, ip, now, now, true, null);
    }
}
