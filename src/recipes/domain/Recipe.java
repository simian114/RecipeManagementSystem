package recipes.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int recipeId;
    private String name;
    private String description;

    // auto set
    private LocalDateTime date;

    // case sensitive
    private String category;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<Ingredient> ingredients;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<Direction> directions;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @PrePersist
    @PreUpdate
    private void onCreate() {
        this.date = LocalDateTime.now();
    }
}
