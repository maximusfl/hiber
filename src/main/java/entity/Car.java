package entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(uniqueConstraints =  @UniqueConstraint(columnNames = {"color"}))
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
    }


    private String color;

    @OneToMany(mappedBy = "car")
    private List<Purchase> purchases;
}
