package com.weather_forecast_report.models.entity;

import lombok.*;

import javax.persistence.*;

@Table(name = "location")
@Entity
@NoArgsConstructor
@Getter
@Setter
@RequiredArgsConstructor
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @NonNull
    @Column(nullable = false, length = 100)
    private String country;

    @NonNull
    @Column(nullable = false, length = 100)
    private String city;


    @ManyToOne
    @JoinColumn(name = "user_id")
    @NonNull
    private User user;
}
