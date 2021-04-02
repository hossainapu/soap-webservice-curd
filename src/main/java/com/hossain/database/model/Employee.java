package com.hossain.database.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import javax.persistence.*;
import java.io.Serializable;
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "employee")
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Integer id;

    @Column(name = "first_name", nullable = false, length = 50)
    String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    String lastName;

    @Column(name = "email", unique = true, nullable = false, length = 100)
    String email;

    @Column(name = "phone_number", unique = true, nullable = false, length = 20)
    String phoneNumber;

    @Column(name = "department_name")
    String departmentName;

    @Column(name = "password", nullable = false, length = 100)
    String password;

    @Column(name = "status", nullable = false)
    Integer status;
}
