package App.Controllers;

import App.Entities.*;
import App.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/admin")
//@PreAuthorize("hasRole('ADMIN')")
public class AdminController
{
    @Autowired
    private AdminService adminService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/buyers")
    public ResponseEntity<Set<Buyer>> getAllBuyers()
    {
        return ResponseEntity.ok(adminService.getAllBuyers());
    }

    @GetMapping("/vendors")
    public ResponseEntity<Set<Vendor>> getAllVendors()
    {
    	System.out.println("here");
        return ResponseEntity.ok(adminService.getAllVendors());
    }

    @GetMapping("/delivery-boys")
    public ResponseEntity<Set<DeliveryBoy>> getAllDeliveryBoys()
    {
        return ResponseEntity.ok(adminService.getAllDeliveryBoys());
    }

    @DeleteMapping("/buyer/{email}")
    public ResponseEntity<?> deleteBuyer(@PathVariable String email)
    {
        return adminService.deleteBuyer(email);
    }

    @DeleteMapping("/vendor/{email}")
    public ResponseEntity<?> deleteVendor(@PathVariable String email)
    {
        return adminService.deleteVendor(email);
    }

    @DeleteMapping("/delivery-boy/{email}")
    public ResponseEntity<?> deleteDeliveryBoy(@PathVariable String email)
    {
        return adminService.deleteDeliveryBoy(email);
    }

    @GetMapping("/vendor/{email}/products")
    public ResponseEntity<Set<Product>> getVendorProducts(@PathVariable String email)
    {
        return ResponseEntity.ok(adminService.getVendorProducts(email));
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId)
    {
        return adminService.deleteProduct(productId);
    }

    @GetMapping("/orders")
    public ResponseEntity<Set<OrderEntity>> getAllOrders()
    {
        return ResponseEntity.ok(adminService.getAllOrders());
    }

    @DeleteMapping("/order/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long orderId)
    {
        return adminService.deleteOrder(orderId);
    }
}
