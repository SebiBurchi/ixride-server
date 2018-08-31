/**
 * 
 */
package keysight.ixia.hackathon.ixride_server.greedy;

/**
 * @author ovapostu
 *
 */
public class Passenger extends GeoLocation implements Cloneable {

	private long id;

	public Passenger(long id, double latitude, double longitude) {
		super(latitude, longitude);
		this.id = id;
	}

	public long getId() {
		return id;
	}

	@Override
	public Passenger clone() {
		return new Passenger(this.id, this.getLatitude(), this.getLongitude());
	}

}
