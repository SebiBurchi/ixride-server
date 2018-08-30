package keysight.ixia.hackathon.ixride_server.greedy;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

class Solution implements Comparable<Solution> {

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
		System.out.println("Max Deviation " + getMaxAcceptedDeviationPerVehicle() + " Total cost " + getCost()
				+ " Total deviation " + getDeviation() + " minimum cost " + getMinimumCost() + " with no ride "
				+ getNrOfPassengersWithoutAride());
	}

	public void drawRoutes(String fileName) {

		int VRP_Y = 800;
		int VRP_INFO = 200;
		int X_GAP = 600;
		int margin = 30;
		int marginNode = 1;

		int XXX = VRP_INFO + X_GAP;
		int YYY = VRP_Y;

		BufferedImage output = new BufferedImage(XXX, YYY, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = output.createGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, XXX, YYY);
		g.setColor(Color.BLACK);

		double minX = 0;
		double maxX = 100;
		double minY = 0;
		double maxY = 100;

		int mX = XXX - 2 * margin;
		int mY = VRP_Y - 2 * margin;

		int A, B;
		if ((maxX - minX) > (maxY - minY)) {
			A = mX;
			B = (int) ((double) (A) * (maxY - minY) / (maxX - minX));
			if (B > mY) {
				B = mY;
				A = (int) ((double) (B) * (maxX - minX) / (maxY - minY));
			}
		} else {
			B = mY;
			A = (int) ((double) (B) * (maxX - minX) / (maxY - minY));
			if (A > mX) {
				A = mX;
				B = (int) ((double) (A) * (maxY - minY) / (maxX - minX));
			}
		}

		// Draw Route
		int nrOfVehicles = this.getVehicless().size();
		for (int i = 0; i < nrOfVehicles; i++) {
			for (int j = 1; j < this.getVehicless().get(i).getRoute().size(); j++) {
				GeoLocation n = this.getVehicless().get(i).getRoute().get(j - 1);

				int ii1 = (int) ((double) (A) * ((n.getLatitude() - minX) / (maxX - minX) - 0.5) + (double) mX / 2)
						+ margin;
				int jj1 = (int) ((double) (B) * (0.5 - (n.getLongitude() - minY) / (maxY - minY)) + (double) mY / 2)
						+ margin;

				n = this.getVehicless().get(i).getRoute().get(j);

				int ii2 = (int) ((double) (A) * ((n.getLatitude() - minX) / (maxX - minX) - 0.5) + (double) mX / 2)
						+ margin;
				int jj2 = (int) ((double) (B) * (0.5 - (n.getLongitude() - minY) / (maxY - minY)) + (double) mY / 2)
						+ margin;

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
