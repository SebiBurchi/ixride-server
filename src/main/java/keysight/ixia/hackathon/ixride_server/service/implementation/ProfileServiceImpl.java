package keysight.ixia.hackathon.ixride_server.service.implementation;

import keysight.ixia.hackathon.ixride_server.model.Profile;
import keysight.ixia.hackathon.ixride_server.model.User;
import keysight.ixia.hackathon.ixride_server.repository.ProfileRepository;
import keysight.ixia.hackathon.ixride_server.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileServiceImpl implements ProfileService {

    ProfileRepository profileRepository;

    @Autowired
    public void setProfileRepository(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public Profile findById(long id) {
        return profileRepository.findById(id);
    }

    @Override
    public Profile findByUser(User user) {
        return profileRepository.findByUser(user);
    }

    @Override
    public List<Profile> findAll() {
        return profileRepository.findAll();
    }

    @Override
    public Profile save(Profile profile) {
        return profileRepository.save(profile);
    }

    @Override
    public Long deleteProfileById(long id) {
        return profileRepository.deleteProfileById(id);
    }


}
