package com.ioglyph.modulo.core.user;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    void persist(User user);

    Optional<User> load(UUID id);

    List<User> all();
}