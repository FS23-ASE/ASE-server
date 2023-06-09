package ASE.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Internal Cart Representation
 * This class composes the internal representation of the cart and defines how
 * the cart is stored in the database.
 * Every variable will be mapped into a database field with the @Column
 * annotation
 * - nullable = false -> this cannot be left empty
 */
@Entity
@Table(name = "CART")
public class Cart implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    private Long id;
    @ManyToMany
    private List<Book> books;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private float prices;

    @Column(nullable = false)
    private Long userId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Book> getBooks(){
        return books;
    }

    public void setBooks(List<Book> books){
        this.books=books;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setQuantity(int quantity){
        this.quantity=quantity;
    }

    public float getPrices(){
        return prices;
    }

    public void setPrices(float prices){
        this.prices=prices;
    }

    public Long getUserId(){
        return userId;
    }

    public void setUserId(Long userId){
        this.userId=userId;
    }




}
