package App.Services;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import App.Entities.*;
import App.Repos.ProductRepo;

@Service
public class ProductService
{
	@Autowired
	private ProductRepo repo;
	
	public Product addProduct(Product p)
	{
		return repo.save(p);
	}
	
	@Transactional(readOnly = true)
	public Product getProduct(Long id)
	{
		return repo.findById(id).orElse(null);
	}
	
	@Transactional(readOnly = true)
	public Set<Product> getProductsByTags(Set<String> t)
	{
		Set<Product> p = new HashSet<>(), s = new HashSet<>();
		repo.findAll().forEach(p :: add);
		for(Product x: p)
			for(String y: t)
				if(x.getTags().contains(y))
					s.add(x);
		return s;
	}
	
	@Transactional(readOnly = true)
	public Set<Product> getProductsByVendors(Set<Vendor> v)
	{
		Set<Product> p = new HashSet<>();
		for(Vendor x: v)
			p.addAll(repo.findByVendorEmail(x.getEmail()));
		return p;
	}
	
	@Transactional(readOnly = true)
	public Set<Product> getProductsBelowPrice(float maxPrice) {
	    Set<Product> s = new HashSet<>();
	    repo.findAll().forEach(p ->
	    {
	        if (p.getPrice() <= maxPrice)
	            s.add(p);
	    });
	    return s;
	}

	@Transactional(readOnly = true)
	public Set<Product> getProductsByTagsAndVendors(Set<String> t, Set<Vendor> v)
	{
		Set<Product> p = getProductsByVendors(v), s = new HashSet<>();
		for(Product x: p)
			for(String y: t)
					if(x.getTags().contains(y))
						s.add(x);
		return s;
	}
	
	@Transactional(readOnly = true)
	public Set<Product> getAvailableProducts() {
	    Set<Product> s = new HashSet<>();
	    repo.findAll().forEach(p ->
	    {
	        if (p.getAvailability() > 0) {
	            s.add(p);
	        }
	    });
	    return s;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Set<Product> getTopSellingProducts(int limit)
	{
	    return ((Set<Product>)repo.findAll()).stream()
	    		.sorted((p1, p2) -> Long.compare(p2.getOrdered_no(), p1.getOrdered_no()))
	            .limit(limit).collect(Collectors.toSet());
	}
	
	public void deleteProduct(Vendor v, Product p)
	{
		if(p.getVendor().equals(v))
			repo.delete(p);
	}
	
	public void deleteAllProducts(Vendor v)
	{
		Set<Product> products = v.getProducts();
        for (Product p : products)
            	deleteProduct(v, p);
	}

	public boolean deleteProductById(Long id)
	{
		Product p = getProduct(id);
		if(p != null)
		{
			repo.delete(p);
			return true;
		}
		return false;
	}
}