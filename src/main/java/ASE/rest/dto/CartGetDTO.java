package ASE.rest.dto;

import ASE.entity.Book;
import java.util.List;

public class CartGetDTO {
    private Long id;
    private List<Book> books;
    private int quantity;
    private float prices;
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

    public long getUserId(){
        return userId;
    }

    public void setUserId(long userId){
        this.userId=userId;
    }
}
