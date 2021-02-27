package entity;

import javax.persistence.*;
import java.util.List;

@Entity

public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    @JoinColumn(name = "purchase_id", referencedColumnName = "id")
    private List<Item> items;

    public Purchase() {
    }

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "car_lol_id", referencedColumnName = "color")
    private Car car;

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Item> getItems() {
        return items;
    }
    @Column(unique = true)
    private String name;

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
