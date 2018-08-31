/**
 * 
 */
package keysight.ixia.hackathon.ixride_server.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ovapostu
 *
 */
public class RouteWayPoint implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7686359447321079739L;

	@JsonProperty
	private Long index;

	@JsonProperty
	private double latitude;

	@JsonProperty
	private double longitude;

	@JsonProperty
	private Long profileId;

	public Long getIndex() {
		return index;
	}

	public void setIndex(Long index) {
		this.index = index;
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

	public Long getProfileId() {
		return profileId;
	}

	public void setProfileId(Long profileId) {
		this.profileId = profileId;
	}	

}
