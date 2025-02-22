package App.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import App.Entities.*;
import App.Repos.DeliveryBoyRepo;

@Service
public class DeliveryBoyService implements UserDetailsService
{
    @Autowired
    private DeliveryBoyRepo repo;
    
    @Autowired
    private OrderService o_service;

    @Transactional(readOnly = true)
    public DeliveryBoy getDeliveryBoy(String email)
    {
        return repo.findById(email).orElse(null);
    }

    public DeliveryBoy putDeliveryBoy(DeliveryBoy deliveryBoy)
    {
        return repo.save(deliveryBoy);
    }

    @Transactional
    public boolean assignOrderToDeliveryBoy(Long orderId, String deliveryBoyEmail)
    {
        OrderEntity order = o_service.getOrder(orderId);
        DeliveryBoy deliveryBoy = getDeliveryBoy(deliveryBoyEmail);

        if (order != null && deliveryBoy != null && deliveryBoy.isAvailable())
        {
            order.setPersonnel(deliveryBoy);
            order.setStatus("Out for Delivery");
            o_service.updateOrder(order);
            deliveryBoy.setAvailable(false);
            putDeliveryBoy(deliveryBoy);
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public List<OrderEntity> getAssignedOrders(String email)
    {
    	DeliveryBoy d = getDeliveryBoy(email);
		if(d == null)
			return null;
        return o_service.getOrdersByDeliveryBoy(d);
    }

    @Transactional
    public boolean markOrderAsDelivered(Long orderId, String deliveryBoyEmail)
    {
        OrderEntity order = o_service.getOrder(orderId);
        DeliveryBoy deliveryBoy = getDeliveryBoy(deliveryBoyEmail);

        if (order != null && deliveryBoy != null && order.getPersonnel().equals(deliveryBoy))
        {
            order.setStatus("Delivered");
            o_service.updateOrder(order);
            deliveryBoy.setAvailable(true);
            putDeliveryBoy(deliveryBoy);
            return true;
        }
        return false;
    }

    public boolean updateAvailability(String deliveryBoyEmail, boolean isAvailable)
    {
        DeliveryBoy deliveryBoy = getDeliveryBoy(deliveryBoyEmail);
        if (deliveryBoy != null)
        {
            deliveryBoy.setAvailable(isAvailable);
            putDeliveryBoy(deliveryBoy);
            return true;
        }
        return false;
    }

	public Set<DeliveryBoy> getAllDeliveryBoys()
	{
		Set<DeliveryBoy> d = new HashSet<>();
		repo.findAll().forEach(d :: add);
		return d;
	}

	public boolean deleteDeliveryBoy(String email)
	{
		DeliveryBoy d = getDeliveryBoy(email);
		if(d != null)
		{
			repo.delete(d);
			return true;
		}
		return false;
	}

	public List<OrderEntity> getDeliveryHistory(String email)
	{
		DeliveryBoy d = getDeliveryBoy(email);
		if(d == null)
			return null;
		return o_service.getDeliveryHistory(d);
	}
	
	@Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
    {
        DeliveryBoy deliveryBoy = repo.findById(email)
                .orElseThrow(() -> new UsernameNotFoundException("DeliveryBoy not found with email: " + email));
        return deliveryBoy;
    }
	
	public DeliveryBoy authenticate(String email, String password)
	{
		DeliveryBoy user = getDeliveryBoy(email);
		if(user == null || !user.getPassword().equals(password))
			return null;
		return user;
	}
}
