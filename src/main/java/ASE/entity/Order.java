package ASE.entity;



import javax.persistence.*;
import java.util.List;

/**
 * Internal Order Representation
 * This class composes the internal representation of the order and defines how
 * the order is stored in the database.
 * Every variable will be mapped into a database field with the @Column
 * annotation
 * - nullable = false -> this cannot be left empty
 */
@Entity
@Table(name = "ORDERS")
public class Order {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Long buyerId;

    @Column(nullable = false)
    private Long sellerId;

    @OneToMany
    private List<Book> books;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String date;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public  Long getSellerId(){
        return sellerId;
    }

    public void setSellerId(Long sellerId){
        this.sellerId=sellerId;
    }

    public List<Book> getBooks(){
        return books;
    }

    public void setBooks(List<Book> books){
        this.books=books;
    }

    public double getAmount(){
        return amount;
    }

    public void setAmount(double amount){
        this.amount=amount;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status=status;
    }

    public String getDate(){
        return date;
    }

    public void setDate(String date){
        this.date=date;
    }


}
