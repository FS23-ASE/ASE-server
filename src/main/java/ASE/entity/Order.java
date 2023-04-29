package ASE.entity;


import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "ORDER")
public class Order {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Long buyerId;

    @Column(nullable = false)
    private  Long sellerId;

    @ElementCollection
    private List<Long> books;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private Status status;

    @Column(nullable = false)
    private Date date;


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

    public List<Long> getBooks(){
        return books;
    }

    public void setBooks(List<Long> books){
        this.books=books;
    }

    public double getAmount(){
        return amount;
    }

    public void setAmount(double amount){
        this.amount=amount;
    }

    public Status getStatus(){
        return status;
    }

    public void setStatus(Status status){
        this.status=status;
    }

    public Date getDate(){
        return date;
    }

    public void setDate(Date date){
        this.date=date;
    }


}
