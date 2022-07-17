package bloggApp_apis.PayloadsOrDTOs;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ManyToOne;

import bloggApp_apis.Entities.Category;
import bloggApp_apis.Entities.Comment;
import bloggApp_apis.Entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class PostDTO {
	private int id;
	private String title;
	private String content;
	private String imageName;
	private Date addedDate;
	private CategoryDTO category;
	private UserDTO user;
	private Set<CommentDTO> comments= new HashSet<>();
}
