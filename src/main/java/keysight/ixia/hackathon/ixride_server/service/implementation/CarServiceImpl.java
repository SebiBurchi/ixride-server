package keysight.ixia.hackathon.ixride_server.service.implementation;

import keysight.ixia.hackathon.ixride_server.model.Car;
import keysight.ixia.hackathon.ixride_server.model.Profile;
import keysight.ixia.hackathon.ixride_server.repository.CarRepository;
import keysight.ixia.hackathon.ixride_server.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    CarRepository carRepository;

    @Autowired
    public void setCarRepository(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public Car findById(long id) {
        return carRepository.findById(id);
    }

    @Override
    public Car findByProfile(Profile profile) {
        return carRepository.findByProfile(profile);
    }

    @Override
    public List<Car> findAll() {
        return carRepository.findAll();
    }

    @Override
    public Car findByLicensePlate(String licensePlate) {
        return carRepository.findByLicensePlate(licensePlate);
    }

    @Override
    public Car save(Car car) {
        return carRepository.save(car);
    }
}
