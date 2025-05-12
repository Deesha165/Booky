package com.example.Bookify.service.impl;


import com.example.Bookify.dto.event.EventCreationRequest;
import com.example.Bookify.dto.event.EventDetailsResponse;
import com.example.Bookify.dto.event.EventUpdateRequest;
import com.example.Bookify.entity.event.Category;
import com.example.Bookify.entity.event.Event;
import com.example.Bookify.entity.user.User;
import com.example.Bookify.exception.DuplicateResourceException;
import com.example.Bookify.exception.EntityNotFoundException;
import com.example.Bookify.exception.ResourceType;
import com.example.Bookify.mapper.EventMapper;
import com.example.Bookify.repository.CategoryRepository;
import com.example.Bookify.repository.EventTagRepository;
import com.example.Bookify.repository.TagRepository;
import com.example.Bookify.repository.projection.EventWithBookingStatus;
import com.example.Bookify.repository.EventRepository;
import com.example.Bookify.service.EventService;
import com.example.Bookify.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final EventMapper eventMapper;
    private final UserService userService;

    private volatile List<EventDetailsResponse> trendingCachedEvents=List.of();

    @Override
    @Transactional
    public EventDetailsResponse createEvent(EventCreationRequest eventCreationRequest) {

          Boolean eventExists=eventRepository.checkEventExistence(eventCreationRequest.name());

          if(eventExists) throw new DuplicateResourceException("Event with name = "+eventCreationRequest.name()+ "already Exists", ResourceType.EVENT);

          Event event= eventMapper.toEventEntity(eventCreationRequest);

          User creator=userService.getUser(eventCreationRequest.creatorId());
        Category category=categoryRepository.findById(eventCreationRequest.categoryId())
                .orElseThrow(()->new EntityNotFoundException("Category with id= "+eventCreationRequest.categoryId()+" doesn't exist"));

        event.setCategory(category);
           event.setCreatedBy(creator);

         Event savedEvent= eventRepository.save(event);

          return eventMapper.toEventResponse(savedEvent);
    }

    @Override
    @Transactional
    public EventDetailsResponse updateEvent(EventUpdateRequest eventUpdateRequest) {

        Event event=getEventEntityById(eventUpdateRequest.eventId());

        event.setEventTime(eventUpdateRequest.eventTime());
                       event.setDescription(event.getDescription());
        event.setImage(event.getImage());

        return eventMapper.toEventResponse(event);
    }

    @Override
    @Transactional
    public int deleteEvent(int eventId) {

        Event event=getEventEntityById(eventId);
        eventRepository.delete(event);
        return eventId;
    }

    @Override
    public EventDetailsResponse getEvent(int eventId) {
        return eventMapper.toEventResponse(getEventEntityById(eventId));
    }


    @Override
    public Page<EventDetailsResponse> getAllEventsPaged(int page,int size,String sortBy) {
        int userId=1;
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortBy));
              Page<EventWithBookingStatus>events=eventRepository.findPagedEvents(userId,pageable);
        return  events.map(eventMapper::fromProjectedEventToEventResponse);
    }

    @Override
    public Page<EventDetailsResponse> getEventsPagedFilteredByCategory(String categoryName,int page,int size,String sortBy) {

        int userId=1;
        Pageable pageable=PageRequest.of(page,size,Sort.by(Sort.Direction.DESC,sortBy));
        Page<EventWithBookingStatus> events=eventRepository.findPagedEventsFilteredByCategory(categoryName,userId,pageable);

    return events.map(eventMapper::fromProjectedEventToEventResponse);
    }

    @Scheduled(fixedRate = 1,timeUnit = TimeUnit.HOURS)
    public void updateTrendingEvents() {

        List<Integer> topTagIds=tagRepository.getTopTagIds();

        int userId=1;// later we would expose auth id from security context
        List<EventWithBookingStatus> event=eventRepository.findTrendingEventsFilteredByTagOccurrence(topTagIds,userId);

        this.trendingCachedEvents=event.stream().map(eventMapper::fromProjectedEventToEventResponse).toList();

    }


    public Event getEventEntityById(int eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(()->new EntityNotFoundException("Event with id = " + eventId+ "doesn't exist"));

    }

    public List<EventDetailsResponse> getTrendingEvents() {
        return this.trendingCachedEvents;
    }


}
