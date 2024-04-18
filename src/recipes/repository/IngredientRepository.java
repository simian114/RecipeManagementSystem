package recipes.repository;

import org.springframework.data.repository.CrudRepository;
import recipes.domain.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, Integer> {
}
