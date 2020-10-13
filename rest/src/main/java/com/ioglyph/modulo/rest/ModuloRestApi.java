package com.ioglyph.modulo.rest;

import com.ioglyph.modulo.core.user.*;
import com.ioglyph.modulo.rest.data.LocationData;
import com.ioglyph.modulo.rest.data.UserData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
public class ModuloRestApi {
    private static final Logger logger = LoggerFactory.getLogger(ModuloRestApi.class);

    private final UserRepository repository;
    private final UserService service;
    private final UserLocationService locator;

    public ModuloRestApi(UserService service, UserLocationService locator, UserRepository repository){
        this.service = service;
        this.locator = locator;
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
        CreateUserCommand command = new CreateUserCommand(data.username, data.email, data.ip);
        User user = service.create(command);

        URI uri = builder.path("/api/user/{id}").buildAndExpand(user.id()).toUri();
        return ResponseEntity.created(uri).body(userResponse(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable UUID id){
        logger.info("User ID {} requested.", id);
        return repository.load(id)
                .map(user -> ResponseEntity.ok(userResponse(user)))
                .orElseThrow(ResourceNotFoundException::new);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable UUID id, @RequestBody UserData data){
        User user = repository.load(id).orElseThrow(ResourceNotFoundException::new);
        UpdateUserCommand command = new UpdateUserCommand(user, data.email, data.ip);
        User updated = service.update(command);
        return ResponseEntity.ok(userResponse(updated));
    }

    @GetMapping("/{id}/location")
    public ResponseEntity<?> getUserLocation(@PathVariable UUID id){
        return locator
                .getLocationData(id)
                .map(location -> ResponseEntity.ok(locationResponse(location)))
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

    private Map<String, Object> locationResponse(UserLocation location){
        return new HashMap<String, Object>() {{
            put("location", new LocationData(location));
        }};
    }

    private void validate(UserData data){
        if(null != data.id){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID specification not allowed on user creation");
        }
    }
}
