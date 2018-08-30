package keysight.ixia.hackathon.ixride_server.service;

import keysight.ixia.hackathon.ixride_server.model.Car;
import keysight.ixia.hackathon.ixride_server.model.Profile;

import java.util.List;

public interface CarService {

    Car findById(long id);

    Car findByProfile(Profile profile);

    List<Car> findAll();

    Car findByLicensePlate(String licensePlate);

    Car save(Car car);
}
