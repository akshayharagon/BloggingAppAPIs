package bloggApp_apis.SeriveImpl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import bloggApp_apis.Entities.Category;
import bloggApp_apis.Entities.Post;
import bloggApp_apis.Entities.User;
import bloggApp_apis.Exceptions.ResourceNotFoundException;
import bloggApp_apis.PayloadsOrDTOs.CategoryDTO;
import bloggApp_apis.PayloadsOrDTOs.PostDTO;
import bloggApp_apis.PayloadsOrDTOs.PostResponse;
import bloggApp_apis.PayloadsOrDTOs.UserDTO;
import bloggApp_apis.Repositories.CategoryRepository;
import bloggApp_apis.Repositories.PostRepository;
import bloggApp_apis.Repositories.UserRepository;
import bloggApp_apis.Services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private CategoryRepository categoryRepo;

	@Override
	public PostDTO createPost(PostDTO postDTO, int user_id, int category_id) {

		User user = userRepo.findById(user_id)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User Id", user_id));
		Category category = categoryRepo.findById(category_id)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", category_id));

		Post post = modelMapper.map(postDTO, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);

		Post newPost = postRepo.save(post);
		return modelMapper.map(newPost, PostDTO.class);
	}

	@Override
	public PostDTO updatePost(PostDTO postDTO, int id) {
		Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", id));
		post.setTitle(postDTO.getTitle());
		post.setContent(postDTO.getContent());
		post.setImageName(postDTO.getImageName());
		Post updatedPost = postRepo.save(post);
		return modelMapper.map(updatedPost, PostDTO.class);
	}

	@Override
	public PostDTO getPostById(int id) {
		Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", id));
		return modelMapper.map(post, PostDTO.class);
	}

	@Override
	public void deletePost(int id) {

		Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "post id", id));
		postRepo.delete(post);
	}

	@Override
	public PostResponse getAllPosts(int pageNumber, int pageSize, String sortBy, String sortDir) {

		Sort sort = (sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		
		Pageable p=PageRequest.of(pageNumber, pageSize, sort);
		
		Page<Post> pagePost = postRepo.findAll(p);
		List<Post> allPosts = pagePost.getContent();
		
		List<PostDTO> postDTOs = allPosts.stream().map((post) -> modelMapper.map(post, PostDTO.class))
				.collect(Collectors.toList());
		PostResponse pr= new PostResponse();
		
		pr.setContent(postDTOs);
		pr.setPageNumber(pagePost.getNumber());
		pr.setPageSize(pagePost.getSize());
		pr.setTotalElements(pagePost.getTotalElements());
		pr.setTotalPages(pagePost.getTotalPages());
		pr.setLastPage(pagePost.isLast());

		return pr;
	}

	@Override
	public List<PostDTO> getPostsByCategory(int category_id) {
		Category category = categoryRepo.findById(category_id)
				.orElseThrow(() -> new ResourceNotFoundException("category", "Category Id", category_id));
		List<Post> posts = postRepo.findByCategory(category);
		List<PostDTO> postDTOs = posts.stream().map((post) -> modelMapper.map(post, PostDTO.class))
				.collect(Collectors.toList());
		return postDTOs;
	}

	@Override
	public List<PostDTO> getPostsByUser(int user_id) {
		User user = userRepo.findById(user_id)
				.orElseThrow(() -> new ResourceNotFoundException("user", "User Id", user_id));
		List<Post> posts = postRepo.findByUser(user);
		List<PostDTO> postDTOs = posts.stream().map((post) -> modelMapper.map(post, PostDTO.class))
				.collect(Collectors.toList());
		return postDTOs;

	}

	@Override
	public List<PostDTO> searchPosts(String keyword) {
		List<Post> posts = postRepo.findByTitleContaining(keyword);
		List<PostDTO> postDTOs = posts.stream().map((post)->modelMapper.map(post, PostDTO.class)).collect(Collectors.toList()); 
		return postDTOs;
	}

}
