package keysight.ixia.hackathon.ixride_server.service;

import keysight.ixia.hackathon.ixride_server.model.Profile;

import java.util.List;

public interface ProfileService {

    Profile findById(long id);

    Profile findByUser(long userId);

    List<Profile> findAll();

    Profile save(Profile profile);

    Long deleteProfileById(long id);
}
