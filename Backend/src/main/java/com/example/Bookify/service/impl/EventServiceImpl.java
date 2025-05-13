package com.example.Bookify.service.impl;


import com.example.Bookify.dto.event.CategoryResponse;
import com.example.Bookify.dto.event.EventCreationRequest;
import com.example.Bookify.dto.event.EventDetailsResponse;
import com.example.Bookify.dto.event.EventUpdateRequest;
import com.example.Bookify.entity.event.Category;
import com.example.Bookify.entity.event.Event;
import com.example.Bookify.entity.event.EventTag;
import com.example.Bookify.entity.event.Tag;
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
import com.example.Bookify.util.AuthUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private  final EventTagRepository eventTagRepository;
    private final EventMapper eventMapper;
    private final UserService userService;

    private volatile Set<EventDetailsResponse> trendingCachedEvents=new HashSet<>();

    private AuthUtil authUtil;

    @Override
    @Transactional
    public EventDetailsResponse createEvent(EventCreationRequest eventCreationRequest) {

          Boolean eventExists=eventRepository.checkEventExistence(eventCreationRequest.name());

          if(eventExists) throw new DuplicateResourceException("Event with name = "+eventCreationRequest.name()+ "already Exists", ResourceType.EVENT);

          Event event= eventMapper.toEventEntity(eventCreationRequest);
        User authUser=authUtil.getAuthenticatedUser();

        User creator=userService.getUser(authUser.getId());
        Category category=categoryRepository.findById(eventCreationRequest.categoryId())
                .orElseThrow(()->new EntityNotFoundException("Category with id= "+eventCreationRequest.categoryId()+" doesn't exist"));



        event.setCategory(category);
        event.setCreatedBy(creator);

         Event savedEvent= eventRepository.save(event);

        processTags(eventCreationRequest.description(),savedEvent);

        EventDetailsResponse baseResponse = eventMapper.toEventResponse(savedEvent);


        return new EventDetailsResponse(
                baseResponse.id(),
                baseResponse.name(),
                baseResponse.description(),
                baseResponse.eventTime(),
                 baseResponse.venue(),
                  baseResponse.pricePerTicket(),
                  baseResponse.availableTickets(),
                    baseResponse.image(),
                     eventMapper.toCategoryResponse(category),
                null
        );
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

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortBy));
        User authUser=authUtil.getAuthenticatedUser();

              Page<EventWithBookingStatus>events=eventRepository.findPagedEvents(authUser.getId(),pageable);
        return  events.map(eventMapper::fromProjectedEventToEventResponse);
    }

    @Override
    public Page<EventDetailsResponse> getEventsPagedFilteredByCategory(String categoryName,int page,int size,String sortBy) {


        Pageable pageable=PageRequest.of(page,size,Sort.by(Sort.Direction.DESC,sortBy));
        User authUser=authUtil.getAuthenticatedUser();

        Page<EventWithBookingStatus> events=eventRepository.findPagedEventsFilteredByCategory(categoryName,authUser.getId(),pageable);

    return events.map(eventMapper::fromProjectedEventToEventResponse);
    }

    @Scheduled(fixedRate = 4,timeUnit = TimeUnit.MINUTES)
    public void updateTrendingEvents() throws IllegalAccessException {

        try{
            User authUser=authUtil.getAuthenticatedUser();

            this.trendingCachedEvents.clear();
            List<Integer> topTagIds=tagRepository.getTopTagIds();


            List<EventWithBookingStatus> event=eventRepository.findTrendingEventsFilteredByTagOccurrence(topTagIds,authUser.getId());

            this.trendingCachedEvents=event.stream().map(eventMapper::fromProjectedEventToEventResponse).collect(Collectors.toSet());

            this.trendingCachedEvents.forEach((trend)->{
                log.info("this is trending cached events {}",trend);
            });
        }
        catch (Exception ex){

        }


    }


    public Event getEventEntityById(int eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(()->new EntityNotFoundException("Event with id = " + eventId+ "doesn't exist"));

    }

    public Set<EventDetailsResponse> getTrendingEvents() {
        return this.trendingCachedEvents;
    }

    @Override
    public CategoryResponse createCategory(String categoryName) {
       Boolean categoryExists= categoryRepository.checkCategoryExistense(categoryName);
        if(categoryExists) throw new DuplicateResourceException("Category with name = "+categoryName+ "already Exists", ResourceType.EVENT);

        Category category=Category.builder().name(categoryName).build();


       Category categoryEntity=categoryRepository.save(category);


        return eventMapper.toCategoryResponse(categoryEntity);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream().map(eventMapper::toCategoryResponse).toList();
    }


    @Transactional
    private void processTags(String text,Event event){
        Map<String,Integer>detectedTags=detectTags(text);
        List<Tag> existingTags=tagRepository.findAll();

        List<EventTag>newEventTags=new ArrayList<>();

        existingTags.forEach(tag->{
           if (detectedTags.containsKey(tag.getName())){
               tag.setOccurrence(tag.getOccurrence()+detectedTags.get(tag.getName()));
               detectedTags.remove(tag.getName());

               newEventTags.add(EventTag.builder().event(event).tag(tag).build());
           }

        });



        List<Tag> newAddedTags = detectedTags.entrySet().stream()
                .map(entry ->
                {
                    Tag tag=new Tag(entry.getKey(), entry.getValue());
                    newEventTags.add(EventTag.builder().event(event).tag(tag).build());
                  return   tag;
                }
                        )
                .toList();

        tagRepository.saveAll(newAddedTags);
          eventTagRepository.saveAll(newEventTags);

    }
    private Map<String, Integer> detectTags(String text) {
        Map<String, Integer> detectedTags = new HashMap<>();

        for (String word : text.split(" ")) {
            if (word.startsWith("#")) {
                detectedTags.merge(word, 1, Integer::sum);
            }
        }

        return detectedTags;
    }

}
