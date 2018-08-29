package keysight.ixia.hackathon.ixride_server.service;

import keysight.ixia.hackathon.ixride_server.model.Car;

import java.util.List;

public interface CarService {

    Car findById(long id);

    Car findByProfile(long profileId);

    List<Car> findAll();

    Car findByLicensePlate(String licensePlate);

    Car save(Car car);
}
