package App.Services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import App.Entities.*;
import App.Repos.OrderRepo;

@Service
public class OrderService
{
	@Autowired
	private OrderRepo repo;
	
	public OrderEntity getOrder(Long id)
	{
		return repo.findById(id).orElse(null);
	}
	public OrderEntity addOrder(OrderEntity o)
	{
		return repo.save(o);
	}
	
	public void updateOrder(OrderEntity o)
	{
		repo.save(o);
	}
	public boolean removeOrder(Buyer b, Long id)
	{
		OrderEntity o = getOrder(id);
		if(o != null && b.getOrders().contains(o))
		{	repo.delete(o);
        	return true;
		}
		return false;
	}

	public Set<OrderEntity> getAllOrders()
	{
		Set<OrderEntity> o = new HashSet<>();
		repo.findAll().forEach(o :: add);
		return o;
	}
	public boolean deleteOrder(Long id)
	{
		OrderEntity o = getOrder(id);
		if(o != null)
		{
			repo.deleteById(id);
			return true;
		}
		return false;
	}
	public List<OrderEntity> getOrdersByDeliveryBoy(DeliveryBoy d)
	{
		return repo.findByPersonnel(d);
	}
	
	public List<OrderEntity> getDeliveryHistory(DeliveryBoy d)
	{
		return repo.getDeliveredOrdersByPersonnel(d);
	}
}