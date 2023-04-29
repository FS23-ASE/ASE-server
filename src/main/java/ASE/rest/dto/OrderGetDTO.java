package ASE.rest.dto;

import ASE.entity.Book;
import ASE.entity.Status;

import java.util.Date;
import java.util.List;

public class OrderGetDTO {
    private Long id;

    private Long buyerId;

    private Long sellerId;

    private List<Long> books;

    private double amount;

    private Status status;

    private Date date;

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id=id;
    }

    public Long getBuyerId(){
        return buyerId;
    }

    public void setBuyerId(Long buyerId){
        this.buyerId=buyerId;
    }

    public Long getSellerId(){
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
