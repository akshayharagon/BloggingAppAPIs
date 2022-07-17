package bloggApp_apis.Services;

import java.util.List;

import bloggApp_apis.PayloadsOrDTOs.UserDTO;

public interface UserService {

	UserDTO registerNewUser(UserDTO userDTO);
	UserDTO createUser(UserDTO user);
	UserDTO updateUser(UserDTO user, int id);
	UserDTO getUserById(int id);
	List<UserDTO> getAllusers();
	void deleteUser(int id);
}
