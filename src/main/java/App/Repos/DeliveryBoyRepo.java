package App.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import App.Entities.DeliveryBoy;

@Repository
public interface DeliveryBoyRepo extends JpaRepository<DeliveryBoy, String>
{}