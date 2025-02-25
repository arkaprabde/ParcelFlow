package App.Entities;

import jakarta.persistence.*;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Cart
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "buyer_email", referencedColumnName = "email")
    @JsonBackReference("buyer-cart")
    private Buyer buyer;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    private Set<Product> products;

    public Cart()
    {
        super();
    }

    public Cart(Buyer buyer, Set<Product> products)
    {
        super();
        this.buyer = buyer;
        this.products = products;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Buyer getBuyer()
    {
        return buyer;
    }

    public void setBuyer(Buyer buyer)
    {
        this.buyer = buyer;
    }

    public Set<Product> getProducts()
    {
        return products;
    }

    public void setProducts(Set<Product> products)
    {
        this.products = products;
    }
}