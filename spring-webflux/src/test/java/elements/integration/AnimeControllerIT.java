package elements.integration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import elements.model.Anime;
import elements.repository.AnimeRepository;
import elements.util.AnimeCreator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureWebTestClient
public class AnimeControllerIT {
    
    @MockBean
    private AnimeRepository repository;

    @Autowired
    private WebTestClient client;
    
    private final Anime anime = AnimeCreator.createValidAnime();

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
        client.get()
              .uri("/anime")
              .exchange()
              .expectStatus()
              .isOk()
              .expectBody()
              .jsonPath("$.[0].id").isEqualTo(anime.getId())
              .jsonPath("$.[0].name").isEqualTo(anime.getName());
    }

    @Test
    @DisplayName("Get Id")
    public void getId(){
        client.get()
              .uri("/anime/{id}", 1)
              .exchange()
              .expectStatus()
              .isOk()
              .expectBody(Anime.class)
              .isEqualTo(anime);
    }

    @Test
    @DisplayName("Get Id Error")
    public void getIdError(){
        BDDMockito.when(repository.findById(anyInt())).thenReturn(Mono.empty());
        client.get()
              .uri("/anime/{id}", 1)
              .exchange()
              .expectStatus()
              .isNotFound()
              .expectBody()
              .jsonPath("$.status").isEqualTo(404);
    }

    @Test
    @DisplayName("Save Anime")
    public void save(){
        client.post()
              .uri("/anime")
              .contentType(MediaType.APPLICATION_JSON)
              .body(BodyInserters.fromValue(AnimeCreator.createAnime()))
              .exchange()
              .expectStatus()
              .isCreated()
              .expectBody(Anime.class)
              .isEqualTo(anime);
    }

    @Test
    @DisplayName("Update Anime")
    public void update(){
        client.put()
              .uri("/anime/{id}", 1)
              .contentType(MediaType.APPLICATION_JSON)
              .body(BodyInserters.fromValue(anime))
              .exchange()
              .expectStatus()
              .isNoContent();
    }

    @Test
    @DisplayName("Update Anime Error")
    public void updateError(){
        BDDMockito.when(repository.findById(anyInt())).thenReturn(Mono.empty());        
        client.put()
              .uri("/anime/{id}", 1)
              .contentType(MediaType.APPLICATION_JSON)
              .body(BodyInserters.fromValue(anime))
              .exchange()
              .expectStatus()
              .isNotFound()
              .expectBody()
              .jsonPath("$.status").isEqualTo(404);
    }

    @Test
    @DisplayName("Delete Anime")
    public void delete(){
        client.delete()
              .uri("/anime/{id}", 1)
              .exchange()
              .expectStatus()
              .isNoContent();
    }

    @Test
    @DisplayName("Delete Anime Error")
    public void deleteError(){
        BDDMockito.when(repository.findById(anyInt())).thenReturn(Mono.empty());
        client.delete()
              .uri("/anime/{id}", 1)
              .exchange()
              .expectStatus()
              .isNotFound()
              .expectBody()
              .jsonPath("$.status").isEqualTo(404);
    }
    
     

}
