package ASE.rest.dto;

import ASE.entity.Book;
import ASE.entity.User;

import java.util.List;

public class CartPostDTO {
    private Long id;
    private List<Book> books;
    private int quantity;
    private double price;
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

    public double getPrice(){
        return price;
    }

    public void setPrice(double price){
        this.price=price;
    }

    public long getUser(){
        return userId;
    }

    public void setUser(long userId){
        this.userId=userId;
    }
}
