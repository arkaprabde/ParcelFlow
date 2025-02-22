package App.Repos;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import App.Entities.Buyer;
import App.Entities.Review;

@Repository
public interface ReviewRepo extends JpaRepository<Review, Long>
{
	List<Review> findByProductId(Long productId);
	List<Review> findByCustomer(Buyer customer);
}