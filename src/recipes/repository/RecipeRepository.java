package recipes.repository;

import org.springframework.data.repository.CrudRepository;
import recipes.domain.Recipe;

import java.util.List;

public interface RecipeRepository extends CrudRepository<Recipe, Integer> {
    List<Recipe> findByNameIgnoreCaseContainsOrderByDateDesc(String name);
    List<Recipe> findByCategoryIgnoreCaseOrderByDateDesc(String category);
}
