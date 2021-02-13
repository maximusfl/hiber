package entity;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "person")
public class Person {
    public Person() {
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;


    @OneToMany
    private Set<Purchase> orders;


    @OneToOne
    private Car car;

    private Address address;

    public Set<Purchase> getOrders() {
        return orders;
    }

    public void setOrders(Set<Purchase> orders) {
        this.orders = orders;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address){
        this.address = address;
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



    @Embeddable
    public static
    class Address{
        public Address(){}

        public Address(String street, int houseNumber){
            this.street = street;
            this.houseNumber = houseNumber;
        }
        String street;
        int houseNumber;
    }
}
