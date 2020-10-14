package com.ioglyph.modulo.user;

import com.ioglyph.modulo.core.user.CreateUserCommand;
import com.ioglyph.modulo.core.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CreateUserCommandTest {

    @Test
    void testCreate(){
        String username = "user";
        String email = "email@example.com";
        String ip = "32.64.128.0";
        CreateUserCommand command = new CreateUserCommand(username, email, ip);
        User user = command.execute();

        Assertions.assertNotNull(user.id());
        Assertions.assertNull(user.deleted());
        Assertions.assertTrue(user.visible());
        Assertions.assertEquals(username, user.username().value());
        Assertions.assertEquals(email, user.email());
        Assertions.assertEquals(ip, user.lastIp());

        Assertions.assertEquals(user.created(), user.updated());
    }
}
