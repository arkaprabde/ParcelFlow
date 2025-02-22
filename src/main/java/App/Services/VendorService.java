package App.Services;

import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import App.Entities.Vendor;
import App.Entities.Product;
import App.Repos.VendorRepo;

@Service
public class VendorService implements UserDetailsService
{
	@Autowired
	private VendorRepo repo;
	
	@Autowired
	private ProductService p_service;
	
	@Transactional(readOnly = true)
	public Vendor getVendor(String email)
	{
		return repo.findById(email).orElse(null);
	}
	
	public Vendor putVendor(Vendor v)
	{
		return repo.save(v);
	}
	
	@Transactional(readOnly = true)
	public Set<Product> getProducts(String email)
	{
		Vendor v = getVendor(email);
		return (v != null) ? v.getProducts() : new HashSet<>();
	}

    public Vendor updateVendor(String email, Vendor updatedVendor)
    {
        Vendor vendor = getVendor(email);
        if (vendor != null)
        {
            vendor.setName(updatedVendor.getName());
            vendor.setAddress(updatedVendor.getAddress());
            return putVendor(vendor);
        }
        return null;
    }

    public Product addProduct(String email, Product product)
    {
        Vendor vendor = getVendor(email);
        if (vendor != null)
        {
            product.setVendor(vendor);
            return p_service.addProduct(product);
        }
        return null;
    }

    public boolean removeProduct(String email, Long productId)
    {
        Vendor vendor = getVendor(email);
        if (vendor != null)
        {
        	Product product = p_service.getProduct(productId);
        	if(product != null)
        	{
        		p_service.deleteProduct(vendor, product);
        		return true;
        	}
        }
        return false;
    }

    public boolean deleteVendor(String email)
    {
        Vendor vendor = getVendor(email);
        if (vendor != null)
        {
            p_service.deleteAllProducts(vendor);
            repo.delete(vendor);
            return true;
        }
        return false;
    }

	public Set<Vendor> getAllVendors()
	{
		System.out.println("here");
		Set<Vendor> v = new HashSet<>();
		repo.findAll().forEach(v :: add);
		return v;
	}
	
	@Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
    {
        Vendor vendor = repo.findById(email)
                .orElseThrow(() -> new UsernameNotFoundException("Vendor not found with email: " + email));
        return vendor;
    }
	
	public Vendor authenticate(String email, String password)
	{
		Vendor user = getVendor(email);
		if(user == null || !user.getPassword().equals(password))
			return null;
		return user;
	}
}
