package keysight.ixia.hackathon.ixride_server.controller;


import keysight.ixia.hackathon.ixride_server.model.User;
import keysight.ixia.hackathon.ixride_server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public User getUserById(@PathVariable long userId) {
        return userService.findById(userId);

    }

    @PostMapping("/users")
    public User addNewUser(@Valid @RequestBody User user) {

        return userService.save(user);

    }

    @PutMapping("/users/{userId}")
    public User updateUser(@Valid @RequestBody User user, @PathVariable Long userId) {
        User userToUpdate = userService.findById(userId);
        if (userToUpdate != null) {
            userToUpdate.setUsername(user.getUsername());
            userToUpdate.setPassword(user.getPassword());
        }

        return userService.save(userToUpdate);
    }


    @DeleteMapping("/users/{userId}")
    public Long deleteUser(@PathVariable("userId") Long userId) {
        return userService.deleteUserById(userId);
    }


}
