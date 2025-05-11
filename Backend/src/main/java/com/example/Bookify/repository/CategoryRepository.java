package com.example.Bookify.repository;

import com.example.Bookify.entity.event.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
}
