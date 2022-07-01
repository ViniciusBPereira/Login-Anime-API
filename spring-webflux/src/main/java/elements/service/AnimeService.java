package elements.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import elements.model.Anime;
import elements.repository.AnimeRepository;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class AnimeService {
    
    @Autowired
    private AnimeRepository repository;

    public Flux<Anime> getAll(){
        log.info("GetAll");
        return repository.findAll();
    }

    public Mono<Anime> getId(Integer id){
        log.info("GetId");
        return repository.findById(id).switchIfEmpty(monoError());
    }

    public Mono<Anime> save(Anime anime){
        log.info("Save");
        return repository.save(anime);
    }

    public Mono<Void> update(Anime anime){
        log.info("Update");
        return getId(anime.getId()).switchIfEmpty(monoError())
                                   .map(a -> anime.withId(a.getId()))
                                   .flatMap(repository::save)
                                   .then();
    }

    public Mono<Void> delete(Integer id){
        log.info("Delete");
        return getId(id).flatMap(repository::delete);
    }

    private <T> Mono<T> monoError(){
        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime Not Found"));
    }

}
