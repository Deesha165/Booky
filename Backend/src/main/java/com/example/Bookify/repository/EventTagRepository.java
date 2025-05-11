package com.example.Bookify.repository;

import com.example.Bookify.entity.event.Event;
import com.example.Bookify.entity.event.EventTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventTagRepository extends JpaRepository<EventTag,Integer> {




}
