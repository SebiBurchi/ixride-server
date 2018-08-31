package keysight.ixia.hackathon.ixride_server.controller;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import keysight.ixia.hackathon.ixride_server.greedy.GeoLocation;
import keysight.ixia.hackathon.ixride_server.greedy.GreedySearch;
import keysight.ixia.hackathon.ixride_server.greedy.Passenger;
import keysight.ixia.hackathon.ixride_server.greedy.Solution;
import keysight.ixia.hackathon.ixride_server.greedy.Vehicle;
import keysight.ixia.hackathon.ixride_server.model.Car;
import keysight.ixia.hackathon.ixride_server.model.Profile;
import keysight.ixia.hackathon.ixride_server.model.Route;
import keysight.ixia.hackathon.ixride_server.model.User;
import keysight.ixia.hackathon.ixride_server.service.CarService;
import keysight.ixia.hackathon.ixride_server.service.ProfileService;
import keysight.ixia.hackathon.ixride_server.service.RouteService;
import keysight.ixia.hackathon.ixride_server.service.UserService;

@RestController
public class GreedySolutionController {
	private final CarService carService;
	private final ProfileService profileService;
	private final UserService userService;
	private final RouteService routeService;

	@Autowired
	public GreedySolutionController(CarService carService, ProfileService profileService, UserService userService,
			RouteService routeService) {
		this.carService = carService;
		this.profileService = profileService;
		this.userService = userService;
		this.routeService = routeService;
	}

	@GetMapping("/greedypopulate")
	public ResponseEntity<Object> populateWithFakeData() {

		Random ran = new Random();
		int nrOfPassengers = 400;
		int nrOfVehicles = 100;

		double latitudes[] = ran.doubles(nrOfPassengers, GreedySearch.FAKE_LAT_START, GreedySearch.FAKE_LAT_END)
				.toArray();
		double longitudes[] = ran.doubles(nrOfPassengers, GreedySearch.FAKE_LONG_START, GreedySearch.FAKE_LONG_END)
				.toArray();

		for (int i = 0; i < nrOfPassengers; i++) {
			User u = new User("passenger" + i, "passwroddd");
			Profile p = new Profile("passenger myname p" + i, "001234" + i, longitudes[i], latitudes[i], false);
			User savedUser = userService.save(u);			
			p.setUser(savedUser);
			profileService.save(p);
		}

		latitudes = ran.doubles(nrOfPassengers, GreedySearch.FAKE_LAT_START, GreedySearch.FAKE_LAT_END).toArray();
		longitudes = ran.doubles(nrOfPassengers, GreedySearch.FAKE_LONG_START, GreedySearch.FAKE_LONG_END).toArray();

		for (int i = 0; i < nrOfVehicles; i++) {
			User u = new User("driver" + i, "passwroddd");
			Profile p = new Profile("driver myname " + i, "11001234" + i, longitudes[i], latitudes[i], true);
			User savedUser = userService.save(u);
			p.setUser(savedUser);
			Profile savedProfile = profileService.save(p);
			Car c = new Car("license" + i, 5);
			c.setProfile(savedProfile);
			carService.save(c);

		}

		return ResponseEntity.ok().build();
	}

	@GetMapping("/greedy")
	public ResponseEntity<Object> performSearch() {		

		List<Profile> passengerProfiles = profileService.findAllByIsDriver(false);
		List<Passenger> allPassengers = passengerProfiles.stream().map(
				profile -> new Passenger(profile.getId(), profile.getAddressLatitude(), profile.getAddressLongitude()))
				.collect(Collectors.toList());

		List<Profile> driverProfiles = profileService.findAllByIsDriver(true);
		List<Vehicle> allVehicles = driverProfiles.stream()
				.map(profile -> new Vehicle(profile.getId(),
						new GeoLocation(profile.getAddressLatitude(), profile.getAddressLongitude()),
						carService.findByProfile(profile).getSeatsNumber()))
				.collect(Collectors.toList());

		GreedySearch problem = new GreedySearch(new GeoLocation(GreedySearch.DESTINATION_LATITUDE, GreedySearch.DESTINATION_LONGITUDE));
		problem.setPassengers(allPassengers);
		problem.setVehicles(allVehicles);
		problem.findSolutions();
		Solution bestSolution = problem.getBestSolution();
		Date timestamp = new Date();

		for (Vehicle v : bestSolution.getVehicless()) {
			Car car = carService.findByProfile(profileService.findById(v.getId()));
			Route origin = new Route((long) 0, timestamp, car, profileService.findById(v.getId()));
			routeService.save(origin);
			for (int i = 0; i < v.getPassengers().size(); i++) {
				Route r = new Route((long) i + 1, timestamp, car,
						profileService.findById(v.getPassengers().get(i).getId()));
				routeService.save(r);
			}		
		}

		return ResponseEntity.ok().build();
	}
}
