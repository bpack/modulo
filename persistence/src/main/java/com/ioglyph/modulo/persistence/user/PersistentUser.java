package com.ioglyph.modulo.persistence.user;

import java.time.OffsetDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.UniqueConstraint;

import com.ioglyph.modulo.core.user.User;
import com.ioglyph.modulo.core.user.Username;

@Entity(name = "app_user")
public class PersistentUser {

    @Id
    private UUID id;

    @Column(unique = true)
    private String username;

    private String email;

    private String ip;

    @Column(name = "created", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime created;

    @Column(name = "updated", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime updated;

    boolean visible;

    @Column(name = "deleted", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime deleted;

    public PersistentUser() {}

    public PersistentUser(final UUID id, final String username, final String email,
            final String ip, final OffsetDateTime created, final OffsetDateTime updated,
            final boolean visible, final OffsetDateTime deleted) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.ip = ip;
        this.created = created;
        this.updated = updated;
        this.visible = visible;
        this.deleted = deleted;
    }

    User toUser(){
        return new User(
                this.id,
                this.username,
                this.email,
                this.ip,
                this.created,
                this.updated,
                this.visible,
                this.deleted
        );
    }

    public static PersistentUser from(User user){
        return new PersistentUser(
                user.id(),
                user.username().value(),
                user.email(),
                user.lastIp(),
                user.created(),
                user.updated(),
                user.visible(),
                user.deleted());
    }
}
