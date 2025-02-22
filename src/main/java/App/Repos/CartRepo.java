package App.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import App.Entities.Cart;

@Repository
public interface CartRepo extends JpaRepository<Cart, Long>
{
	Cart findByBuyerEmail(String email);
}