package co.spribe.testtask.model.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
}
