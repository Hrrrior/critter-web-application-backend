package ee.taltech.critter;

import ee.taltech.critter.model.Game;
import ee.taltech.critter.model.Platform;
import ee.taltech.critter.repository.GameRepository;
import ee.taltech.critter.repository.PlatformRepository;
import ee.taltech.critter.service.GenreService;
import ee.taltech.critter.service.IgdbService;

import java.util.List;

public class DemoDatabase {


    static void setUpDemo(GameRepository gameRepository, GenreService genreService, PlatformRepository platformRepository) {


        IgdbService igdbController = new IgdbService();

        Game game = igdbController.findGameByName("final fantasy xv");
        Game game2 = igdbController.findGameByName("final fantasy vi");
        Game game3 = igdbController.findGameByName("persona 3");
        Game game4 = igdbController.findGameByName("sekiro");
        Game game5 = igdbController.findGameByName("bloodborne");
        Game game6 = igdbController.findGameByName("dark souls");
        Game game7 = igdbController.findGameByName("resident evil hd");
        Game game8 = igdbController.findGameByName("crash bandicoot");
        List<Game> games = List.of(
                game,
                game2, game3,
                game4,
                game5, game6,
                game7, game8
        );
        Platform platform = Platform.builder().name("PC (Microsoft Windows)").build();
        Platform platform2 = Platform.builder().name("PlayStation 4").build();
        Platform platform3 = Platform.builder().name("Xbox One").build();
        Platform platform4 = Platform.builder().name("Nintendo Switch").build();
        Platform platform5 = Platform.builder().name("PlayStation Vita").build();
        Platform platform6 = Platform.builder().name("Xbox 360").build();
        Platform platform7 = Platform.builder().name("PlayStation 3").build();
        List<Platform> platformList = List.of(platform, platform2, platform3, platform4, platform5, platform6, platform7);
        platformRepository.saveAll(platformList);
        genreService.saveFromIgdb();
        gameRepository.saveAll(games);
    }
}

