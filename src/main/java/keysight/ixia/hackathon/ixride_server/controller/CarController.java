package keysight.ixia.hackathon.ixride_server.controller;

import keysight.ixia.hackathon.ixride_server.model.Car;
import keysight.ixia.hackathon.ixride_server.model.Profile;
import keysight.ixia.hackathon.ixride_server.service.CarService;
import keysight.ixia.hackathon.ixride_server.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CarController {

    private final CarService carService;
    private final ProfileService profileService;

    @Autowired
    public CarController(CarService carService, ProfileService profileService) {
        this.carService = carService;
        this.profileService = profileService;
    }

    @GetMapping("/cars")
    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();
        carService.findAll().forEach(cars::add);
        return cars;
    }

    @GetMapping("/cars/{carId}")
    public Car getCarById(@PathVariable Long id) {
        return carService.findById(id);
    }

    @GetMapping("/profiles/{profileId}/car")
    public Car getCarByProfile(@PathVariable long profileId) {
        Profile profile = profileService.findById(profileId);
        return carService.findByProfile(profile);
    }

    @PostMapping("/profiles/{profileId}/cars")
    public Car addNewCar(@PathVariable Long profileId, @Valid @RequestBody Car car) {
        Profile profile = profileService.findById(profileId);
        car.setProfile(profile);

        return carService.save(car);

    }
}
