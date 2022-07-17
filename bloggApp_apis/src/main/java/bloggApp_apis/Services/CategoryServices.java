package bloggApp_apis.Services;

import java.util.List;

import bloggApp_apis.PayloadsOrDTOs.CategoryDTO;


public interface CategoryServices {

	CategoryDTO createCategory(CategoryDTO categoryDTO);
	CategoryDTO updateCategory(CategoryDTO categoryDTO, int id);
	CategoryDTO getCategoryById(int id);
	List<CategoryDTO> getAllCategories();
	void deleteCategory(int id);
}
