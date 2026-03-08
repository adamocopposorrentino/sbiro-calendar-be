package sorrentino.sbirocalendar.entity;

// src/main/java/com/tuoprogetto/entity/Disponibilita.java

import jakarta.persistence.*;

@Entity
@Table(name = "availability")
public class Availability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user", nullable = false)
    private User user;

    @Column(name = "day_available", nullable = false)
    private Integer day; // Da 1 a 31
    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }
    // Genera qui i costruttori, Getter e Setter
}