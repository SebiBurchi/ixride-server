package keysight.ixia.hackathon.ixride_server.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ovapostu
 *
 */
public class NavigationRoute  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1971032079207359909L;

	@JsonProperty
	private List<RouteWayPoint> route;

	@JsonProperty
	private Car car;

	@JsonProperty
	private Profile driver;

	public List<RouteWayPoint> getRoute() {
		return route;
	}

	public void setRoute(List<RouteWayPoint> route) {
		this.route = route;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public Profile getDriver() {
		return driver;
	}

	public void setDriver(Profile driver) {
		this.driver = driver;
	}

}
