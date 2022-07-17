package bloggApp_apis.SeriveImpl;

import java.text.Collator;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import bloggApp_apis.Configurations.AppConstants;
import bloggApp_apis.Entities.Role;
import bloggApp_apis.Entities.User;
import bloggApp_apis.PayloadsOrDTOs.UserDTO;
import bloggApp_apis.Repositories.RoleRepository;
import bloggApp_apis.Repositories.UserRepository;
import bloggApp_apis.Services.UserService;
import bloggApp_apis.Exceptions.*;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Override
	public UserDTO createUser(UserDTO userdto) {
		User user = dtoToUser(userdto);
		User savedUser = userRepo.save(user);
		return userToDTo(savedUser);
	}

	@Override
	public UserDTO updateUser(UserDTO userDto, int id) {
		User user = userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User", "Id", id));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		User updatedUser = userRepo.save(user);
		UserDTO userDTo1 = userToDTo(updatedUser);
		return userDTo1;
	}

	@Override
	public UserDTO getUserById(int id) {
		User user = userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User", "Id", id));
		return userToDTo(user);
	}

	@Override
	public List<UserDTO> getAllusers() {
		List<User> users = userRepo.findAll();
		List<UserDTO> userDtos = users.stream().map(user->userToDTo(user)).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public void deleteUser(int id) {
		User user = userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User", "Id", id));
		userRepo.delete(user);
	}
	public User dtoToUser(UserDTO userdto) {
		User user=modelMapper.map(userdto, User.class);
//		user.setId(userdto.getId());
//		user.setName(userdto.getName());
//		user.setAbout(userdto.getAbout());
//		user.setEmail(userdto.getEmail());
//		user.setPassword(userdto.getPassword());
		return user;
	}
	
	public UserDTO userToDTo(User user) {
		UserDTO userdto = modelMapper.map(user, UserDTO.class);
//		userdto.setId(user.getId());
//		userdto.setName(user.getName());
//		userdto.setEmail(user.getEmail());
//		userdto.setAbout(user.getAbout());
//		userdto.setPassword(user.getPassword());
		return userdto;
	}

	@Override
	public UserDTO registerNewUser(UserDTO userDTO) {
		
		User user = modelMapper.map(userDTO, User.class);
		
//		Encoding the password
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
//		role giving as normal user only
		Role role = roleRepo.findById(AppConstants.NORMAL_USER).get();
		user.getRoles().add(role);
		User newUser = userRepo.save(user);
		
		
		return modelMapper.map(user, UserDTO.class);
		
		
		
	}
	
	}

