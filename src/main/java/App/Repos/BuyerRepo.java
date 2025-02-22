package App.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import App.Entities.Buyer;

@Repository
public interface BuyerRepo extends JpaRepository<Buyer, String>
{}