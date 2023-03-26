package ASE.rest.dto;

import java.sql.Blob;

public class BookPostDTO {

    private String name;
    private String publisher;
    private String description;
    private Boolean status;
    private long sellerid;
    private String author;
    private long buyerid;
    private Blob image;

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public long getBuyerid() {
        return buyerid;
    }

    public void setBuyerid(long buyerid) {
        this.buyerid = buyerid;
    }

    public long getSellerid() {
        return sellerid;
    }

    public void setSellerid(long sellerid) {
        this.sellerid = sellerid;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getAuthor() {
        return author;
    }


    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
