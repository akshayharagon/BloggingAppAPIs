package bloggApp_apis.Services;

import bloggApp_apis.PayloadsOrDTOs.CommentDTO;

public interface CommentService {
	
	
	public CommentDTO createComment(CommentDTO commentDTO, int id);
	public void deleteComment(int id);

}
