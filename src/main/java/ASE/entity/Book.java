package ASE.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Blob;
import java.util.Date;

@Entity
@Table(name = "BOOK")
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Boolean status;//1 for available, 0 for sold out
    @Column(nullable = false)
    private String author;
    @Column(nullable = false)
    private String publisher;
    @Column(nullable = true)
    private String description;
    @Lob
    @Column(nullable = true)
    private Blob image;
    @Column(nullable = false)
    private long sellerid;
    @Column(nullable = true)
    private long buyerid;

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }

    public long getSellerid() {
        return sellerid;
    }

    public void setSellerid(long seller_id) {
        this.sellerid = seller_id;
    }

    public long getBuyerid() {
        return buyerid;
    }

    public void setBuyerid(long buyerid) {
        this.buyerid = buyerid;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }


    public Book(String name, String author, String description, String publisher, Long sellerid) {
        this.name = name;
        this.author = author;
        this.description = description;
        this.publisher = publisher;
        this.sellerid = sellerid;
    }

    public Book(){

    }
}
