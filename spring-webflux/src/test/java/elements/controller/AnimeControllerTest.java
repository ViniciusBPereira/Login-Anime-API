package elements.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import elements.model.Anime;
import elements.service.AnimeService;
import elements.util.AnimeCreator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
public class AnimeControllerTest {
    
    @InjectMocks
    private AnimeController controller;

    @Mock
    private AnimeService service;

    private final Anime anime = AnimeCreator.createAnime();
   
    @BeforeEach
    public void setUp(){
        BDDMockito.when(service.getAll()).thenReturn(Flux.just(anime));
        BDDMockito.when(service.getId(anyInt())).thenReturn(Mono.just(anime));
        BDDMockito.when(service.save(any())).thenReturn(Mono.just(anime));
        BDDMockito.when(service.delete(anyInt())).thenReturn(Mono.empty());
        BDDMockito.when(service.update(any())).thenReturn(Mono.empty());
    }

    @Test
    @DisplayName("Get All")
    public void getAll(){
        StepVerifier.create(controller.getAll())
                    .expectSubscription()
                    .expectNext(anime)
                    .verifyComplete();
    }

    @Test
    @DisplayName("Get Id")
    public void getId(){
        StepVerifier.create(controller.getId(1))
                    .expectSubscription()
                    .expectNext(anime)
                    .verifyComplete();
    }

    @Test
    @DisplayName("Save Anime")
    public void save(){
        StepVerifier.create(controller.save(anime))
                    .expectSubscription()
                    .expectNext(anime)
                    .verifyComplete();
    }

    @Test
    @DisplayName("Update Anime")
    public void update(){
        StepVerifier.create(controller.update(1,AnimeCreator.createValidUpdateAnime()))
                    .expectSubscription()                    
                    .verifyComplete();
    }

    @Test
    @DisplayName("Delete Anime")
    public void delete(){
        StepVerifier.create(controller.delete(1))
                    .expectSubscription()
                    .verifyComplete();
    }
}
