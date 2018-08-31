/**
 * 
 */
package keysight.ixia.hackathon.ixride_server.greedy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ovapostu
 *
 */
public class Vehicle implements Cloneable {
	public long getId() {
		return id;
	}

	private long id;
	private GeoLocation currentLocation;
	private GeoLocation originLocation;
	private int totalCapacity;
	private int remainingCapacity;
	private List<GeoLocation> route;
	private List<Passenger> passengers;
	private double totalCost;

	public Vehicle(long id, GeoLocation origin, int capacity) {
		this.id = id;
		this.totalCapacity = capacity;
		// total minus the driver
		this.remainingCapacity = capacity - 1;
		this.currentLocation = origin;
		this.originLocation = new GeoLocation(origin.getLatitude(), origin.getLongitude());
		this.route = new ArrayList<GeoLocation>();
		this.route.add(origin);
		this.passengers = new ArrayList<Passenger>();
		this.totalCost = 0;
	}

	public List<Passenger> getPassengers() {
		return passengers;
	}

	public GeoLocation getOriginLocation() {
		return originLocation;
	}

	public boolean hasSeatsAvalable() {
		return remainingCapacity > 0;
	}

	public int getCapacity() {
		return remainingCapacity;
	}

	public void addDestination(GeoLocation location) {
		this.totalCost += this.currentLocation.getDistanceToLocation(location);
		this.currentLocation = new GeoLocation(location.getLatitude(), location.getLongitude());
		route.add(location);
	}

	public List<GeoLocation> getRoute() {
		return route;
	}

	public void addPassenger(Passenger passenger) {
		if (!hasSeatsAvalable()) {
			throw new RuntimeException("No seats available");
		}
		this.addDestination(passenger);
		this.passengers.add(passenger);
		this.remainingCapacity--;
	}

	public GeoLocation getCurrentLocation() {
		return currentLocation;
	}

	public Passenger getNearestPassenger(List<Passenger> passengers) {
		if (passengers == null || passengers.isEmpty())
			return null;

		final GeoLocation vehicleLocation = this.currentLocation;
		return passengers.stream().min(new Comparator<Passenger>() {

			@Override
			public int compare(Passenger o1, Passenger o2) {
				double distanceTo1 = vehicleLocation.getDistanceToLocation(o1);
				double distanceTo2 = vehicleLocation.getDistanceToLocation(o2);
				if (distanceTo1 < distanceTo2)
					return -1;
				if (distanceTo1 > distanceTo2)
					return 1;
				return 0;
			}
		}).get();
	}

	public List<Passenger> sortPassengersBySmallestDeviationFromRoute(List<Passenger> passengers,
			GeoLocation destination) {
		List<Passenger> result = new ArrayList<Passenger>(passengers);
		final Vehicle vehicle = this;
		return result.stream().sorted(new Comparator<Passenger>() {
			@Override
			public int compare(Passenger o1, Passenger o2) {

				double distance1 = vehicle.getOriginLocation().getDistanceToLocation(o1)
						+ o1.getDistanceToLocation(destination);
				double distance2 = vehicle.getOriginLocation().getDistanceToLocation(o2)
						+ o2.getDistanceToLocation(destination);
				if (distance1 < distance2)
					return -1;
				if (distance1 > distance2)
					return 1;
				return 0;
			}
		}).collect(Collectors.toList());
	}

	@Override
	public Vehicle clone() {
		return new Vehicle(this.id, this.getCurrentLocation(), this.remainingCapacity);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Vehicle ").append(id).append(" Passengers ");
		passengers.forEach(p -> sb.append(" ").append(p.getId()));
		sb.append(" Total Cost ").append(totalCost);
		return sb.toString();
	}

	public double getTotalCost() {
		return totalCost;
	}

}
