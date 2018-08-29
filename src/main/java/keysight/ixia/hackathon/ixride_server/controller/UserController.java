package keysight.ixia.hackathon.ixride_server.controller;


import keysight.ixia.hackathon.ixride_server.model.User;
import keysight.ixia.hackathon.ixride_server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    private UserController(UserService userService) {
        this.userService = userService;

    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        userService.findAll().forEach(users::add);
        return users;
    }


    @GetMapping("/users/{userId}")
    public User getUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        return user;

    }

    @PostMapping("/users")
    public ResponseEntity<Object> addNewUser(@Valid @RequestBody User user) {

        User savedUser = userService.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{userId}")
    public long deleteUser(@PathVariable Long id) {
        return userService.deleteUserById(id);

    }


}
