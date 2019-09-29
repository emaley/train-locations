package com.ewan.timetable.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Data
@Builder
@Table(name = "train", uniqueConstraints = {@UniqueConstraint(columnNames={"id"})})
public class Train implements Serializable {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private Long trainId;

    @Column(name = "gps_latitude")
    private BigDecimal gpsLatitude;

    @Column(name = "gps_longitude")
    private BigDecimal gpsLongitude;

    @Column(name = "train_name")
    private String trainName;

    @Column(name = "destination")
    private String destination;

    @Column(name = "speed")
    private BigDecimal speed;

    @Tolerate
    public Train() {}
}
