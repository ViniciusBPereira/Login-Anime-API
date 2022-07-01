package elements.util;

import elements.model.Anime;

public class AnimeCreator {
    
    public static Anime createAnime(){
        return Anime.builder().name("Fullmetal Alchemist").build();
    }
    public static Anime createValidAnime(){
        return Anime.builder().id(1).name("Fullmetal Alchemist").build();
    }
    public static Anime createValidUpdateAnime(){
        return Anime.builder().id(1).name("Fullmetal Alchemist Brotherhood").build();
    }


}
