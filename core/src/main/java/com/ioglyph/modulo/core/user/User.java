package com.ioglyph.modulo.core.user;

import java.time.OffsetDateTime;
import java.util.UUID;

public class User {
    private UUID id;
    private Username username;
    private String email;
    private OffsetDateTime created;
    private OffsetDateTime updated;

    private boolean visible = false;
    private OffsetDateTime deleted;

    public User(UUID id, String name, String email){
        this(id, name, email, null, null, true, null);
    }

    public User(UUID id, String name, String email, OffsetDateTime created, OffsetDateTime updated, boolean visible, OffsetDateTime deleted){
        this.id = id;
        this.username = new Username(name);
        this.email = email;
        this.created = created;
        this.updated = updated;
        this.visible = visible;
        this.deleted = deleted;
    }

    public void delete(){
        this.visible = false;
        this.deleted = OffsetDateTime.now();
    }

    public UUID id(){
        return this.id;
    }

    public Username username(){
        return this.username;
    }

    public String email(){
        return this.email;
    }

    public OffsetDateTime created(){
        return this.created;
    }

    public OffsetDateTime updated(){
        return this.updated;
    }

    public boolean visible(){
        return this.visible;
    }

    public OffsetDateTime deleted(){
        return this.deleted;
    }
}
