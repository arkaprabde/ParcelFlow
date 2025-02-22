package App.Repos;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import App.Entities.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long>
{
	Set<Product> findByVendorEmail(String email);
}