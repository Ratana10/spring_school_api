package com.piseth.schoolapi.auditing;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "testing")
public class Test extends AuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cat_id")
    private Long id;

    @Column(name = "cat_name")
    private String name;

}
