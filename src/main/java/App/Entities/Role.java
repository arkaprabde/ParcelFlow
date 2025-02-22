package App.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Role
{
	@Id
	@OneToOne
    @JoinColumn(name = "email", referencedColumnName = "email")
	private String email;
	
	String role;

	Role()
	{
		super();
	}
	
	Role(String email, String role)
	{
		this.email = email;
		this.role = role;
	}
	
	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getRole()
	{
		return role;
	}

	public void setRole(String role)
	{
		this.role = role;
	}
}
