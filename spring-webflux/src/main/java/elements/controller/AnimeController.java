package elements.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import elements.model.Anime;
import elements.service.AnimeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("/anime")
@RestController
@SecurityScheme(name = "Basic Authentication", type = SecuritySchemeType.HTTP, scheme = "basic")
public class AnimeController {

    @Autowired
    private AnimeService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "List All", tags = "anime", security = @SecurityRequirement(name = "Basic Authentication"))
    public Flux<Anime> getAll(){
        return service.getAll();
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "List All", tags = "anime", security = @SecurityRequirement(name = "Basic Authentication"))
    public Mono<Anime> getId(@PathVariable Integer id){
        return service.getId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "List All", tags = "anime", security = @SecurityRequirement(name = "Basic Authentication"))
    public Mono<Anime> save(@RequestBody Anime anime){
        return service.save(anime);
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT )
    @Operation(summary = "List All", tags = "anime", security = @SecurityRequirement(name = "Basic Authentication"))
    public Mono<Void> update(@PathVariable Integer id, @RequestBody Anime anime){
        return service.update(anime.withId(id));
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "List All", tags = "anime", security = @SecurityRequirement(name = "Basic Authentication"))
    public Mono<Void> delete(@PathVariable Integer id){
        return service.delete(id);
    }

}
