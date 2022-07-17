package bloggApp_apis.Services;

import java.util.List;

import bloggApp_apis.PayloadsOrDTOs.CategoryDTO;
import bloggApp_apis.PayloadsOrDTOs.PostDTO;
import bloggApp_apis.PayloadsOrDTOs.PostResponse;
import bloggApp_apis.PayloadsOrDTOs.UserDTO;

public interface PostService {

	PostDTO createPost(PostDTO postDTO, int user_id, int category_id);
	PostDTO updatePost(PostDTO postDTO, int post_id);
	PostDTO getPostById(int id);
	void deletePost(int id);
	PostResponse getAllPosts(int pageNumber, int pageSize, String sortBy, String sortDir);
	List<PostDTO> getPostsByCategory(int category_id);
	List<PostDTO> getPostsByUser(int user_id);
	List<PostDTO> searchPosts(String keyword);
	

}
