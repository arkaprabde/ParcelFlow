package App.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Vendor  implements UserDetails, Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(nullable = false, unique = true, columnDefinition = "VARCHAR(255)")
	private String email;
	private String password, name, address;
	
	@Lob
	private String token, refresh_token;
	
	private long phone;
	private float rating;
	private int experience;

	@OneToOne
    @JoinColumn(name = "email", referencedColumnName = "email")
    private Role role;
	
	@OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonManagedReference("vendor-product")
    private Set<Product> products;
	
	public Vendor()
	{
		super();
	}

	public Vendor(String email, String password, String name, String address, String token,
			String refresh_token, long phone, float rating, int experience)
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
		this.experience = experience;
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
	
	public int getExperience()
	{
		return experience;
	}
	
	public void setExperience(int experience)
	{
		this.experience = experience;
	}
	
	public Role getRole()
	{
		return role;
	}
	
	public Set<Product> getProducts()
	{
		return products;
	}
	
	public void setProducts(Set<Product> p)
	{
		this.products = p;
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