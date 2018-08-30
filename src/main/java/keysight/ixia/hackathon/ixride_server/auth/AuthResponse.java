package keysight.ixia.hackathon.ixride_server.auth;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthResponse {

    @JsonProperty
    private Long id;

    @JsonProperty
    private String username;

    @JsonProperty
    private String password;
}
