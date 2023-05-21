package ASE.rest.dto;



public class BookPostDTO {

    private String name;
    private String publisher;
    private String description;
    private Boolean status;
    private long sellerId;
    private String author;
    private long buyerId;
    private float price;
    private String category;

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private String image;


//    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
//    private Blob image;
//    private String imagestring;
//
//    public String getImagestring() {
//        return imagestring;
//    }
//
//    public void setImagestring(String imagestring) {
//        this.imagestring = imagestring;
//    }
//
//    public Blob getImage() {
//        return image;
//    }
//
//    public void setImage(Blob image) {
//        this.image = image;
//    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public long getBuyerid() {
        return buyerId;
    }

    public void setBuyerid(long buyerId) {
        this.buyerId = buyerId;
    }

    public long getSellerid() {
        return sellerId;
    }

    public void setSellerid(long sellerId) {
        this.sellerId = sellerId;
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
