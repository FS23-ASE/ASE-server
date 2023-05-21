package ASE.rest.dto;



public class ContactPostDTO {
    private Long id;

    private Long sender;

    private Long accepter;

    private Long orderId;

    private String msg;

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id=id;
    }

    public Long getSender(){
        return sender;
    }

    public void setSender(Long sender){
        this.sender=sender;
    }

    public Long getAccepter(){
        return accepter;
    }

    public void setAccepter(Long accepter){
        this.accepter=accepter;
    }

    public double getOrderId(){
        return orderId;
    }

    public void setOrderId(Long orderId){
        this.orderId=orderId;
    }

    public String getMsg(){
        return msg;
    }

    public void setMsg(String msg){
        this.msg=msg;
    }
}