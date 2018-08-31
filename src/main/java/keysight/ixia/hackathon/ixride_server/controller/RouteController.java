package keysight.ixia.hackathon.ixride_server.controller;

import keysight.ixia.hackathon.ixride_server.auth.AuthResponse;
import keysight.ixia.hackathon.ixride_server.greedy.GreedySearch;
import keysight.ixia.hackathon.ixride_server.model.Car;
import keysight.ixia.hackathon.ixride_server.model.NavigationRoute;
import keysight.ixia.hackathon.ixride_server.model.Profile;
import keysight.ixia.hackathon.ixride_server.model.Route;
import keysight.ixia.hackathon.ixride_server.model.RouteWayPoint;
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
import java.util.stream.Collectors;

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

	@GetMapping("/profiles/{profileId}/routes")
	public NavigationRoute getRoutesById(@PathVariable Long profileId) {

		NavigationRoute result = new NavigationRoute();
		List<Route> routeEntriesForProfile = routeService.findByProfile(profileService.findById(profileId));
		if (routeEntriesForProfile.isEmpty()) {
			// no routes
		} else {
			if (routeEntriesForProfile.size() > 1) {
				// problem we should have only one route entry per profile, a passenger can't be
				// in 2 cars at the same time
				throw new RuntimeException(
						"Profile " + profileId + " has " + routeEntriesForProfile.size() + "entries in Routes");
			}
			Route routeEntryForProfile = routeEntriesForProfile.get(0);
			Car car = routeEntryForProfile.getCar();

			List<Route> waypointsForCar = routeService.findByCar(car);
			List<RouteWayPoint> navigationRoutePoints = waypointsForCar.stream()
					.map(cwp -> convertRouteToRouteEndPoint(cwp)).collect(Collectors.toList());

			// add the final destination to the route result with profileid Long.MAX_VALUE, and the next
			// available index
			RouteWayPoint destination = new RouteWayPoint();
			destination.setLatitude(GreedySearch.DESTINATION_LATITUDE);
			destination.setLatitude(GreedySearch.DESTINATION_LONGITUDE);
			destination.setIndex(navigationRoutePoints.stream().mapToLong(wp -> wp.getIndex()).max().getAsLong() + 1);
			destination.setProfileId(Long.MAX_VALUE);
			result.setCar(car);
			result.setRoute(navigationRoutePoints);
			result.setDriver(car.getProfile());
		}
		return result;
	}

	private RouteWayPoint convertRouteToRouteEndPoint(Route cwp) {
		RouteWayPoint rep = new RouteWayPoint();
		rep.setIndex(cwp.getOrderNumber());
		rep.setLatitude(cwp.getProfile().getAddressLatitude());
		rep.setLongitude(cwp.getProfile().getAddressLongitude());
		rep.setProfileId(cwp.getProfile().getId());
		return rep;
	}

	@PostMapping("/profiles/{profileId}/cars/{carId}/routes")
	public ResponseEntity<Object> addNewRoute(@PathVariable Long profileId, @PathVariable Long carId,
			@Valid @RequestBody Route route) {
		Profile profile = profileService.findById(profileId);
		route.setProfile(profile);

		Car car = carService.findById(carId);
		route.setCar(car);

		Route savedRoute = routeService.save(route);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedRoute.getId())
				.toUri();

		return ResponseEntity.created(location).build();
	}

}
