package keysight.ixia.hackathon.ixride_server.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "Profiles")
public class Profile implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROFILE_ID", updatable = false)
    private long id;

    @Column(name = "NAME", updatable = false, length = 50)
    private String name;

    @Column(name = "PHONE", length = 20, unique = true)
    private String phone;

    @Column(name = "ADDRESS_LONGITUDE")
    private double addressLongitude;

    @Column(name = "ADDRESS_LATITUDE")
    private double addressLatitude;

    @Column(name = "ISDRIVER")
    private boolean isDriver;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ID", nullable = false)
    @JsonIgnore
    private User user;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "profile")
    @JsonIgnore
    private Car car;

    @OneToMany(mappedBy = "profile", fetch = FetchType.LAZY)
    @JsonIgnore
    private Collection<Route> routes;

    public Profile() {
    }

    public Profile(String name, String phone, double addressLongitude, double addressLatitude, boolean isDriver) {
        this.name = name;
        this.phone = phone;
        this.addressLongitude = addressLongitude;
        this.addressLatitude = addressLatitude;
        this.isDriver = isDriver;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getAddressLongitude() {
        return addressLongitude;
    }

    public void setAddressLongitude(double addressLongitude) {
        this.addressLongitude = addressLongitude;
    }

    public double getAddressLatitude() {
        return addressLatitude;
    }

    public void setAddressLatitude(double addressLatitude) {
        this.addressLatitude = addressLatitude;
    }

    public boolean isDriver() {
        return isDriver;
    }

    public void setDriver(boolean driver) {
        isDriver = driver;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return id == profile.id &&
                Double.compare(profile.addressLongitude, addressLongitude) == 0 &&
                Double.compare(profile.addressLatitude, addressLatitude) == 0 &&
                isDriver == profile.isDriver &&
                Objects.equals(name, profile.name) &&
                Objects.equals(phone, profile.phone) &&
                Objects.equals(user, profile.user) &&
                Objects.equals(car, profile.car) &&
                Objects.equals(routes, profile.routes);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, phone, addressLongitude, addressLatitude, isDriver, user, car, routes);
    }

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", addressLongitude=" + addressLongitude +
                ", addressLatitude=" + addressLatitude +
                ", isDriver=" + isDriver +
                ", user=" + user +
                ", car=" + car +
                ", routes=" + routes +
                '}';
    }
}
