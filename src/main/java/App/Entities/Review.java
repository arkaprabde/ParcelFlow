package App.Entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Review
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "buyer_email", nullable = false)
	private Buyer customer;

	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
	@JsonBackReference("product-reviews")
	private Product product;

	private LocalDate review_date;
	private String description;
	private float rating;

	public Review() {}

	public Review(Buyer customer, Product product, String description, float rating)
	{
		this.customer = customer;
		this.product = product;
		this.review_date = LocalDate.now();
		this.description = description;
		this.rating = rating;
	}

	public Long getId()
	{
		return id;
	}

	public Buyer getCustomer()
	{
		return customer;
	}

	public void setCustomer(Buyer customer)
	{
		this.customer = customer;
	}

	public Product getProduct()
	{
		return product;
	}

	public void setProduct(Product product)
	{
		this.product = product;
	}

	public LocalDate getReview_date()
	{
		return review_date;
	}

	public void setReview_date(LocalDate review_date)
	{
		this.review_date = review_date;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public float getRating()
	{
		return rating;
	}

	public void setRating(float rating)
	{
		this.rating = rating;
	}
}
