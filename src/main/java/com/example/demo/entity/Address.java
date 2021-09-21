package com.example.demo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;


@Getter
@Setter
@ToString(exclude = "user")
@Entity
public class Address {
    @Id
    private Long id;

    @Column
    private String country;

    @Column
    private String city;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;
}
