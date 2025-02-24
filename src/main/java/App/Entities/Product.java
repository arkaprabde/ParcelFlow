package App.Entities;

import jakarta.persistence.*;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Product
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(insertable=false, updatable=false)
	private long p_id;
	private String name, description;

	@Lob
	private String images; // Large image data or URLs

	private float price;
	private long availability, ordered_no;

	@ManyToOne
	@JoinColumn(name = "vendor_email", referencedColumnName = "email")
	@JsonBackReference
	private Vendor vendor;

	@ElementCollection
	private Set<String> tags;

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Review> reviews;
	
	public Product()
	{
		super();
	}

	public Product(String name, String description, String images, float price, long availability,
			long ordered_no, Set<String> tags, Set<Review> reviews)
	{
		super();
		this.name = name;
		this.description = description;
		this.images = images;
		this.price = price;
		this.availability = availability;
		this.ordered_no = ordered_no;
		this.tags = tags;
		this.reviews = reviews;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getImages()
	{
		return images;
	}

	public void setImages(String images)
	{
		this.images = images;
	}

	public float getPrice()
	{
		return price;
	}

	public void setPrice(float price)
	{
		this.price = price;
	}

	public long getAvailability()
	{
		return availability;
	}

	public void setAvailability(long availability)
	{
		this.availability = availability;
	}

	public long getOrdered_no()
	{
		return ordered_no;
	}

	public void setOrdered_no(long ordered_no)
	{
		this.ordered_no = ordered_no;
	}

	public Vendor getVendor()
	{
		return vendor;
	}

	public void setVendor(Vendor vendor)
	{
		this.vendor = vendor;
	}

	public Set<String> getTags()
	{
		return tags;
	}

	public void setTags(Set<String> tags)
	{
		this.tags = tags;
	}

	public Set<Review> getReviews()
	{
		return reviews;
	}

	public void addReview(Review review)
	{
		this.reviews.add(review);
	}
	
	public long getId()
	{
		return p_id;
	}
}
