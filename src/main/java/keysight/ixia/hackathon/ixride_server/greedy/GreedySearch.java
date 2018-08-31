package keysight.ixia.hackathon.ixride_server.greedy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class GreedySearch {

	private static final int MAX_SEARCH_ITERATIONS = 1000;
	private static final int TOTAL_DEVATION_WEIGHT = 5;
	private static final int PASSENGER_LEFT_BEHIND_WEIGHT = 2;
	public static final double DESTINATION_LONGITUDE = 26.094533;
	public static final double DESTINATION_LATITUDE = 44.438959;

	public static final double FAKE_LAT_START = 44.323752;
	public static final double FAKE_LAT_END = 44.547617;
	public static final double FAKE_LONG_START = 25.938429;
	public static final double FAKE_LONG_END = 26.275744;

	private GeoLocation destination;
	private List<Passenger> passengers;
	private List<Vehicle> vehicles;
	private List<Solution> solutions;

	public List<Solution> getSolutions() {
		return solutions;
	}

	public void setSolutions(List<Solution> solutions) {
		this.solutions = solutions;
	}

	public GreedySearch(GeoLocation destination) {
		this.destination = destination;
		passengers = new ArrayList<Passenger>();
		vehicles = new ArrayList<Vehicle>();
		solutions = new ArrayList<Solution>();
	}

	public List<Passenger> getPassengers() {
		return passengers;
	}

	public void setPassengers(List<Passenger> passengers) {
		this.passengers = passengers;
	}

	public List<Vehicle> getVehicles() {
		return vehicles;
	}

	public void setVehicles(List<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}

	public static void main(String[] args) {
		GreedySearch problem = new GreedySearch(new GeoLocation(DESTINATION_LATITUDE, DESTINATION_LONGITUDE));
		problem.getPassengersAndVehicles();
		problem.findSolutions();
	}

	private void getPassengersAndVehicles() {
		Random ran = new Random();
		int nrOfPassengers = 400;
		int nrOfVehicles = 100;

		double latitudes[] = ran.doubles(nrOfPassengers, FAKE_LAT_START, FAKE_LAT_END).toArray();
		double longitudes[] = ran.doubles(nrOfPassengers, FAKE_LONG_START, FAKE_LONG_END).toArray();		

		for (int i = 0; i < nrOfPassengers; i++) {
			passengers.add(new Passenger(i, latitudes[i], longitudes[i]));
		}

		latitudes = ran.doubles(nrOfPassengers, FAKE_LAT_START, FAKE_LAT_END).toArray();
		longitudes = ran.doubles(nrOfPassengers, FAKE_LONG_START, FAKE_LONG_END).toArray();

		for (int i = 0; i < nrOfVehicles; i++) {
			vehicles.add(new Vehicle(i, new GeoLocation(latitudes[i], longitudes[i]), 5));
		}
	}

	private double getSolutionScore(Solution s) {
		return s.getDeviation() * TOTAL_DEVATION_WEIGHT
				+ s.getNrOfPassengersWithoutAride() * PASSENGER_LEFT_BEHIND_WEIGHT;
	}

	public void findSolutions() {

		for (int i = 0; i < MAX_SEARCH_ITERATIONS; i++) {
			List<Vehicle> copyVehicles2 = new ArrayList<Vehicle>();
			vehicles.forEach(v -> copyVehicles2.add(v.clone()));
			List<Passenger> copyPassengers2 = new ArrayList<Passenger>();
			passengers.forEach(p -> copyPassengers2.add(p.clone()));

			Solution s = new Solution(copyVehicles2, copyPassengers2, destination, i);
			// greedySearchNearestToCurrentPosition(s);
			// s.printSolution();
			greedySearchSmallestDeviation(s);
			// s.printSolution();
			solutions.add(s);
		}

		Solution bestSolution = getBestSolution();

		bestSolution.printSolution();
		bestSolution.drawRoutes("greedy", FAKE_LONG_START, FAKE_LAT_START, FAKE_LONG_END, FAKE_LAT_END);

	}

	public Solution getBestSolution() {
		Solution bestSolution = solutions.stream().min(new Comparator<Solution>() {
			@Override
			public int compare(Solution o1, Solution o2) {
				return Double.compare(getSolutionScore(o1), getSolutionScore(o2));
			}
		}).get();
		return bestSolution;
	}

	private void greedySearchSmallestDeviation(Solution s) {
		for (Vehicle v : s.getVehicless()) {

			// sort all remaining passengers by the deviation from the original route
			List<Passenger> sortedByDeviation = v
					.sortPassengersBySmallestDeviationFromRoute(s.getPassengersWithoutAride(), destination);

			// get the passengers with the smallest deviation that can be fitted in the car
			int numberOfPassengersThatCanBeFitted = Math.min(v.getCapacity(), sortedByDeviation.size());
			List<Passenger> passengersForThisCar = new ArrayList<Passenger>(numberOfPassengersThatCanBeFitted);
			for (int i = 0; i < numberOfPassengersThatCanBeFitted; i++) {
				Passenger nextPassengerWithSmallestDeviation = sortedByDeviation.get(0);
				passengersForThisCar.add(nextPassengerWithSmallestDeviation);
				sortedByDeviation.remove(nextPassengerWithSmallestDeviation);
			}

			// greedy add them in the order dictated by their distance from the vehicle
			while (v.hasSeatsAvalable()) {
				Passenger nearestPassenger = v.getNearestPassenger(passengersForThisCar);

				if (nearestPassenger != null) {
					// calculate the new total deviation that will be introduced if we take this
					// passenger
					double newPossibleDeviationDev = 100
							* (((v.getTotalCost() + v.getCurrentLocation().getDistanceToLocation(nearestPassenger)
									+ nearestPassenger.getDistanceToLocation(destination))
									/ (v.getTotalCost() + v.getCurrentLocation().getDistanceToLocation(destination)))
									- 1);
					if (newPossibleDeviationDev <= s.getMaxAcceptedDeviationPerVehicle()) {
						v.addPassenger(nearestPassenger);
						passengersForThisCar.remove(nearestPassenger);
						// remove the passenger from the original list of available passengers
						// a passenger can only be present in one car
						s.getPassengersWithoutAride().remove(nearestPassenger);
					} else {
						// rejected because of max deviation
						/*
						 * System.out.println("Passenger " + nearestPassenger.getId() +
						 * " rejected because the total added deviation is " + newPossibleDeviationDev);
						 */
						break;
					}
				} else {
					// no more passengers
					break;
				}
			}
			// go to final destination
			v.addDestination(destination);
		}
	}
}
