package App.Services;

import App.Entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import io.github.cdimascio.dotenv.Dotenv;
import java.util.HashSet;
import java.util.Set;
import App.Repos.ProductRepo;

@Service
public class AdminService
{
	private final Dotenv dotenv = Dotenv.load();
    private final String adminEmail = dotenv.get("ADMIN_EMAIL");
    private final String adminPassword = dotenv.get("ADMIN_PASSWORD");
    
    @Autowired
    private BuyerService buyerService;

    @Autowired
    private VendorService vendorService;

    @Autowired
    private DeliveryBoyService deliveryBoyService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;
    
    @Autowired
    private ProductRepo p_repo;

    public Set<Buyer> getAllBuyers()
    {
        return buyerService.getAllBuyers();
    }

    public Set<Vendor> getAllVendors()
    {
        return vendorService.getAllVendors();
    }
    
    public Set<Product> getAllProducts()
    {
    	Set<Product> v = new HashSet<>();
		p_repo.findAll().forEach(v :: add);
		return v;
	}

    public Set<DeliveryBoy> getAllDeliveryBoys()
    {
        return deliveryBoyService.getAllDeliveryBoys();
    }

    public ResponseEntity<?> deleteBuyer(String email)
    {
        return buyerService.deleteBuyer(email)? ResponseEntity.ok("Buyer deleted successfully.")
                                              : ResponseEntity.badRequest().body("Buyer not found.");
    }

    public ResponseEntity<?> deleteVendor(String email)
    {
        return vendorService.deleteVendor(email)? ResponseEntity.ok("Vendor deleted successfully.")
                                                : ResponseEntity.badRequest().body("Vendor not found.");
    }

    public ResponseEntity<?> deleteDeliveryBoy(String email)
    {
        return deliveryBoyService.deleteDeliveryBoy(email)? ResponseEntity.ok("Delivery boy deleted successfully.")
                                                          : ResponseEntity.badRequest().body("Delivery boy not found.");
    }

    public Set<Product> getVendorProducts(String email)
    {
        return vendorService.getProducts(email);
    }

    public ResponseEntity<?> deleteProduct(Long productId)
    {
        return productService.deleteProductById(productId)? ResponseEntity.ok("Product deleted successfully.")
                                                          : ResponseEntity.badRequest().body("Product not found.");
    }

    public Set<OrderEntity> getAllOrders()
    {
        return orderService.getAllOrders();
    }

    public ResponseEntity<?> deleteOrder(Long orderId)
    {
        return orderService.deleteOrder(orderId)? ResponseEntity.ok("Order deleted successfully.")
                                                : ResponseEntity.badRequest().body("Order not found.");
    }

    public boolean validateAdmin(String email, String password)
    {
        return email.equals(adminEmail) && password.equals(adminPassword);
    }

    public boolean isAdmin(String email)
    {
        return email.equals(adminEmail);
    }
}
