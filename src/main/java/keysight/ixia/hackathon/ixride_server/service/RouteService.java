package keysight.ixia.hackathon.ixride_server.service;

import keysight.ixia.hackathon.ixride_server.model.Car;
import keysight.ixia.hackathon.ixride_server.model.Profile;
import keysight.ixia.hackathon.ixride_server.model.Route;

import java.util.Date;
import java.util.List;

public interface RouteService {

	List<Route> findByCar(Car car);

	List<Route> findByProfile(Profile profile);

    List<Route> findByCarAndProfile(Long cardId, Long profileId);

    List<Route> findAll();

    Route findByTimestamp(Date timestamp);

    Route save(Route route);
}
