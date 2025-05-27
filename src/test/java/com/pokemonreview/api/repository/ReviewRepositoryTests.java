package com.pokemonreview.api.repository;

import com.pokemonreview.api.models.Review;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ReviewRepositoryTests {

    private ReviewRepository reviewRepository;

    @Autowired
    public ReviewRepositoryTests(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Test
    public void reviewRepository_SaveAll_ReturnsSavedReviews(){
        Review review=Review.builder()
                .title("title")
                .content("content")
                .stars(5)
                .build();
        Review savedReview=reviewRepository.save(review);
        Assertions.assertThat(savedReview).isNotNull();
        Assertions.assertThat(savedReview.getId()).isGreaterThan(0);
    }

    @Test
    public void reviewRepository_GetAll_ReturnsMoreThanOneReview(){
        Review review=Review.builder()
                .content("content")
                .title("title")
                .stars(5)
                .build();
        Review review2=Review.builder()
                .content("content")
                .title("title")
                .stars(5)
                .build();
        reviewRepository.save(review);
        reviewRepository.save(review2);
        List<Review> reviewList=reviewRepository.findAll();

        Assertions.assertThat(reviewList).isNotNull();
        Assertions.assertThat(reviewList.size()).isEqualTo(2);
    }

    @Test
    public void ReviewRepository_FindById_ReturnsSavedReviews(){
        Review review=Review.builder()
                .title("title")
                .content("content")
                .build();
        reviewRepository.save(review);
        Review returnReview=reviewRepository.findById(review.getId()).get();
        Assertions.assertThat(returnReview).isNotNull();
    }

    @Test
    public void ReviewRepository_UpdateReview_ReturnReview(){
        Review review=Review.builder()
                .title("title")
                .content("content")
                .build();
        reviewRepository.save(review);
        Review updatedReview=reviewRepository.findById(review.getId()).get();
        updatedReview.setTitle("title");
        updatedReview.setContent("content");
         reviewRepository.save(updatedReview);
        Assertions.assertThat(updatedReview.getTitle()).isNotNull();
        Assertions.assertThat(updatedReview.getContent()).isNotNull();
    }

    @Test
    public void reviewRepository_DeleteReview_ReturnReviewIsEmpty(){
        Review review=Review.builder()
                .title("title")
                .content("content")
                .stars(5)
                .build();
        reviewRepository.save(review);
        reviewRepository.deleteById(review.getId());
        Optional<Review> reviewReturn=reviewRepository.findById(review.getId());
        Assertions.assertThat(reviewReturn).isEmpty();
    }


}
