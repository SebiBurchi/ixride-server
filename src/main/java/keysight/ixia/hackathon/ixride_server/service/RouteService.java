package keysight.ixia.hackathon.ixride_server.service;

import keysight.ixia.hackathon.ixride_server.model.Route;

import java.util.Date;
import java.util.List;

public interface RouteService {

    Route findByCar(Long carId);

    Route findByProfile(Long profileId);

    List<Route> findByCarAndProfile(Long cardId, Long profileId);

    List<Route> findAll();

    Route findByTimestamp(Date timestamp);

    Route save(Route route);
}
