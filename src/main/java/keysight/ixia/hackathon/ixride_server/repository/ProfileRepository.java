package keysight.ixia.hackathon.ixride_server.repository;

import keysight.ixia.hackathon.ixride_server.model.Profile;
import keysight.ixia.hackathon.ixride_server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Profile findById(long id);

    Profile findByUser(User user);

    List<Profile> findAll();

    Profile save(Profile profile);

    Long deleteProfileById(long id);

}
