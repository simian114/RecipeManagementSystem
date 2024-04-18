package recipes.web.mapper;

import org.springframework.stereotype.Component;
import recipes.domain.Direction;
import recipes.domain.Ingredient;
import recipes.domain.Recipe;
import recipes.domain.User;
import recipes.web.dto.RecipeDto;

import java.util.List;

@Component
public class RecipeMapper {
    public Recipe toEntity(RecipeDto recipeDto, User user) {
        Recipe recipe = new Recipe();
        if (user != null) {
            recipe.setUser(user);
        }
        recipe.setName(recipeDto.getName());
        recipe.setDescription(recipeDto.getDescription());
        recipe.setCategory(recipeDto.getCategory());
        List<Ingredient> ingredients = recipeDto.getIngredients().stream()
                .map(r -> {
                    Ingredient ingredient = new Ingredient();
                    ingredient.setName(r);
                    ingredient.setRecipe(recipe);
                    return ingredient;
                }).toList();
        List<Direction> directions = recipeDto.getDirections().stream()
                .map(d -> {
                    Direction direction = new Direction();
                    direction.setComment(d);
                    direction.setRecipe(recipe);
                    return direction;
                }).toList();
        recipe.setIngredients(ingredients);
        recipe.setDirections(directions);
        return recipe;
    }

    public RecipeDto toDto(Recipe recipe) {
        return RecipeDto.builder()
                .name(recipe.getName())
                .description(recipe.getDescription())
                .ingredients(recipe.getIngredients().stream().map(Ingredient::getName).toList())
                .directions(recipe.getDirections().stream().map(Direction::getComment).toList())
                .date(recipe.getDate())
                .category(recipe.getCategory())
                .build();
    }
}
