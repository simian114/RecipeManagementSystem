package recipes.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
public class Direction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int directionId;
    private String comment;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;
}
