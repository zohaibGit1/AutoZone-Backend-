package com.Vechile_Service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Vehicle_Visit_Table")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VehicleVisit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long visitId;

    private LocalDate visitDate;

    private int currentKm;

    private LocalDateTime checkInTime;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @OneToMany(mappedBy = "vehicleVisit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Complaint> complaints = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        visitDate = LocalDate.now();
        checkInTime = LocalDateTime.now();
    }

}
