package com.training.rledenev.bankapp.entity;

import com.training.rledenev.bankapp.entity.enums.Status;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "managers")
@Getter
@Setter
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @OneToMany(
            mappedBy = "manager",
            cascade = {MERGE, PERSIST, REFRESH, DETACH},
            fetch = FetchType.LAZY
    )
    private Set<Client> clients = new HashSet<>();

    @OneToMany(
            mappedBy = "manager",
            cascade = {MERGE, PERSIST, REFRESH, DETACH},
            fetch = FetchType.LAZY
    )
    private Set<Product> products = new HashSet<>();

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Enumerated
    @Column(name = "status")
    private Status status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Manager setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public Manager setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Manager setStatus(Status status) {
        this.status = status;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Manager manager = (Manager) o;
        return Objects.equals(id, manager.id)
                && Objects.equals(firstName, manager.firstName)
                && Objects.equals(lastName, manager.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName);
    }

    @Override
    public String toString() {
        return "Manager{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
