package recipes.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recipes.domain.User;
import recipes.service.RecipeService;
import recipes.web.config.argumentResolver.annotation.LoginUser;
import recipes.web.dto.RecipeDto;

import javax.validation.Valid;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/recipe")
public class RecipeController {
    private final RecipeService recipeService;

    @PostMapping("/new")
    ResponseEntity<?> addRecipe(@Valid @RequestBody RecipeDto recipeDto,
                                @LoginUser User currentUser
    ) {
        return ResponseEntity.ok(Map.of("id", this.recipeService.addRecipe(recipeDto, currentUser)));
    }

    @PutMapping("{id}")
    ResponseEntity<?> updateRecipe(@PathVariable("id") Integer id,
                                   @Valid @RequestBody RecipeDto recipeDto,
                                   @LoginUser User currentUser
    ) {
        this.recipeService.updateRecipe(id, recipeDto, currentUser);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getRecipe(@PathVariable("id") int id) {
        return ResponseEntity.ok(this.recipeService.getRecipe(id));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteRecipe(@PathVariable("id") int id,
                                   @LoginUser User currentUser
    ) {
        this.recipeService.deleteRecipe(id, currentUser);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/search", params = {"name", "!category"})
    ResponseEntity<?> searchRecipeByName(@RequestParam(value = "name", required = false, defaultValue = "") String name) {
        return ResponseEntity.ok(this.recipeService.findByName(name));
    }

    @GetMapping(value = "/search", params = {"category", "!name"})
    ResponseEntity<?> searchRecipeByCategory(@RequestParam(value = "category", required = false, defaultValue = "") String category) {
        return ResponseEntity.ok(this.recipeService.findByCategory(category));
    }
}
