package bloggApp_apis.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import bloggApp_apis.Entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
