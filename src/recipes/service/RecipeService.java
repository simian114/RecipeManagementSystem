package recipes.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import recipes.domain.Recipe;
import recipes.domain.User;
import recipes.repository.DirectionRepository;
import recipes.repository.IngredientRepository;
import recipes.repository.RecipeRepository;
import recipes.web.dto.RecipeDto;
import recipes.web.exception.exceptions.forbidden.ForbiddenBasicException;
import recipes.web.exception.exceptions.notFound.NotFoundBasicException;
import recipes.web.mapper.RecipeMapper;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final IngredientRepository ingredientRepository;
    private final DirectionRepository directionRepository;
    private final RecipeRepository recipeRepository;
    private final RecipeMapper recipeMapper;

    public RecipeDto getRecipe(int id) {
        Recipe recipe = this.recipeRepository.findById(id).orElseThrow(NotFoundBasicException::new);
        return this.recipeMapper.toDto(recipe);
    }

    public long addRecipe(RecipeDto recipeDto, User currentUser) {
        Recipe entity = this.recipeMapper.toEntity(recipeDto, currentUser);
        entity.setUser(currentUser);
        Recipe save = this.recipeRepository.save(entity);
        return save.getRecipeId();
    }

    @Transactional
    public void updateRecipe(int id, RecipeDto recipeDto, User currentUser) {
        Recipe recipe = this.recipeRepository.findById(id).orElseThrow(NotFoundBasicException::new);
        if (recipe.getUser().getId() != currentUser.getId()) {
            throw new ForbiddenBasicException();
        }
        // delete all ingredients and directions
        this.ingredientRepository.deleteAll(recipe.getIngredients());
        this.directionRepository.deleteAll(recipe.getDirections());

        Recipe updatedRecipe = this.recipeMapper.toEntity(recipeDto, currentUser);

        updatedRecipe.setRecipeId(id);
        this.recipeRepository.save(updatedRecipe);
    }

    public void deleteRecipe(int id, User currentUser) {
        Recipe recipe = recipeRepository.findById(id).orElseThrow(NotFoundBasicException::new);
        if (recipe.getUser().getId() != currentUser.getId()) {
            throw new ForbiddenBasicException();
        }
        this.recipeRepository.delete(recipe);
    }

    public List<RecipeDto> findByName(String name) {
        return this.recipeRepository.findByNameIgnoreCaseContainsOrderByDateDesc(name).stream()
                .map(this.recipeMapper::toDto)
                .toList();
    }

    public List<RecipeDto> findByCategory(String category) {
        return this.recipeRepository.findByCategoryIgnoreCaseOrderByDateDesc(category).stream()
                .map(this.recipeMapper::toDto)
                .toList();
    }
}
