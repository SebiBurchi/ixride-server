package keysight.ixia.hackathon.ixride_server.repository;

import keysight.ixia.hackathon.ixride_server.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface RouteRepository extends JpaRepository<Route, Long> {

    Route findByCar(Long carId);

    Route findByProfile(Long profileId);

    List<Route> findByCarAndProfile(Long cardId, Long profileId);

    List<Route> findAll();

    Route findByTimestamp(Date timestamp);

    Route save(Route route);


}
