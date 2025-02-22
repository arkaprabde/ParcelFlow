package App.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import App.Entities.*;
import App.Repos.CartRepo;

@Service
public class CartService
{
	@Autowired
	private CartRepo repo;
	
	public Cart addCart(Cart c)
	{
		return repo.save(c);
	}

	@Transactional(readOnly = true)
	public Cart getCartById(Long id)
	{
		return repo.findById(id).orElse(null);
	}

	@Transactional(readOnly = true)
	public Cart getCartByBuyerEmail(String email)
	{
		return repo.findByBuyerEmail(email);
	}

	@Transactional
	public Cart addProduct(Cart c, Product p)
	{
		if (c == null) return null;
		c.getProducts().add(p);
        return addCart(c);
	}

	@Transactional
	public Cart removeProduct(Cart c, Product p)
	{
		if (c == null) return null;
		c.getProducts().remove(p);
        return addCart(c);
	}

	@Transactional
	public void removeAll(Cart c)
	{
		if (c != null)
		{
			c.getProducts().clear();
			repo.save(c);
		}
	}
	
	public boolean containsProduct(Cart c, Product p)
	{
	    return c != null && c.getProducts().contains(p);
	}
	
	public double getCartTotal(Cart c)
	{
	    if (c == null) return 0.0;
	    return c.getProducts().stream().mapToDouble(Product::getPrice).sum();
	}
	
	public void clearCartByBuyerEmail(String email)
	{
	    Cart c = getCartByBuyerEmail(email);
	    if (c != null)
	    	removeAll(c);
	}
}
