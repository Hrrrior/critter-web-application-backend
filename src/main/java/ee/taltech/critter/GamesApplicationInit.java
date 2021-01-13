package ee.taltech.critter;

import ee.taltech.critter.repository.GameRepository;
import ee.taltech.critter.repository.PlatformRepository;
import ee.taltech.critter.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class GamesApplicationInit implements CommandLineRunner {
    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GenreService genreService;
    @Autowired
    private PlatformRepository platformRepository;

    @Override
    public void run(String... args) {
        DemoDatabase.setUpDemo(gameRepository, genreService, platformRepository);
    }
}
