package App.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.userdetails.UserDetails;
import App.Entities.*;
import App.Services.*;
import App.Security.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController
{
	@Autowired
	private DeliveryBoyService deliveryBoyService;
	
	@Autowired
	private BuyerService buyerService;
	
	@Autowired
	private VendorService vendorService;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@PostMapping("/register/buyer")
    public ResponseEntity<?> registerBuyer(@RequestBody Buyer buyer)
    {
        if (buyerService.getBuyer(buyer.getEmail()) != null)
            return ResponseEntity.badRequest().body("Email already registered.");
        buyerService.putBuyer(buyer);
        return ResponseEntity.ok("Buyer registered successfully.");
    }
	
	@PostMapping("/register/vendor")
    public ResponseEntity<?> registerVendor(@RequestBody Vendor vendor)
    {
        if (vendorService.getVendor(vendor.getEmail()) != null)
            return ResponseEntity.badRequest().body("Email already registered.");
        vendorService.putVendor(vendor);
        return ResponseEntity.ok("Vendor registered successfully.");
    }
	
	@PostMapping("/register/deliveryboy")
    public ResponseEntity<?> registerDeliveryBoy(@RequestBody DeliveryBoy boy)
    {
        if (deliveryBoyService.getDeliveryBoy(boy.getEmail()) != null)
            return ResponseEntity.badRequest().body("Email already registered.");
        deliveryBoyService.putDeliveryBoy(boy);
        return ResponseEntity.ok("Delivery Boy registered successfully.");
    }
	
	@PostMapping("/login/admin")
	public ResponseEntity<?> loginAdmin(@RequestBody LoginRequest req)
	{
		if(!adminService.validateAdmin(req.getEmail(), req.getPassword()))
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid Credentials");
		return ResponseEntity.ok("token: " + jwtUtil.generateToken(req.getEmail()));
			
	}
	
	@PostMapping("/login/buyer")
	public ResponseEntity<?> loginBuyer(@RequestBody LoginRequest req)
	{
		return authenticate(buyerService.authenticate(req.getEmail(), req.getPassword()));
	}
	
	@PostMapping("/login/vendor")
	public ResponseEntity<?> loginVendor(@RequestBody LoginRequest req)
	{
		return authenticate(vendorService.authenticate(req.getEmail(), req.getPassword()));
	}
	
	@PostMapping("/login/deliveryboy")
	public ResponseEntity<?> loginDeliveryBoy(@RequestBody LoginRequest req)
	{
		return authenticate(deliveryBoyService.authenticate(req.getEmail(), req.getPassword()));
	}
	
	public ResponseEntity<?> authenticate(UserDetails user)
	{
		if(user == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid Credentials");
		return ResponseEntity.ok("token: " + jwtUtil.generateToken(user.getUsername()));
	}
}