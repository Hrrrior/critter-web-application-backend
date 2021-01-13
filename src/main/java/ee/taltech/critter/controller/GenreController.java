package ee.taltech.critter.controller;

import ee.taltech.critter.model.Genre;
import ee.taltech.critter.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("genres")
@RestController
public class GenreController {
    @Autowired
    private GenreService genreService;

    /*
    GET. Get all genre.
     */
    @GetMapping
    public List<Genre> getGenre() {
        return genreService.findAll();
    }

    /*
    POST. Save new genre to db.
     */
    @PostMapping
    public Genre saveGenre(@RequestBody Genre genre) {
        return genreService.save(genre);
    }

    /*
    POST. Save new genre to db.
     */
    @PostMapping("/igdb")
    public List<Genre> saveAllGenresFromIgdb() {
        return genreService.saveFromIgdb();
    }

    /*
    DELETE. Delete genre with ID.
     */
    @DeleteMapping("{id}")
    public void deleteGenreById(@PathVariable Long id) {
        genreService.delete(id);
    }
}
