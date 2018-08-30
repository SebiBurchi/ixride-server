/**
 * 
 */
package keysight.ixia.hackathon.ixride_server.greedy;

/**
 * @author ovapostu
 *
 */
public class Passenger extends GeoLocation implements Cloneable {

	private int id;

	public Passenger(int id, double latitude, double longitude) {
		super(latitude, longitude);
		this.id = id;
	}

	public int getId() {
		return id;
	}

	@Override
	public Passenger clone() {
		return new Passenger(this.id, this.getLatitude(), this.getLongitude());
	}

}
