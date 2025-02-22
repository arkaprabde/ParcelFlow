package App.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import App.Entities.Review;
import App.Repos.ReviewRepo;

@Service
public class ReviewService
{
    @Autowired
    private ReviewRepo repo;
    
    @Transactional
    public void addReview(Review r)
    {
        repo.save(r);
    }

    @Transactional(readOnly = true)
    public List<Review> getReviewsByProduct(Long productId)
    {
        return repo.findByProductId(productId);
    }

    /*@Transactional(readOnly = true)
    public List<Review> getReviewsByBuyer(String email)
    {
    	Buyer b = b_service.getBuyer(email);
    	if(b == null)
    		return null;
        return repo.findByCustomer(b);
    }*/

    @Transactional
    public boolean deleteReview(Long reviewId)
    {
        if (repo.existsById(reviewId))
        {
            repo.deleteById(reviewId);
            return true;
        }
        return false;
    }

    @Transactional
    public Review updateReview(Long reviewId, float rating, String description)
    {
        return repo.findById(reviewId).map(review ->
        {
            review.setRating(rating);
            review.setDescription(description);
            return repo.save(review);
        }).orElse(null);
    }
}
