package recipes.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class RecipeDto {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime date;

    @NotBlank
    @Size(min = 1, max = 50)
    private String category;

    @NotNull
    @Size(min = 1)
    private List<String> ingredients;

    @NotNull
    @Size(min = 1)
    private List<String> directions;

}
