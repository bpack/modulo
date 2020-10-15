package com.ioglyph.modulo.rest;

import com.ioglyph.modulo.core.user.*;
import com.ioglyph.modulo.rest.data.LocationData;
import com.ioglyph.modulo.rest.data.UserData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
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
    public ResponseEntity<UserData> createUser(@RequestBody UserData data, UriComponentsBuilder builder){
        validate(data);
        CreateUserCommand command = new CreateUserCommand(data.username, data.email, data.ip);
        User user = service.create(command);

        URI uri = builder.path("/api/user/{id}").buildAndExpand(user.id()).toUri();
        return ResponseEntity.created(uri).body(userResponse(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserData> getUser(@PathVariable UUID id){
        logger.info("User ID {} requested.", id);
        return repository.load(id)
                .map(user -> ResponseEntity.ok(userResponse(user)))
                .orElseThrow(ResourceNotFoundException::new);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserData> updateUser(@PathVariable UUID id, @RequestBody UserData data){
        User user = repository.load(id).orElseThrow(ResourceNotFoundException::new);
        UpdateUserCommand command = new UpdateUserCommand(user, data.email, data.ip);
        User updated = service.update(command);
        return ResponseEntity.ok(userResponse(updated));
    }

    @GetMapping("/{id}/location")
    public ResponseEntity<LocationData> getUserLocation(@PathVariable UUID id){
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

    private UserData userResponse(User user) {
        return new UserData(user);
    }

    private LocationData locationResponse(UserLocation location){
        return new LocationData(location);
    }

    private void validate(UserData data){
        if(null != data.id){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID specification not allowed on user creation");
        }
        if(repository.queryByUsername(data.username).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username " + data.username + " already exists");
        }
    }
}
