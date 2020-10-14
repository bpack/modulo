package com.ioglyph.modulo.user;

import com.ioglyph.modulo.core.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.UUID;

class UserTest {

    @Test
    void testDelete(){
        User user = new User(UUID.randomUUID(), "username", "username@example.com",
            "10.10.10.10", OffsetDateTime.now(), OffsetDateTime.now(), true, null);

        user.delete();

        Assertions.assertNotNull(user.deleted());
        Assertions.assertFalse(user.visible());
    }

    @Test
    void testValidUsernames(){
        UUID id = UUID.randomUUID();
        String email = "test@example.com";
        String ip = "127.0.255.012";
        String[] usernames = { "sample", "sample_1", "sample.1", "sample42", "sample-me", "1234", "_sample"};

        Arrays.asList(usernames)
                .stream()
                .map(name -> new User(id, name, email, ip, null, null, true, null))
                .forEach(Assertions::assertNotNull);
    }

    @ParameterizedTest
    @ValueSource(strings ={ "sample!", "sample's", "$sample", "sample()", "sam ple" } )
    void testInvalidUsernameThrowsException(String user){
        String ip = "10.10.10.10";
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new User(UUID.randomUUID(), user, "test@example.com", ip, null, null, true, null);
        });
    }

    @Test
    void testValidEmails(){
        UUID id = UUID.randomUUID();
        String username = "sample";
        String ip = "10.10.10.10";
        String[] emails = {"test.1@example.com", "test_1@example.com", "test+1@example.com"};

        Arrays.asList(emails)
                .stream()
                .map(email -> new User(id, username, email, ip, null, null, true, null))
                .forEach(Assertions::assertNotNull);
    }

    @ParameterizedTest
    @ValueSource(strings ={ "sample!", "sam ple@example.com", "$sample@example.com", "sample()@example.com"} )
    void testInvalidEmailThrowsException(String email){
        String ip = "10.10.10.10";
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new User(UUID.randomUUID(), "user", email, ip, null, null, true, null);
        });
    }

    @ParameterizedTest
    @ValueSource(strings ={ "....", "256.10.10.10", "123.456.789.012", "125..122.1"} )
    void testInvalidIpThrowsException(String ip){
        String email = "test@test.com";
        String username = "user";
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new User(UUID.randomUUID(), username, email, ip, null, null, true, null);
        });
    }
}
