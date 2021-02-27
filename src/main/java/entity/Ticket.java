package entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Ticket {
    public Ticket (){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(name = "GGG",joinColumns = @JoinColumn(name = "ticket_lol", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk"))
    , inverseJoinColumns = @JoinColumn(name = "guest_lol"))
    private List<Person> guests;

    @Column(unique = true)
    private Date date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Person> getGuests() {
        return guests;
    }

    public void setGuests(List<Person> guests) {
        this.guests = guests;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}
