package ee.taltech.critter.service;

import ee.taltech.critter.exception.ApiNotFoundException;
import ee.taltech.critter.model.Game;
import ee.taltech.critter.repository.GameRepository;
import ee.taltech.critter.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    public List<Game> findAll() {
        return gameRepository.findAll();
    }

    public Game findById(Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new ApiNotFoundException("Game not found."));
    }


    public void delete(Long id) {
        Game dbGame = findById(id);
        if (dbGame == null) {
            throw new ApiNotFoundException("Game not found.");
        }
        reviewRepository.deleteAll(dbGame.getReviews());
        gameRepository.delete(dbGame);
    }
}
