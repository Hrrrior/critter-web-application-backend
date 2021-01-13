package ee.taltech.critter.service;

import ee.taltech.critter.exception.ApiNotFoundException;
import ee.taltech.critter.igdb.IgdbJsonParse;
import ee.taltech.critter.igdb.IgdbRequest;
import ee.taltech.critter.igdb.RequestTypes;
import ee.taltech.critter.model.Genre;
import ee.taltech.critter.repository.GenreRepository;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenreService {
    @Autowired
    private GenreRepository genreRepository;
    private final IgdbRequest igdbRequest = new IgdbRequest();
    private final IgdbJsonParse igdbJsonParse = new IgdbJsonParse();

    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    public Genre save(Genre genre) {
        return genreRepository.save(genre);
    }

    public List<Genre> saveFromIgdb() { return genreRepository.saveAll(findAllGenres()); }

    public void delete(Long id) {
        genreRepository.deleteById(id);
    }


    public List<Genre> findAllGenres() {
        JSONArray jsonArray = igdbRequest.sendPost(RequestTypes.GENRES, "", 30);
        List<Genre> genreList = new ArrayList<>();
        if (jsonArray == null) {
            throw new ApiNotFoundException("Genre not found.");
        }
        for (int i = 0; i < jsonArray.length(); i++) {
            genreList.add(igdbJsonParse.jsonToGenre(jsonArray.getJSONObject(i)));
        }
        return genreList;
    }
}
