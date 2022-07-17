package bloggApp_apis.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bloggApp_apis.Exceptions.APIException;
import bloggApp_apis.PayloadsOrDTOs.JWTAUthResponse;
import bloggApp_apis.PayloadsOrDTOs.JWTAuthRequest;
import bloggApp_apis.PayloadsOrDTOs.UserDTO;
import bloggApp_apis.Services.UserService;
import bloggApp_apis.SeurityBlock.CustromUserDetailService;
import bloggApp_apis.SeurityBlock.JWTokenHelper;
import jdk.nashorn.internal.ir.RuntimeNode.Request;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

	@Autowired
	private JWTokenHelper jwtTokenHelper;

	@Autowired
	private UserDetailsService userdetailService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/login")
	public ResponseEntity<JWTAUthResponse> createToken(@RequestBody JWTAuthRequest request) throws Exception {
		authenticate(request.getEmail(), request.getPassword());

		UserDetails userDetails = userdetailService.loadUserByUsername(request.getEmail());
		String token = jwtTokenHelper.generateToken(userDetails);
		JWTAUthResponse response = new JWTAUthResponse();
		response.setToken(token);
		return new ResponseEntity<JWTAUthResponse>(response, HttpStatus.OK);
	}

	private void authenticate(String email, String password) throws Exception {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email,
				password);
		try {
			authenticationManager.authenticate(authenticationToken);
		} catch (BadCredentialsException e) {
			System.out.println("bad credentials");
			throw new APIException("invalid username or pasword");
		}

	}
//	?? register new user api
	@PostMapping("/register")
			public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO){
				UserDTO registerdUser = userService.registerNewUser(userDTO);
				return new ResponseEntity<UserDTO>(registerdUser, HttpStatus.OK);
			}
	
}
