/**
 * 
 */
package keysight.ixia.hackathon.ixride_server.greedy;

/**
 * @author ovapostu
 *
 */
public class GeoLocation implements Cloneable {
	private double latitude;
	private double longitude;

	public GeoLocation(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (!(other instanceof GeoLocation))
			return false;
		GeoLocation otherLocation = (GeoLocation) other;
		return otherLocation.latitude == latitude && otherLocation.longitude == longitude;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 71 * hash + (int) this.latitude;
		hash = 71 * hash + (int) this.longitude;
		return hash;
	}

	public double getDistanceToLocation(GeoLocation other) {
		double dLat = getLatitude() - other.getLatitude();
		double dLong = getLongitude() - other.getLongitude();
		return Math.sqrt(dLat * dLat + dLong * dLong);
	}

	@Override
	public GeoLocation clone() {
		return new GeoLocation(this.latitude, this.longitude);
	}

}
