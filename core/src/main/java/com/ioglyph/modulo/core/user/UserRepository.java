package com.ioglyph.modulo.core.user;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Data access interface for the User domain object.
 */
public interface UserRepository {
    void persist(User user);

    Optional<User> load(UUID id);

    Optional<User> queryByUsername(String username);

    List<User> all();
}