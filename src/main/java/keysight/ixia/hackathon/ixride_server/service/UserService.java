package keysight.ixia.hackathon.ixride_server.service;

import keysight.ixia.hackathon.ixride_server.model.User;

import java.util.List;

public interface UserService {

    User findById(long id);

    List<User> findAll();

    User findByUsernameAndPassword(String username, String password);

    User save(User user);

    Long deleteUserById(Long userId);
}
