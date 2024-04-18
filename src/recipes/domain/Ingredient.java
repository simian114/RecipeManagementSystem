package recipes.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Setter
@Getter
@Entity
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ingredientId;

    private String name;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;
}
