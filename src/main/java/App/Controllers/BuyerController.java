package App.Controllers;

import java.security.Principal;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import App.Services.BuyerService;
import App.Entities.Buyer;
import App.Entities.Cart;
import App.Entities.OrderEntity;

@RestController
@RequestMapping("/buyer")
public class BuyerController
{
	@Autowired
	BuyerService service;
	
	@GetMapping("/profile")
    @PreAuthorize("hasRole('BUYER')") 
    public ResponseEntity<Buyer> getBuyer(Principal b)
    {
		System.out.println("Authenticated");
        Buyer buyer = service.getBuyer(b.getName());
        return (buyer != null)? ResponseEntity.ok(buyer): ResponseEntity.notFound().build();
    }

    @GetMapping("/cart")
    @PreAuthorize("hasRole('BUYER')") 
    public ResponseEntity<Cart> getCart(Principal b)
    {
        Cart cart = service.getCart(b.getName());
        return (cart != null)? ResponseEntity.ok(cart): ResponseEntity.notFound().build();
    }

    @GetMapping("/orders")
    @PreAuthorize("hasRole('BUYER')") 
    public ResponseEntity<Set<OrderEntity>> getOrders(Principal b)
    {
        Set<OrderEntity> orders = service.getOrders(b.getName());
        return (orders != null)? ResponseEntity.ok(orders): ResponseEntity.notFound().build();
    }
    
    @PutMapping("/profile/update")
    @PreAuthorize("hasRole('BUYER')")
    public ResponseEntity<Buyer> updateBuyer(Principal b, @RequestBody Buyer ub) 
    {
        Buyer buyer = service.updateBuyer(b.getName(), ub);
        return (buyer != null)? ResponseEntity.ok(buyer): ResponseEntity.notFound().build();
    }
    
    @PostMapping("/cart/add/{productId}")
    @PreAuthorize("hasRole('BUYER')")
    public ResponseEntity<Cart> addProductToCart(Principal b, @PathVariable Long productId)
    {
        Cart cart = service.addProductToCart(b.getName(), productId);
        return (cart != null)? ResponseEntity.ok(cart): ResponseEntity.badRequest().build();
    }
    
    @DeleteMapping("/cart/remove/{productId}")
    @PreAuthorize("hasRole('BUYER')")
    public ResponseEntity<Cart> removeProductFromCart(@AuthenticationPrincipal Principal b, @PathVariable Long productId)
    {
        Cart cart = service.removeProductFromCart(b.getName(), productId);
        return (cart != null)? ResponseEntity.ok(cart): ResponseEntity.badRequest().build();
    }
    
    @PostMapping("/cart/checkout")
    @PreAuthorize("hasRole('BUYER')")
    public ResponseEntity<OrderEntity> checkout(Principal b)
    {
        OrderEntity order = service.checkout(b.getName());
        return (order != null)? ResponseEntity.ok(order): ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    
    @DeleteMapping("/orders/cancel/{orderId}")
    @PreAuthorize("hasRole('BUYER')")
    public ResponseEntity<String> cancelOrder(Principal b, @PathVariable Long orderId)
    {
        String result = service.cancelOrder(b.getName(), orderId);
        return result.contains("successfully")? ResponseEntity.ok(result): ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }
    
    @PostMapping("/orders/rate/{productId}")
    @PreAuthorize("hasRole('BUYER')")
    public ResponseEntity<String> rateProduct(Principal b, @PathVariable Long productId, @RequestParam int rating, @RequestParam String review)
    {
        try 
        {
            return ResponseEntity.ok(service.rateProduct(b.getName(), productId, rating, review));
        } 
        catch (RuntimeException e) 
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
    @PutMapping("/profile/upgrade")
    @PreAuthorize("hasRole('BUYER')")
    public ResponseEntity<Buyer> upgradeToPremium(Principal b)
    {
        Buyer buyer = service.upgradeToPremium(b.getName());
        return (buyer != null)? ResponseEntity.ok(buyer): ResponseEntity.notFound().build();
    }
}
