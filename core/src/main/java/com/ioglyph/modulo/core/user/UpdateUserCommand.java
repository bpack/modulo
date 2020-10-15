package com.ioglyph.modulo.core.user;

import java.time.OffsetDateTime;

/**
 * Command class for updating an existing user.
 */
public class UpdateUserCommand {
    private final User user;
    private final String email;
    private final String ip;

    public UpdateUserCommand(User user, String email, String ip){
        this.user = user;
        this.email = email;
        this.ip = ip;
    }

    /**
     * Modifies the IP address and/or email address for the user and
     * returns an updated user object that can then be persisted.
     */
    public User execute(){
        String email = (null != this.email) ? this.email : user.email();
        String ip = (null != this.ip) ? this.ip : user.lastIp();

        return new User(user.id(), user.username().value(), email, ip, user.created(), OffsetDateTime.now(), true, null);
    }
}
