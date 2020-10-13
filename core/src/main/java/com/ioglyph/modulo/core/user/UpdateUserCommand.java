package com.ioglyph.modulo.core.user;

import java.time.OffsetDateTime;

public class UpdateUserCommand {
    private User user;
    private String email;
    private String ip;

    public UpdateUserCommand(User user, String email, String ip){
        this.user = user;
        this.email = email;
        this.ip = ip;
    }

    public User execute(){
        String email = (null != this.email) ? this.email : user.email();
        String ip = (null != this.ip) ? this.ip : user.lastIp();

        return new User(user.id(), user.username().value(), email, ip, user.created(), OffsetDateTime.now(), true, null);
    }
}
