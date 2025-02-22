package App.Repos;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import App.Entities.DeliveryBoy;
import App.Entities.OrderEntity;

@Repository
public interface OrderRepo extends JpaRepository<OrderEntity, Long>
{
	List<OrderEntity> findByPersonnel(DeliveryBoy d);
	List<OrderEntity> getDeliveredOrdersByPersonnel(DeliveryBoy d);
}