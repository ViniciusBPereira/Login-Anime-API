package elements.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import elements.model.Anime;

@Repository
public interface AnimeRepository extends ReactiveCrudRepository<Anime, Integer>{
    
}
