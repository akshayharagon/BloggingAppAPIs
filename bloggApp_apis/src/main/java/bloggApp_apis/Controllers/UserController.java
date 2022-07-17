package bloggApp_apis.Controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bloggApp_apis.PayloadsOrDTOs.APIResponse;
import bloggApp_apis.PayloadsOrDTOs.UserDTO;
import bloggApp_apis.Services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/")
	public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO){
		UserDTO createdUserDto = userService.createUser(userDTO);
		return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<UserDTO> updateuser(@Valid @RequestBody UserDTO userDTO, @PathVariable int id){
		UserDTO updatedUser = userService.updateUser(userDTO, id);
		return ResponseEntity.ok(updatedUser);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<APIResponse> deleteuser(@PathVariable int id) {
		userService.deleteUser(id);
		return new ResponseEntity<APIResponse>(new APIResponse("User Deleted Successfully", true), HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<UserDTO>> getAllusers() {
		return ResponseEntity.ok(userService.getAllusers());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> getSingleuser(@PathVariable int id) {
		return ResponseEntity.ok(userService.getUserById(id));
	}
}
