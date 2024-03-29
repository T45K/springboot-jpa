package com.rakuten.internship.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "No")
    private long number;

    @Column(name = "Title", nullable = false)
    private String title;

    @Column(name = "Description")
    private String description;
}
