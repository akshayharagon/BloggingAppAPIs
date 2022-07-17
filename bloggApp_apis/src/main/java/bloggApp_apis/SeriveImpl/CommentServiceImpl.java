package bloggApp_apis.SeriveImpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bloggApp_apis.Entities.Comment;
import bloggApp_apis.Entities.Post;
import bloggApp_apis.Exceptions.ResourceNotFoundException;
import bloggApp_apis.PayloadsOrDTOs.CommentDTO;
import bloggApp_apis.Repositories.CommentRepository;
import bloggApp_apis.Repositories.PostRepository;
import bloggApp_apis.Services.CommentService;
@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private PostRepository postRepo;
	
	@Autowired
	private CommentRepository commentRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CommentDTO createComment(CommentDTO commentDTO, int id) {
		Post post = postRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("post", "post id", id));
		Comment comment = modelMapper.map(commentDTO, Comment.class);
		comment.setPost(post);
		Comment savedComment = commentRepo.save(comment);
		return modelMapper.map(savedComment, CommentDTO.class);
	}

	@Override
	public void deleteComment(int id) {
		Comment comment = commentRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("comment", "comment id", id));
		commentRepo.delete(comment);
	}

}
