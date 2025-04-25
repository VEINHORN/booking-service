package co.spribe.testtask.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@Table(name = "unit")
public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "number_of_rooms")
    private Integer numberOfRooms;

    @Enumerated(EnumType.STRING)
    @Column(name = "accomodation_type")
    private AccomodationType accomodationType;

    @Column(name = "floor")
    private Integer floor;

    @Column(name = "cost")
    private BigDecimal cost;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "unit")
    private List<Booking> bookings = new ArrayList<>();

    @OneToOne
    private User user;
}
