package keysight.ixia.hackathon.ixride_server.greedy;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class Solution implements Comparable<Solution> {

	private GeoLocation destination;
	private List<Vehicle> vehicless = new ArrayList<Vehicle>();
	private List<Passenger> passengersWithoutAride = new ArrayList<Passenger>();
	private double maxAcceptedDeviationPerVehicle;

	public Solution(List<Vehicle> vehicless, List<Passenger> passengers, GeoLocation destination,
			double maxAcceptedDeviationPerVehicle) {
		this.destination = destination;
		this.passengersWithoutAride = passengers;
		this.vehicless = vehicless;
		this.maxAcceptedDeviationPerVehicle = maxAcceptedDeviationPerVehicle;
	}

	public double getMaxAcceptedDeviationPerVehicle() {
		return maxAcceptedDeviationPerVehicle;
	}

	public List<Vehicle> getVehicless() {
		return vehicless;
	}

	public List<Passenger> getPassengersWithoutAride() {
		return passengersWithoutAride;
	}

	public double getCost() {
		return vehicless.stream().mapToDouble(v -> v.getTotalCost()).sum();
	}

	public double getDeviation() {
		return 100 * (getCost() / getMinimumCost() - 1);
	}

	public int getNrOfPassengersWithoutAride() {
		return passengersWithoutAride.size();
	}

	private double getMinimumCost() {
		return vehicless.stream().mapToDouble(v -> v.getOriginLocation().getDistanceToLocation(destination)).sum();
	}

	public void printSolution() {
		this.vehicless.forEach(v -> System.out.println(v.toString()));
		System.out.println("Max Accepted Deviation " + getMaxAcceptedDeviationPerVehicle() + " Total cost " + getCost()
				+ " Total deviation " + getDeviation() + " minimum cost " + getMinimumCost() + " with no ride "
				+ getNrOfPassengersWithoutAride());
	}

	public void drawRoutes(String fileName, double x0, double y0, double x1, double y1) {

		int imageDimension = 800;

		double pixelsForDistanceX = (double) imageDimension / (Math.abs(x1 - x0));
		double pixelsForDistanceY = (double) imageDimension / (Math.abs(y1 - y0));

		BufferedImage output = new BufferedImage(imageDimension, imageDimension, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = output.createGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, imageDimension, imageDimension);
		g.setColor(Color.BLACK);

		// Draw Route
		int nrOfVehicles = this.getVehicless().size();
		for (int i = 0; i < nrOfVehicles; i++) {

			Vehicle vehicle = this.getVehicless().get(i);
			List<GeoLocation> route = vehicle.getRoute();

			for (int j = 1; j < route.size(); j++) {

				GeoLocation wayPoint = route.get(j - 1);
				int ii1 = (int) ((wayPoint.getLongitude() - x0) * pixelsForDistanceX);
				int jj1 = (int) ((wayPoint.getLatitude() - y0) * pixelsForDistanceY);

				wayPoint = route.get(j);
				int ii2 = (int) ((wayPoint.getLongitude() - x0) * pixelsForDistanceX);
				int jj2 = (int) ((wayPoint.getLatitude() - y0) * pixelsForDistanceY);

				g.drawLine(ii1, jj1, ii2, jj2);
			}
		}

		/*
		 * for (int i = 0; i < s.Vehicles.length; i++) { for (int j = 0; j <
		 * s.Vehicles[i].Route.size(); j++) {
		 * 
		 * Node n = s.Vehicles[i].Route.get(j);
		 * 
		 * int ii = (int) ((double) (A) * ((n.Node_X - minX) / (maxX - minX) - 0.5) +
		 * (double) mX / 2) + margin; int jj = (int) ((double) (B) * (0.5 - (n.Node_Y -
		 * minY) / (maxY - minY)) + (double) mY / 2) + margin; if (i != 0) {
		 * g.fillOval(ii - 3 * marginNode, jj - 3 * marginNode, 6 * marginNode, 6 *
		 * marginNode); // 2244 String id = Integer.toString(n.NodeId); g.drawString(id,
		 * ii + 6 * marginNode, jj + 6 * marginNode); // 88 } else { g.fillRect(ii - 3 *
		 * marginNode, jj - 3 * marginNode, 6 * marginNode, 6 * marginNode); // 4488
		 * String id = Integer.toString(n.NodeId); g.drawString(id, ii + 6 * marginNode,
		 * jj + 6 * marginNode); // 88 } }
		 * 
		 * }
		 */

		/*
		 * String cst = "VRP solution for " + s.NoOfCustomers + " customers with Cost: "
		 * + s.; g.drawString(cst, 10, 10);
		 */
		fileName = fileName + ".png";
		File f = new File(fileName);
		try {
			ImageIO.write(output, "PNG", f);
		} catch (IOException ex) {
			// Logger.getLogger(s.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	@Override
	public int compareTo(Solution o) {

		return 0;
	}
}
