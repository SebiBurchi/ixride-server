package keysight.ixia.hackathon.ixride_server.repository;

import keysight.ixia.hackathon.ixride_server.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    Car findById(long id);

    Car findByProfile(long profileId);

    List<Car> findAll();

    Car findByLicensePlate(String licensePlate);

    Car save(Car car);


}
