package com.weather_forecast_report.models.entity;

import lombok.*;

import javax.persistence.*;

@Table(name = "user")
@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @NonNull
    @Column(nullable = false, length = 50)
    private String username;

    @NonNull
    @Column(nullable = false)
    private String password;
}
