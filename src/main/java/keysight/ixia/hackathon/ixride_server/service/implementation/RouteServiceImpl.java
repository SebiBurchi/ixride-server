package keysight.ixia.hackathon.ixride_server.service.implementation;

import keysight.ixia.hackathon.ixride_server.model.Route;
import keysight.ixia.hackathon.ixride_server.repository.RouteRepository;
import keysight.ixia.hackathon.ixride_server.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RouteServiceImpl implements RouteService {

    RouteRepository routeRepository;

    @Autowired
    public void setRouteRepository(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    @Override
    public Route findByCar(Long carId) {
        return routeRepository.findByCar(carId);
    }

    @Override
    public Route findByProfile(Long profileId) {
        return routeRepository.findByProfile(profileId);
    }

    @Override
    public List<Route> findByCarAndProfile(Long cardId, Long profileId) {
        return routeRepository.findByCarAndProfile(cardId, profileId);
    }

    @Override
    public List<Route> findAll() {
        return routeRepository.findAll();
    }

    @Override
    public Route findByTimestamp(Date timestamp) {
        return routeRepository.findByTimestamp(timestamp);
    }

    @Override
    public Route save(Route route) {
        return routeRepository.save(route);
    }
}
