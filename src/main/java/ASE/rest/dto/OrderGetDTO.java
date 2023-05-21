package ASE.rest.dto;

import ASE.entity.Book;

import java.util.List;

public class OrderGetDTO {
    private Long id;

    private Long buyerId;

    private Long sellerId;

    private List<Book> books;

    private double amount;

    private String status;

    private String date;

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
