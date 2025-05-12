package com.example.Bookify.entity.event;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
@Table(name = "categories",
        indexes = @Index(name = "idx_category_name",columnList = "category_name",unique = true))
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private int id;


    @Column(name = "category_name",nullable = false)
    private String name;
}
