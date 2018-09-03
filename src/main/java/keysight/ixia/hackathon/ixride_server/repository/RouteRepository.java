package keysight.ixia.hackathon.ixride_server.repository;

import keysight.ixia.hackathon.ixride_server.model.Car;
import keysight.ixia.hackathon.ixride_server.model.Profile;
import keysight.ixia.hackathon.ixride_server.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface RouteRepository extends JpaRepository<Route, Long> {

	List<Route> findByCar(Car car);

	List<Route> findByProfile(Profile profile);

    List<Route> findByCarAndProfile(Long cardId, Long profileId);

    List<Route> findAll();

    Route findByTimestamp(Date timestamp);

    Route save(Route route);

    void deleteAll();
}
