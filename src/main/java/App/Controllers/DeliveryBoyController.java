package App.Controllers;

import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import App.Entities.DeliveryBoy;
import App.Entities.OrderEntity;
import App.Services.DeliveryBoyService;

@RestController
@RequestMapping("/deliveryboy")
@PreAuthorize("hasRole('DELIVERY_BOY')")
public class DeliveryBoyController
{
	@Autowired
    private DeliveryBoyService service;

    @GetMapping("/profile")
    public ResponseEntity<DeliveryBoy> getDeliveryBoy(Principal userDetails)
    {
        DeliveryBoy deliveryBoy = service.getDeliveryBoy(userDetails.getName());
        return (deliveryBoy != null)? ResponseEntity.ok(deliveryBoy): ResponseEntity.notFound().build();
    }

    @GetMapping("/orders/assigned")
    public ResponseEntity<List<OrderEntity>> getAssignedOrders(Principal userDetails)
    {
        List<OrderEntity> orders = service.getAssignedOrders(userDetails.getName());
        return (!orders.isEmpty())? ResponseEntity.ok(orders): ResponseEntity.noContent().build();
    }

    @PostMapping("/orders/mark-delivered/{orderId}")
    public ResponseEntity<String> markOrderAsDelivered(Principal userDetails, @PathVariable Long orderId)
    {
        return service.markOrderAsDelivered(orderId, userDetails.getName())?
               ResponseEntity.ok("Order marked as delivered"):
               ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found or unauthorized action");
    }

    @PutMapping("/availability/{isAvailable}")
    public ResponseEntity<String> updateAvailability(Principal userDetails, @PathVariable boolean isAvailable)
    {
        return service.updateAvailability(userDetails.getName(), isAvailable)?
               ResponseEntity.ok("Availability updated"):
               ResponseEntity.status(HttpStatus.NOT_FOUND).body("Delivery Boy not found");
    }

    @GetMapping("/orders/delivery-history")
    public ResponseEntity<List<OrderEntity>> getDeliveryHistory(Principal userDetails)
    {
        List<OrderEntity> orders = service.getDeliveryHistory(userDetails.getName());
        return (!orders.isEmpty())? ResponseEntity.ok(orders): ResponseEntity.noContent().build();
    }
}