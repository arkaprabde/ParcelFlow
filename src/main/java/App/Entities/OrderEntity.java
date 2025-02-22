package App.Entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
public class OrderEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long o_id;

	@ManyToOne
	@JoinColumn(name = "buyer_email", nullable = false)
	private Buyer customer;

	@ManyToMany
	@JoinTable(
		name = "order_products",
		joinColumns = @JoinColumn(name = "o_id"),
		inverseJoinColumns = @JoinColumn(name = "p_id")
	)
	private Set<Product> products;

	private String address;
	private LocalDate order_date;
	private LocalDate delivery_date;
	private String status;
	private float price;

	@ManyToOne
	@JoinColumn(name = "delivery_boy_email")
	private DeliveryBoy personnel;

	private boolean isAcceptedByDeliveryBoy = false;
	private boolean isDelivered = false;

	public OrderEntity() {}

	public OrderEntity(Buyer customer, Set<Product> products, String address, LocalDate order_date,
					   LocalDate delivery_date, String status, float price, DeliveryBoy personnel)
	{
		this.customer = customer;
		this.products = products;
		this.address = address;
		this.order_date = order_date;
		this.delivery_date = delivery_date;
		this.status = status;
		this.price = price;
		this.personnel = personnel;
	}

	public Long getO_id()
	{
		return o_id;
	}

	public Buyer getCustomer() 
	{
		return customer;
	}

	public void setCustomer(Buyer customer) 
	{
		this.customer = customer;
	}

	public Set<Product> getProducts()
	{
		return products;
	}

	public void setProducts(Set<Product> products)
	{
		this.products = products;
	}

	public String getAddress() 
	{
		return address;
	}

	public void setAddress(String address) 
	{
		this.address = address;
	}

	public LocalDate getOrder_date()
	{
		return order_date;
	}

	public void setOrder_date(LocalDate order_date) 
	{
		this.order_date = order_date;
	}

	public LocalDate getDelivery_date()
	{
		return delivery_date;
	}

	public void setDelivery_date(LocalDate delivery_date) 
	{
		this.delivery_date = delivery_date;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status) 
	{
		this.status = status;
	}

	public float getPrice()
	{
		return price;
	}

	public void setPrice(float price) 
	{
		this.price = price;
	}

	public DeliveryBoy getPersonnel() 
	{
		return personnel;
	}

	public void setPersonnel(DeliveryBoy personnel) 
	{
		this.personnel = personnel;
	}

	public boolean isAcceptedByDeliveryBoy()
	{
		return isAcceptedByDeliveryBoy;
	}

	public void setAcceptedByDeliveryBoy(boolean isAcceptedByDeliveryBoy)
	{
		this.isAcceptedByDeliveryBoy = isAcceptedByDeliveryBoy;
	}

	public boolean isDelivered()
	{
		return isDelivered;
	}

	public void setDelivered(boolean isDelivered)
	{
		this.isDelivered = isDelivered;
	}

	public void setBuyer(Buyer b)
	{
		this.customer = b;
	}

	public Buyer getBuyer()
	{
		return customer;
	}
}
