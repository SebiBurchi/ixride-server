package keysight.ixia.hackathon.ixride_server.controller;


import keysight.ixia.hackathon.ixride_server.model.Car;
import keysight.ixia.hackathon.ixride_server.model.Profile;
import keysight.ixia.hackathon.ixride_server.model.Route;
import keysight.ixia.hackathon.ixride_server.service.CarService;
import keysight.ixia.hackathon.ixride_server.service.ProfileService;
import keysight.ixia.hackathon.ixride_server.service.RouteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RouteController {

    private final RouteService routeService;
    private final ProfileService profileService;
    private final CarService carService;

    public RouteController(RouteService routeService, ProfileService profileService, CarService carService) {
        this.routeService = routeService;
        this.profileService = profileService;
        this.carService = carService;
    }

    @GetMapping("/routes")
    public List<Route> getAllRoutes() {
        List<Route> routes = new ArrayList<>();
        routeService.findAll().forEach(routes::add);
        return routes;
    }

    @GetMapping("/profiles/{profileId}/cars/{carId}/routes")
    public List<Route> getRoutesById(@PathVariable Long profileId, @PathVariable Long carId) {
        return routeService.findByCarAndProfile(carId, profileId);
    }

    @PostMapping("/profiles/{profileId}/cars/{carId}/routes")
    public ResponseEntity<Object> addNewRoute(@PathVariable Long profileId, @PathVariable Long carId, @Valid @RequestBody Route route) {
        Profile profile = profileService.findById(profileId);
        route.setProfile(profile);

        Car car = carService.findById(carId);
        route.setCar(car);

        Route savedRoute = routeService.save(route);
        URI location = ServletUriComponentsBuilder.
                fromCurrentRequest().
                path("/{id}").
                buildAndExpand(savedRoute.getId()).
                toUri();

        return ResponseEntity.created(location).build();
    }


}
