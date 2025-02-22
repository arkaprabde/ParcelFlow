package App.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class DeliveryBoy  implements UserDetails, Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	private String email;
	private String password, name, phone;
	private Integer orders_delivered;
	private boolean available;
	
	@OneToOne
    @JoinColumn(name = "email", referencedColumnName = "email")
    private Role role;
	
	@Lob
	private String token, refresh_token;
	
	@OneToMany(mappedBy = "personnel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<OrderEntity> assignedOrders;

	public DeliveryBoy()
	{}

	public DeliveryBoy(String email, String password, String name, String phone)
	{
		this.email = email;
		this.password = password;
		this.name = name;
		this.phone = phone;
		this.available = true;
		this.orders_delivered = 0;
		this.role.role = "DELIVERY_BOY";
	}

	public String getEmail()
	{
		return email;
	}

	@Override
    public String getUsername()
    {
        return getEmail();
    }
	
	public void setEmail(String email)
	{
		this.email = email;
	}

	@Override
	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public Integer getOrders_delivered()
	{
		return orders_delivered;
	}

	public void setOrders_delivered(Integer orders_delivered)
	{
		this.orders_delivered = orders_delivered;
	}

	public boolean isAvailable()
	{
		return available;
	}

	public void setAvailable(boolean available)
	{
		this.available = available;
	}

	public Role getRole()
	{
		return role;
	}

	public String getToken()
	{
		return token;
	}

	public void setToken(String token)
	{
		this.token = token;
	}

	public String getRefresh_token()
	{
		return refresh_token;
	}

	public void setRefresh_token(String refresh_token)
	{
		this.refresh_token = refresh_token;
	}

	public List<OrderEntity> getAssignedOrders()
	{
		return assignedOrders;
	}

	public void setAssignedOrders(List<OrderEntity> assignedOrders)
	{
		this.assignedOrders = assignedOrders;
	}
	
	@Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return List.of(new SimpleGrantedAuthority(getRole().role));
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return true;
    }
}