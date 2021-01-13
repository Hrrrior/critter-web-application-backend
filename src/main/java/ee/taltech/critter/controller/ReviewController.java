package ee.taltech.critter.controller;

import ee.taltech.critter.model.Review;
import ee.taltech.critter.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("reviews")
@RestController
@ResponseStatus(value= HttpStatus.OK)
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    /*
    GET. Get all reviews.
     */
    @GetMapping
    public List<Review> getAllReviews() {
        return reviewService.findAll();
    }

    /*
    GET. Get review with ID.
     */
    @GetMapping("{id}")
    public Review getReviewByID(@PathVariable Long id) {
        return reviewService.findById(id);
    }

    /*
    POST. Save new review to db.
     */
    @PostMapping("{gameId}")
    public Review saveReview(@RequestBody Review review, @PathVariable Long gameId) {
        return reviewService.save(review, gameId);
    }

    /*
    DELETE. Delete review with ID.
     */
    @DeleteMapping("{id}")
    public void deleteReview(@PathVariable Long id) {
        reviewService.delete(id);
    }
}
