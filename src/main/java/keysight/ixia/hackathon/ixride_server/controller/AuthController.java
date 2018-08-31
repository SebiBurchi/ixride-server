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
			AuthResponse ar = new AuthResponse();
			ar.setId(null);
			ar.setPassword(null);
			ar.setUsername(null);
			return ar;
		} else {
			AuthResponse ar = new AuthResponse();
			ar.setId(loginUser.getId());
			ar.setPassword(loginUser.getPassword());
			ar.setUsername(loginUser.getUsername());
			return ar;
		}

	}
}
