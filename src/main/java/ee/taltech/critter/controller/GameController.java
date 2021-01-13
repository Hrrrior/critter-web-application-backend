package ee.taltech.critter.controller;

import ee.taltech.critter.model.Game;
import ee.taltech.critter.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("games")
@RestController
public class GameController {
    @Autowired
    private GameService gameService;

    /*
    GET. Get all games.
     */
    @GetMapping
    public List<Game> getAllGames() {
        return gameService.findAll();
    }

    /*
    GET. Get game at ID.
     */
    @GetMapping("{id}")
    public Game getGameById(@PathVariable Long id) {
        return gameService.findById(id);
    }

    /*
    PUT. Replace game with ID with input game.
     */
//    @PutMapping("{id}")
//    public Game updateGameWithId(@RequestBody Game game, @PathVariable Long id) {
//        return gameService.update(game, id);
//    }

    /*
    DELETE. Delete game with ID.
     */
    @DeleteMapping("{id}")
    public void deleteGameWithId(@PathVariable Long id) {
        gameService.delete(id);
    }
}
