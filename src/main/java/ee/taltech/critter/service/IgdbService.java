package ee.taltech.critter.service;

import ee.taltech.critter.exception.ApiBadRequestException;
import ee.taltech.critter.exception.ApiNotFoundException;
import ee.taltech.critter.igdb.IgdbJsonParse;
import ee.taltech.critter.igdb.IgdbRequest;
import ee.taltech.critter.igdb.RequestTypes;
import ee.taltech.critter.model.Game;
import ee.taltech.critter.repository.GameRepository;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class IgdbService {
    @Autowired
    private GameRepository gameRepository;
    private final IgdbRequest igdbRequest = new IgdbRequest();
    private final IgdbJsonParse igdbJsonParse = new IgdbJsonParse();

    public List<Game> findAllGamesByName(String name, int limit) {
        String search = MessageFormat.format("search \"{0}\";", name);
        JSONArray jsonArray = igdbRequest.sendPost(RequestTypes.GAMES, search, limit);
        List<Game> gameList = new ArrayList<>();
        if (jsonArray == null) {
            throw new ApiNotFoundException("Game not found.");
        }
        for (int i = 0; i < jsonArray.length(); i++) {
            gameList.add(igdbJsonParse.jsonToGame(jsonArray.getJSONObject(i)));
        }
        return gameList;
    }

    public Game findGameByName(String name) {
        String search = MessageFormat.format("search \"{0}\";", name);
        JSONArray jsonObject = igdbRequest.sendPost(RequestTypes.GAMES, search, 1);
        if (jsonObject == null) {
            throw new ApiNotFoundException("Game not found.");
        }
        return igdbJsonParse
                .jsonToGame(jsonObject.getJSONObject(0));
    }

    public Game saveFromIgdb(String name) {
        Game game = findGameByName(name);
        if (game == null) {
            throw new ApiNotFoundException("Game not found.");
        } else if (gameRepository.existsById(game.getId())) {
            throw new ApiBadRequestException("Game already exists.");
        }
        return gameRepository.save(game);
    }

    public List<Game> saveAllFromIgdb(String name, int limit) {
        List<Game> gameList = findAllGamesByName(name, limit);
        if (gameList.size() == 0) {
            throw new ApiNotFoundException("Game not found.");
        }
        List<Game> validGamesList = new ArrayList<>();
        for (Game game : gameList) {
            if (!gameRepository.existsById(game.getId())) {
                validGamesList.add(game);
            }
        }
        return gameRepository.saveAll(validGamesList);
    }
}
