package keysight.ixia.hackathon.ixride_server.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthResponse {

	@JsonProperty
	private Long id;

	@JsonProperty
	private String username;

	@JsonProperty
	private String password;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
