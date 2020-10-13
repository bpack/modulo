package com.ioglyph.modulo.user;

import com.ioglyph.modulo.core.user.CreateUserCommand;
import com.ioglyph.modulo.core.user.UpdateUserCommand;
import com.ioglyph.modulo.core.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UpdateUserCommandTest {

    private final String username = "test_user";
    private final String email = "test_user@example.com";
    private final String ip = "171.22.33.44";

    @Test
    public void testUpdateWithEmailChange(){
        User orig = createTestUser();
        UpdateUserCommand command = new UpdateUserCommand(orig, "new_email@example.com", null);

        User user = command.execute();
        Assertions.assertEquals(orig.lastIp(), user.lastIp());
        Assertions.assertNotEquals(orig.updated(), user.updated());
        Assertions.assertEquals("new_email@example.com", user.email());
    }

    @Test
    public void testUpdateWithIpChange(){
        User orig = createTestUser();
        UpdateUserCommand command = new UpdateUserCommand(orig, null, "1.1.1.1");

        User user = command.execute();
        Assertions.assertEquals(orig.email(), user.email());
        Assertions.assertNotEquals(orig.updated(), user.updated());
        Assertions.assertEquals("1.1.1.1", user.lastIp());
    }

    @Test
    public void testUpdateWithIpAndEmailChange(){
        User orig = createTestUser();
        String newEmail = "another@newemail.com";
        String newIp = "255.255.255.255";

        UpdateUserCommand command = new UpdateUserCommand(orig, newEmail, newIp);
        User user = command.execute();

        Assertions.assertEquals(newEmail, user.email());
        Assertions.assertEquals(newIp, user.lastIp());
        Assertions.assertNotEquals(orig.updated(), user.updated());
    }

    private User createTestUser(){
        return new CreateUserCommand(username, email, ip).execute();
    }
}
