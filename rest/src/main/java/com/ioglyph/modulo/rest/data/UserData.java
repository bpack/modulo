package com.ioglyph.modulo.rest.data;

import com.ioglyph.modulo.core.user.User;

import java.time.OffsetDateTime;
import java.util.UUID;

public class UserData {
    public UUID id;
    public String username;
    public String email;
    public OffsetDateTime created;
    public OffsetDateTime updated;

    public UserData() {}

    public UserData(User user){
        this.id = user.id();
        this.username = user.username().value();
        this.email = user.email();
        this.created = user.created();
        this.updated = user.created();
    }
}
