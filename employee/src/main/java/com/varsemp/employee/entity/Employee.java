package com.varsemp.employee.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employees")
public class Employee {

    @Id
    private long id;
    private String name;
    private String email;
    private long salary;
}
