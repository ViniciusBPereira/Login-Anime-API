package elements.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import elements.model.Anime;
import elements.repository.AnimeRepository;
import elements.util.AnimeCreator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
public class AnimeServiceTest {
    
    @InjectMocks
    private AnimeService service;

    @Mock
    private AnimeRepository repository;

    private final Anime anime = AnimeCreator.createAnime();
   
    @BeforeEach
    public void setUp(){
        BDDMockito.when(repository.findAll()).thenReturn(Flux.just(anime));
        BDDMockito.when(repository.findById(anyInt())).thenReturn(Mono.just(anime));
        BDDMockito.when(repository.save(any())).thenReturn(Mono.just(anime));
        BDDMockito.when(repository.delete(any())).thenReturn(Mono.empty());
        BDDMockito.when(repository.save(AnimeCreator.createValidAnime())).thenReturn(Mono.empty());
    }

    @Test
    @DisplayName("Get All")
    public void getAll(){
        StepVerifier.create(service.getAll())
                    .expectSubscription()
                    .expectNext(anime)
                    .verifyComplete();
    }

    @Test
    @DisplayName("Get Id")
    public void getId(){
        StepVerifier.create(service.getId(1))
                    .expectSubscription()
                    .expectNext(anime)
                    .verifyComplete();
    }

    @Test
    @DisplayName("Get Id Error")
    public void getIdError(){
        BDDMockito.when(repository.findById(anyInt())).thenReturn(Mono.empty());
        StepVerifier.create(service.getId(1))
                    .expectSubscription()
                    .expectError(ResponseStatusException.class)
                    .verify();
    }

    @Test
    @DisplayName("Save Anime")
    public void save(){
        StepVerifier.create(service.save(anime))
                    .expectSubscription()
                    .expectNext(anime)
                    .verifyComplete();
    }

    @Test
    @DisplayName("Update Anime")
    public void update(){
        StepVerifier.create(service.update(AnimeCreator.createValidUpdateAnime()))
                    .expectSubscription()                    
                    .verifyComplete();
    }

    @Test
    @DisplayName("Update Anime Error")
    public void updateError(){
        BDDMockito.when(repository.findById(anyInt())).thenReturn(Mono.empty());        
        StepVerifier.create(service.update(AnimeCreator.createValidUpdateAnime()))
                    .expectSubscription()
                    .expectError(ResponseStatusException.class)                    
                    .verify();
    }

    @Test
    @DisplayName("Delete Anime")
    public void delete(){
        StepVerifier.create(service.delete(1))
                    .expectSubscription()
                    .verifyComplete();
    }

    @Test
    @DisplayName("Delete Anime Error")
    public void deleteError(){
        BDDMockito.when(repository.findById(anyInt())).thenReturn(Mono.empty());
        StepVerifier.create(service.delete(1))
                    .expectSubscription()
                    .expectError(ResponseStatusException.class)
                    .verify();
    }

}
