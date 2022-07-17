package bloggApp_apis.PayloadsOrDTOs;

import lombok.Data;

@Data
public class JWTAuthRequest {
	
	private String email;
	private String password;

}
