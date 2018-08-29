package keysight.ixia.hackathon.ixride_server.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


@Entity
@Table(name = "Routes")
public class Route implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROUTE_ID", updatable = false)
    private Long id;

    @Column(name = "ORDER_NUMBER")
    private Long orderNumber;

    @Column(name = "TIMESTAMP")
    private Date timestamp;

    @ManyToOne
    @JoinColumn(name = "CAR_ID", updatable = false)
    private Car car;

    @ManyToOne
    @JoinColumn(name = "PROFILE_ID", updatable = false)
    private Profile profile;

    public Route() {
    }

    public Route(Long orderNumber, Date timestamp, Car car, Profile profile) {
        this.orderNumber = orderNumber;
        this.timestamp = timestamp;
        this.car = car;
        this.profile = profile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return Objects.equals(id, route.id) &&
                Objects.equals(orderNumber, route.orderNumber) &&
                Objects.equals(timestamp, route.timestamp) &&
                Objects.equals(car, route.car) &&
                Objects.equals(profile, route.profile);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, orderNumber, timestamp, car, profile);
    }

    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", orderNumber=" + orderNumber +
                ", timestamp=" + timestamp +
                ", car=" + car +
                ", profile=" + profile +
                '}';
    }
}
