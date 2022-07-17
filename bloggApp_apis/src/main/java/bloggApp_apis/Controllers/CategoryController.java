package bloggApp_apis.Controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bloggApp_apis.PayloadsOrDTOs.APIResponse;
import bloggApp_apis.PayloadsOrDTOs.CategoryDTO;
import bloggApp_apis.PayloadsOrDTOs.UserDTO;
import bloggApp_apis.Services.CategoryServices;
import bloggApp_apis.Services.UserService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	private CategoryServices categoryService;

	@PostMapping("/")
	public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO){
		CategoryDTO createdCategoryDto = categoryService.createCategory(categoryDTO);
		return new ResponseEntity<>(createdCategoryDto, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO, @PathVariable int id){
		CategoryDTO updatedCategory = categoryService.updateCategory(categoryDTO, id);
		return new ResponseEntity<>(updatedCategory, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<APIResponse> deleteCategory(@PathVariable int id) {
		categoryService.deleteCategory(id);
		return new ResponseEntity<APIResponse>(new APIResponse("Category Deleted Successfully", true), HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<CategoryDTO>> getAllCategories() {
		return ResponseEntity.ok(categoryService.getAllCategories());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CategoryDTO> getSingleCategory(@PathVariable int id) {
		return ResponseEntity.ok(categoryService.getCategoryById(id));
	}
}
