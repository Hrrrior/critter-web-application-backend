package ee.taltech.critter.service;

import ee.taltech.critter.exception.ApiNotFoundException;
import ee.taltech.critter.model.Game;
import ee.taltech.critter.model.Review;
import ee.taltech.critter.repository.GameRepository;
import ee.taltech.critter.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private GameRepository gameRepository;

    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    public Review findById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new ApiNotFoundException("Review not found."));
    }

    public Review save(Review review, Long gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new ApiNotFoundException("Game not found."));
        review.setGame(game);
        Review reviewWithID = reviewRepository.save(review);
        game.getReviews().add(reviewWithID);
        game.updateRating();
        gameRepository.save(game);
        return reviewWithID;
    }
    public void delete(Long id) {
        reviewRepository.deleteById(id);
    }
}
