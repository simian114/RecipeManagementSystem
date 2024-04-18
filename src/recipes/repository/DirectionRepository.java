package recipes.repository;

import org.springframework.data.repository.CrudRepository;
import recipes.domain.Direction;

public interface DirectionRepository extends CrudRepository<Direction, Integer> {
}
