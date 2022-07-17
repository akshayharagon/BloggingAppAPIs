package bloggApp_apis.PayloadsOrDTOs;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

import bloggApp_apis.Entities.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
	
	private int id;
	@NotEmpty 
	@Size(min = 4, message = "username must be more than 4 chars")
	private String name;
	@Email(message = "Email address is not valid")
	private String email;
	@NotEmpty 
	@Size(min = 3, max = 10, message = "password must be in betwwn 3-10 chars")
//	@Pattern(regexp = )
	private String password;
	@NotEmpty 
	private String about;
	private Set<RoleDTO> roles = new HashSet<>();

}
