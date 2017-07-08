package app.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

/**
 * Created by grom on 03/07/2017.
 * Project feedback
 * author <grom25174@gmail.com>
 */
@Entity
@Table(name = "feed_back")
public class FeedBack implements Serializable {
    @Id
    @Column(nullable = false)
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String surname;

    private Integer quantity;

    @Column(name = "will_come")
    private Boolean willCome;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Boolean getWillCome() {
        return willCome;
    }

    public void setWillCome(Boolean willCome) {
        this.willCome = willCome;
    }
}