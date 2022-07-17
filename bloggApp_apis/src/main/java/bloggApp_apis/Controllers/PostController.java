package bloggApp_apis.Controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import bloggApp_apis.Configurations.AppConstants;
import bloggApp_apis.PayloadsOrDTOs.APIResponse;
import bloggApp_apis.PayloadsOrDTOs.PostDTO;
import bloggApp_apis.PayloadsOrDTOs.PostResponse;
import bloggApp_apis.Services.FileService;
import bloggApp_apis.Services.PostService;

@RestController
@RequestMapping("/api/")
public class PostController {

	
	@Autowired
	private PostService postService;
	@Autowired
	private FileService fileService;
	@Value("${project.image}")
	private String path;
	
	@PostMapping("/user/{user_id}/category/{category_id}/posts")
	public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO, @PathVariable int user_id, @PathVariable int category_id ){

		PostDTO createPost = postService.createPost(postDTO, user_id, category_id);
		return new ResponseEntity<PostDTO>(createPost, HttpStatus.CREATED);
	}

	@GetMapping("/user/{user_id}/posts")
	public ResponseEntity<List<PostDTO>> getPostByUser(@PathVariable int user_id){
		List<PostDTO> posts = postService.getPostsByUser(user_id);
		return new ResponseEntity<List<PostDTO>>(posts, HttpStatus.OK);
	}
	
	@GetMapping("/category/{category_id}/posts")
	public ResponseEntity<List<PostDTO>> getPotByCategory(@PathVariable int category_id){
		List<PostDTO> posts = postService.getPostsByCategory(category_id);
		return new ResponseEntity<List<PostDTO>>(posts, HttpStatus.OK);
	}	
	
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value="pageNumber", defaultValue=AppConstants.PAGE_NUMBER, required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false)String sortDir)
	{
		PostResponse pr = postService.getAllPosts(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<PostResponse>(pr, HttpStatus.OK);
	}	
	
	@GetMapping("/posts/{id}")
	public ResponseEntity<PostDTO> getOnePostById(@PathVariable int id){
		PostDTO onePost = postService.getPostById(id);
		return new ResponseEntity<PostDTO>(onePost, HttpStatus.OK);
	}
	
	@DeleteMapping("/posts/{id}")
	public APIResponse deleteOnePostById(@PathVariable int id){
		postService.deletePost(id);
		return new APIResponse("Post successfully deleted", true);
	}
	
	@PutMapping("/posts/{id}")
	public ResponseEntity<PostDTO> updateOnePostById(@RequestBody PostDTO postDTO, @PathVariable int id){
		PostDTO updatePost = postService.updatePost(postDTO, id);
		return new ResponseEntity<PostDTO>(updatePost, HttpStatus.OK);
	
	}
	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDTO>> searchPostByTitle(@PathVariable("keywords") String keywords){
		List<PostDTO> result = postService.searchPosts(keywords);
		return new ResponseEntity<List<PostDTO>>(result, HttpStatus.OK);
	}
	
	@PostMapping("/post/image/upload/{id}")
	public ResponseEntity<PostDTO> uploadPostImage(
			@RequestParam("image") MultipartFile image,
			@PathVariable int id) throws IOException{
		PostDTO postDTO = postService.getPostById(id);
		String fileName = fileService.uploadImage(path, image);
		
		postDTO.setImageName(fileName);
		PostDTO updatePost = postService.updatePost(postDTO, id);
		return new ResponseEntity<PostDTO>(updatePost, HttpStatus.OK);
	}
	
	@GetMapping(value="/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response) throws Exception {
		InputStream resource = fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
	
}
