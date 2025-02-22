package App.Services;

import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import App.Repos.BuyerRepo;
import org.springframework.transaction.annotation.Transactional;
import App.Entities.*;

@Service
public class BuyerService implements UserDetailsService
{
	@Autowired
	private BuyerRepo repo;
    
    @Autowired
    private ProductService p_service;
    
    @Autowired
    private CartService c_service;
    
    @Autowired
    private OrderService o_service;
    
    @Autowired
    private ReviewService r_service;
    
    @Autowired
    private RoleService ro_service;
    
    @Transactional(readOnly = true)
	public Buyer getBuyer(String email)
	{
		return repo.findById(email).orElse(null);
	}
	
	public Buyer putBuyer(Buyer b)
	{
		ro_service.putRole(b);
		return repo.save(b);
	}
	
	@Transactional(readOnly = true)
	public Cart getCart(String email)
	{
		Buyer b = getBuyer(email);
		return (b != null)? b.getCart(): null;
	}
	
	@Transactional(readOnly = true)
	public Role getRole(String email)
	{
		Buyer b = getBuyer(email);
		if(getBuyer(email) != null)
			return b.getRole();
		return null;
	}
	
	@Transactional(readOnly = true)
	public Set<OrderEntity> getOrders(String email)
	{
		Buyer b = getBuyer(email);
		return (b != null)? b.getOrders(): null;
	}
	
	@Transactional
	public Buyer updateBuyer(String email, Buyer updatedBuyer)
	{
        Buyer buyer = getBuyer(email);
        if (buyer != null)
        {
            buyer.setName(updatedBuyer.getName());
            buyer.setAddress(updatedBuyer.getAddress());
            return putBuyer(buyer);
        }
        return null;
    }
	
	@Transactional
	public Cart addProductToCart(String email, Long productId)
	{
		Buyer buyer = getBuyer(email);
        if (buyer != null)
        {
        	Product product = p_service.getProduct(productId);
        	if(product != null)
        	{
	            Cart cart = buyer.getCart();
	            return c_service.addProduct(cart, product);
        	}
        }
        return null;
    }

	@Transactional
    public Cart removeProductFromCart(String email, Long productId)
    {
    	Buyer buyer = getBuyer(email);
        if (buyer != null)
        {
        	Product product = p_service.getProduct(productId);
        	if(product != null)
        	{
	            Cart cart = buyer.getCart();
	            return c_service.removeProduct(cart, product);
        	}
        }
        return null;
    }

	@Transactional
    public OrderEntity checkout(String email)
    {
    	Buyer buyer = getBuyer(email);
        if (buyer != null)
        {
            Cart cart = buyer.getCart();
            OrderEntity order = new OrderEntity();
            order.setBuyer(buyer);
            order.setProducts(cart.getProducts());
            c_service.removeAll(cart);
            return o_service.addOrder(order);
        }
        return null;
    }

	@Transactional
	public String cancelOrder(String email, Long orderId)
	{
	    Buyer b = getBuyer(email);
	    if (b == null) return "Buyer not found";
	    return o_service.removeOrder(b, orderId) ? "Order cancelled successfully" : "Order not found or could not authorize";
	}

	@Transactional
    public String rateProduct(String email, Long productId, float rating, String description)
    {
    	Buyer b = getBuyer(email);
        if (b != null)
        {
        	Product p = p_service.getProduct(productId);
        	if(p != null)
        	{
        		Review r = new Review(b, p, description, rating);
        		r_service.addReview(r);
        		return "Review submitted successfully";
        	}
        }
        throw new RuntimeException("Buyer or Product not found");
    }
    
	@Transactional
    public Buyer upgradeToPremium(String email)
    {
        Buyer b = getBuyer(email);
        if (b != null)
        {
            b.setPremium(true);
            return repo.save(b);
        }
        return null;
    }
	
	@Transactional
    public void clearCart(String email)
    {
    	c_service.clearCartByBuyerEmail(email);
    }

	public Set<Buyer> getAllBuyers()
	{
		Set<Buyer> b = new HashSet<>();
		repo.findAll().forEach(b :: add);
		return b;
	}

	public boolean deleteBuyer(String email)
	{
		Buyer b = getBuyer(email);
		if(b != null)
		{
			repo.delete(b);
			return true;
		}
		return false;
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
    {
        Buyer buyer = repo.findById(email)
                .orElseThrow(() -> new UsernameNotFoundException("Buyer not found with email: " + email));
        System.out.println("Buyer found: " + buyer.getEmail());
        return buyer;
    }

	public Buyer authenticate(String email, String password)
	{
		Buyer user = getBuyer(email);
		if(user == null || !user.getPassword().equals(password))
			return null;
		return user;
	}
}