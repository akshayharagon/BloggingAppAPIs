package bloggApp_apis.Entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(length = 100, nullable = false)
	private String title;
	@Column(length=10000)
	private String content;
	private String imageName;
	private Date addedDate;
	@ManyToOne
	private Category category;
	@ManyToOne
	private User user;
	@OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Comment> comments= new HashSet<>();
}
