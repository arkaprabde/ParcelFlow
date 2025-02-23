package App.Controllers;

import java.security.Principal;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import App.Entities.Vendor;
import App.Entities.Product;
import App.Services.VendorService;

@RestController
@RequestMapping("/vendor")
@PreAuthorize("hasRole('VENDOR')")
public class VendorController
{
    @Autowired
    private VendorService service;

    @GetMapping("/profile")
    public ResponseEntity<Vendor> getVendor(Principal userDetails)
    {
        Vendor vendor = service.getVendor(userDetails.getName());
        return (vendor != null)? ResponseEntity.ok(vendor): ResponseEntity.notFound().build();
    }

    @GetMapping("/products")
    public ResponseEntity<Set<Product>> getProducts(Principal userDetails)
    {
        Set<Product> products = service.getProducts(userDetails.getName());
        return (!products.isEmpty())? ResponseEntity.ok(products): ResponseEntity.noContent().build();
    }
    
    @PutMapping("/profile")
    public ResponseEntity<Vendor> updateVendor(Principal userDetails, @RequestBody Vendor updatedVendor) 
    {
        Vendor vendor = service.updateVendor(userDetails.getName(), updatedVendor);
        return (vendor != null)? ResponseEntity.ok(vendor): ResponseEntity.notFound().build();
    }
    
    @PostMapping("/products/add")
    public ResponseEntity<Product> addProduct(Principal userDetails, @RequestBody Product product)
    {
        Product addedProduct = service.addProduct(userDetails.getName(), product);
        return (addedProduct != null)? ResponseEntity.ok(addedProduct): ResponseEntity.badRequest().build();
    }
    
    @DeleteMapping("/products/remove/{productId}")
    public ResponseEntity<String> removeProduct(Principal userDetails, @PathVariable Long productId)
    {
        return service.removeProduct(userDetails.getName(), productId)?
               ResponseEntity.ok("Product removed successfully"):
               ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found or could not be deleted");
    }
    
    @DeleteMapping("/profile/delete")
    public ResponseEntity<String> deleteVendor(Principal userDetails)
    {
        return service.deleteVendor(userDetails.getName())?
               ResponseEntity.ok("Vendor account deleted successfully"):
               ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vendor not found or could not be deleted");
    }
}
