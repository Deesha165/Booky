package com.example.Bookify.repository;

import com.example.Bookify.entity.booking.Booking;
import com.example.Bookify.entity.event.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category,Integer> {

    @Query("""
            select exists( select 1 from Category c where c.name=:categoryName)
            """)
    Boolean checkCategoryExistense(String categoryName);
}
