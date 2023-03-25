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
    private long seller_id;
    @Column(nullable = true)
    private long buyer_id;

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }

    public long getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(long seller_id) {
        this.seller_id = seller_id;
    }

    public long getBuyer_id() {
        return buyer_id;
    }

    public void setBuyer_id(long buyer_id) {
        this.buyer_id = buyer_id;
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
}
