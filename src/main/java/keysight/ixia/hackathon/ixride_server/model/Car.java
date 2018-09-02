package keysight.ixia.hackathon.ixride_server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "Cars")
public class Car implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CAR_ID", updatable = false)
    private long id;

    @Column(name = "LICENSE_PLATE", length = 30, unique = true)
    private String licensePlate;

    @Column(name = "SEATS_NUMBER", length = 2)
    private int seatsNumber;

    @OneToOne(optional = false)
    @JoinColumn(name = "PROFILE_ID", nullable = false)
    @JsonIgnore
    private Profile profile;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Collection<Route> routes;


    public Car() {
    }

    public Car(String licensePlate, int seatsNumber) {
        this.licensePlate = licensePlate;
        this.seatsNumber = seatsNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public int getSeatsNumber() {
        return seatsNumber;
    }

    public void setSeatsNumber(int seatsNumber) {
        this.seatsNumber = seatsNumber;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Collection<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(Collection<Route> routes) {
        this.routes = routes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return id == car.id &&
                seatsNumber == car.seatsNumber &&
                Objects.equals(licensePlate, car.licensePlate) &&
                Objects.equals(profile, car.profile) &&
                Objects.equals(routes, car.routes);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, licensePlate, seatsNumber, profile, routes);
    }

}
