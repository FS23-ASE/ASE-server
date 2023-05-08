package ASE.entity;

import javax.persistence.*;

@Entity
@Table(name = "CONTACT")
public class Contact {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Long accepter;

    @Column(nullable = false)
    private Long sender;
    @Column(nullable = false)
    private Long orderId;
    @Column(nullable = false)
    private String msg;

    public Long getSender() {
        return sender;
    }

    public void setSender(Long sender) {
        this.sender = sender;
    }
    public Long getAccepter() {
        return accepter;
    }

    public void setAccepter(Long accepter) {
        this.accepter = accepter;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
