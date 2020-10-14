package com.ioglyph.modulo.persistence.user;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.ioglyph.modulo.core.user.User;
import com.ioglyph.modulo.core.user.UserRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class JpaUserRepository extends SimpleJpaRepository<PersistentUser, UUID> implements UserRepository{

    public JpaUserRepository(final EntityManager em){
        super(PersistentUser.class, em);
    }

    @Override
    public void persist(User user){
        this.save(PersistentUser.from(user));
    }

    @Override
    public Optional<User> load(UUID id) {
        return this.findById(id).filter(u -> u.visible).map(PersistentUser::toUser);
    }

    @Override
    public Optional<User> queryByUsername(String username){
        // TODO: The following doesn't work but findAll + stream does
//        PersistentUser u = new PersistentUser();
//        u.setUsername(username);
//        return this.findOne(Example.of(u)).map(PersistentUser::toUser);
        return this.findAll()
                .stream()
                .filter(u -> u.getUsername().equals(username))
                .map(PersistentUser::toUser)
                .findFirst();
    }

    @Override
    public List<User> all() {
        return this.findAll().stream()
                .filter(u -> u.visible)
                .map(PersistentUser::toUser)
                .collect(Collectors.toList());
    }

}
