package com.gas.fuelapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity(name = "Driver")
@Table(name = "Driver")
@AllArgsConstructor
public class Driver {

    @Id
    @Column
    private String driverId;

    /*In the future, we can add more information about the driver in this class*/
}
