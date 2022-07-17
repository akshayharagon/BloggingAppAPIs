package bloggApp_apis.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import bloggApp_apis.Entities.Category;
import bloggApp_apis.Entities.Post;
import bloggApp_apis.Entities.User;

public interface PostRepository extends JpaRepository<Post, Integer> {
	
	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
	
	List<Post> findByTitleContaining(String title);
}
