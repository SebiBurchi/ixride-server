package keysight.ixia.hackathon.ixride_server.service;

import keysight.ixia.hackathon.ixride_server.model.Profile;
import keysight.ixia.hackathon.ixride_server.model.User;

import java.util.List;

public interface ProfileService {

    Profile findById(long id);

    Profile findByUser(User user);

    List<Profile> findAll();

    Profile save(Profile profile);

    Long deleteProfileById(long id);
}
