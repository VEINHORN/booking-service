package com.veinhorn.booking.service.model.entity;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private BookingStatus status;

    @OneToMany(mappedBy = "booking")
    private List<Payment> payments;

    @OneToOne
    private User user;
}
