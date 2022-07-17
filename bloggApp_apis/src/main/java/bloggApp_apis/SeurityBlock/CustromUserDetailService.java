package bloggApp_apis.SeurityBlock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import bloggApp_apis.Entities.User;
import bloggApp_apis.Exceptions.ResourceNotFoundException;
import bloggApp_apis.Repositories.UserRepository;

@Service
public class CustromUserDetailService implements UserDetailsService{

	
	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//load user from database by username
		User user = userRepo.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("user", "Email : "+username, 0));
		return user;
	}

}
