package keysight.ixia.hackathon.ixride_server.controller;


import keysight.ixia.hackathon.ixride_server.auth.AuthResponse;
import keysight.ixia.hackathon.ixride_server.model.User;
import keysight.ixia.hackathon.ixride_server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthController {

    @Autowired
    UserRepository userRepository;


    @PostMapping("/auth")
    public AuthResponse authUser(@Valid @RequestBody User user) {

        User loginUser = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        if (loginUser == null) {
            return AuthResponse.builder().id(null).username(null).password(null).build();
        } else {
            return AuthResponse.builder().id(loginUser.getId()).username(loginUser.getUsername()).password(loginUser.getPassword()).build();
        }


    }
}
