package com.example.electricity.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "division_wise_broadcasting_parent")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DivisionWiseLoadSheddingBroadcasting {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reason", nullable = false)
    private String reason;

    @Column(name = "day", nullable = false)
    private LocalDate day;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "division_id", nullable = false)
    private Long divisionId;

    @Column(name = "district_id")
    private Long districtId;

    @Column(name = "upazila_id")
    private Long upazilaId;

    @Column(name = "union_parishad_id")
    private Long unionParishadId;
}
