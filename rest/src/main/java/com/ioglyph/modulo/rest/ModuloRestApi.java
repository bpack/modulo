package com.ioglyph.modulo.rest;

import com.ioglyph.modulo.core.user.CreateUserCommand;
import com.ioglyph.modulo.core.user.User;
import com.ioglyph.modulo.core.user.UserRepository;
import com.ioglyph.modulo.core.user.UserService;
import com.ioglyph.modulo.rest.data.UserData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class ModuloRestApi {
    private static final Logger logger = LoggerFactory.getLogger(ModuloRestApi.class);
    private final UserRepository repository;
    private final UserService service;

    public ModuloRestApi(UserService service, UserRepository repository){
        this.service = service;
        this.repository = repository;
    }

    @GetMapping
    public List<UserData> allUsers() {
        return repository.all()
                .stream()
                .map(UserData::new)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserData data, UriComponentsBuilder builder){
        validate(data);
        CreateUserCommand command = new CreateUserCommand(data.username, data.email);
        User user = service.create(command);

        URI uri = builder.path("/api/users/{id}").buildAndExpand(user.id()).toUri();
        return ResponseEntity.created(uri).body(userResponse(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable UUID id){
        logger.info("User ID {} requested.", id);
        return repository.load(id)
                .map(user -> ResponseEntity.ok(userResponse(user)))
                .orElseThrow(ResourceNotFoundException::new);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable UUID id){
        logger.info("Deleting user with ID {}", id);
        service.delete(id);
    }

    private Map<String, Object> userResponse(User user) {
        return new HashMap<String, Object>() {{
            put("user", new UserData(user));
        }};
    }

    private void validate(UserData data){
        if(null != data.id){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID specification not allowed on user creation");
        }
    }
}
