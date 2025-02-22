package App.Entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.*;

@Entity
public class Buyer implements UserDetails, Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	private String email;
	private String password, name, address;
	private long phone;
	private float rating;
	private boolean premium;
	
	@Lob
	private String token, refresh_token;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cart_id", referencedColumnName = "id")
	private Cart cart;
	
	@OneToOne
    @JoinColumn(name = "email", referencedColumnName = "email")
    private Role role;

	
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<OrderEntity> orders;
	
	public Buyer()
	{
		super();
	}

	public Buyer(String email, String password, String name, String address, String token,
			String refresh_token, long phone, float rating, boolean premium, Cart cart)
	{
		super();
		this.email = email;
		this.password = password;
		this.name = name;
		this.address = address;
		this.token = token;
		this.refresh_token = refresh_token;
		this.phone = phone;
		this.rating = rating;
		this.premium = premium;
		this.cart = cart;
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
	
	public String getAddress()
	{
		return address;
	}
	
	public void setAddress(String address)
	{
		this.address = address;
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
	
	public long getPhone()
	{
		return phone;
	}
	
	public void setPhone(long phone)
	{
		this.phone = phone;
	}
	
	public float getRating()
	{
		return rating;
	}
	
	public void setRating(float rating)
	{
		this.rating = rating;
	}
	
	public boolean isPremium()
	{
		return premium;
	}
	
	public void setPremium(boolean premium)
	{
		this.premium = premium;
	}
	
	public Cart getCart()
	{
		return cart;
	}
	
	public void setCart(Cart cart)
	{
		this.cart = cart;
	}
	
	public Role getRole()
	{
		return role;
	}
	
	public Set<OrderEntity> getOrders()
	{
		return orders;
	}
	
	@Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return List.of(new SimpleGrantedAuthority(getRole().getRole()));
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