package co.spribe.testtask.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "check_in_date")
    private LocalDate checkInDate;

    @Column(name = "check_out_date")
    private LocalDate checkOutDate;

    @ManyToOne
    private Unit unit;

    @Column(name = "cancelled")
    private Boolean cancelled;

    @OneToMany(mappedBy = "booking")
    private List<Payment> payments;

    @OneToOne
    private User user;
}
