package com.example.Bookify.repository;

import com.example.Bookify.entity.event.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag,Integer> {

    @Query("""
            select t.id from Tag t 
            order by t.occurrence desc 
            limit 5
            """)
    List<Integer> getTopTagIds();
}
