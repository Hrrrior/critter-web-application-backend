package ee.taltech.critter.controller;

import ee.taltech.critter.model.Game;
import ee.taltech.critter.service.IgdbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("igdb")
@RestController
public class IgdbController {
    @Autowired
    private IgdbService igdbService;

    /*
POST. Add one new game by name.
 */
    @PostMapping
    public Game saveGameByName(@RequestBody String name) {
        return igdbService.saveFromIgdb(name);
    }

    /*
   POST. Add {limit} new games by name.
    */
    @PostMapping("{limit}")
    public List<Game> saveMultipleGamesByName(@RequestBody String name, @PathVariable int limit) {
        return igdbService.saveAllFromIgdb(name, limit);
    }
}
