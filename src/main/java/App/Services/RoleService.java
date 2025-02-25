package App.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import App.Entities.*;
import App.Repos.RoleRepo;

@Service
public class RoleService 
{
	
	@Autowired
	private RoleRepo repo;
	/*
	private final Dotenv dotenv = Dotenv.load();
    private final String adminEmail = dotenv.get("ADMIN_EMAIL");
    //private final String adminPassword = dotenv.get("ADMIN_PASSWORD");
	@PostConstruct
	public void initAdminRole()
	{
		if (repo.findById(adminEmail).isEmpty())
		{
			Role admin = new Role(adminEmail, "ADMIN");
			repo.save(admin);
			System.out.println("Admin role initialized!");
		}
		else
			System.out.println("Admin already exists.");
	}*/
	
	public Role putRole(Buyer b)
	{
		Role r = new Role(b.getEmail(), "BUYER");
		return repo.save(r);
	}
	
	public Role putRole(Vendor v)
	{
		Role r = new Role(v.getEmail(), "VENDOR");
		return repo.save(r);
	}
	
	public Role putRole(DeliveryBoy d)
	{
		Role r = new Role(d.getEmail(), "DELIVERY_BOY");
		return repo.save(r);
	}
	
	public String getRole(String email)
	{
		Role r = repo.findById(email).orElse(null);
		if(r == null)
			return null;
		else
			return r.getRole();
	}
}
