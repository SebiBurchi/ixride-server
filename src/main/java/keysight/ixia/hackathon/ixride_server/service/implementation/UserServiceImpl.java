package keysight.ixia.hackathon.ixride_server.service.implementation;

import keysight.ixia.hackathon.ixride_server.model.User;
import keysight.ixia.hackathon.ixride_server.repository.UserRepository;
import keysight.ixia.hackathon.ixride_server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public Long deleteUserById(Long userId) {
        return userRepository.deleteUserById(userId);
    }


}
