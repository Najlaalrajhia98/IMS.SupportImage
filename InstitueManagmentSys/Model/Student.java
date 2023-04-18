package com.thedynamicdoers.InstitueManagmentSys.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @Column
    public String name;
    @Pattern(regexp="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}", message="Invalid email")
    @Column
    public String email;
    @Column
    public String imageName;

}