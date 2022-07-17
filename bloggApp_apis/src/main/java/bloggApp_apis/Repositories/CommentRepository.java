package bloggApp_apis.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import bloggApp_apis.Entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

}
