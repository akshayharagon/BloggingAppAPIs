package bloggApp_apis.SeriveImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bloggApp_apis.Entities.Category;
import bloggApp_apis.Exceptions.ResourceNotFoundException;
import bloggApp_apis.PayloadsOrDTOs.CategoryDTO;
import bloggApp_apis.Repositories.CategoryRepository;
import bloggApp_apis.Services.CategoryServices;
@Service
public class CategoryServiceImpl implements CategoryServices {

	@Autowired
	private CategoryRepository categoryrepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDTO createCategory(CategoryDTO categoryDTO) {
		Category category = modelMapper.map(categoryDTO, Category.class);
		Category addedCategory = categoryrepo.save(category);

		return modelMapper.map(addedCategory, CategoryDTO.class);
	}

	@Override
	public CategoryDTO updateCategory(CategoryDTO categoryDTO, int id) {
		Category category = categoryrepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("category", "Category Id", id));
		category.setCategory(categoryDTO.getCategory());
		category.setCategoyDescription(categoryDTO.getCategoyDescription());
		Category updatedCategory = categoryrepo.save(category);
		return modelMapper.map(updatedCategory, CategoryDTO.class);
	}

	@Override
	public CategoryDTO getCategoryById(int id) {
		Category category = categoryrepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("category", "Category Id", id));
		return modelMapper.map(category, CategoryDTO.class);
	}

	@Override
	public List<CategoryDTO> getAllCategories() {
		List<Category> categories = categoryrepo.findAll();
		List<CategoryDTO> categoryDTOs = categories.stream().map((category)-> modelMapper.map(category, CategoryDTO.class)).collect(Collectors.toList());
		return categoryDTOs;
	}

	@Override
	public void deleteCategory(int id) {
		Category category = categoryrepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("category", "Category Id", id));
		categoryrepo.delete(category);
	}

}
