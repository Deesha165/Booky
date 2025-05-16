package com.example.Bookify;

import com.example.Bookify.dto.event.EventCreationRequest;
import com.example.Bookify.dto.event.EventDetailsResponse;
import com.example.Bookify.entity.event.Category;
import com.example.Bookify.entity.event.Event;
import com.example.Bookify.entity.user.User;
import com.example.Bookify.exception.DuplicateResourceException;
import com.example.Bookify.exception.EntityNotFoundException;
import com.example.Bookify.mapper.EventMapper;
import com.example.Bookify.repository.CategoryRepository;
import com.example.Bookify.repository.EventRepository;
import com.example.Bookify.repository.EventTagRepository;
import com.example.Bookify.repository.TagRepository;
import com.example.Bookify.service.BookingService;
import com.example.Bookify.service.EventService;
import com.example.Bookify.service.UserService;
import com.example.Bookify.service.impl.EventServiceImpl;
import com.example.Bookify.util.AuthUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EventServiceImplTest {
    private  EventRepository eventRepository;
    private  CategoryRepository categoryRepository;
    private  TagRepository tagRepository;
    private  EventMapper eventMapper;
    private  UserService userService;
    private EventTagRepository eventTagRepository;
    private volatile Set<EventDetailsResponse> trendingCachedEvents= new HashSet<>();

    private AuthUtil authUtil;
    private EventService eventService;
    private BookingService bookingService;
    @BeforeEach
    void setUp(){
        eventRepository= mock(EventRepository.class);
        categoryRepository=mock(CategoryRepository.class);
        tagRepository=mock(TagRepository.class);
        eventRepository=mock(EventRepository.class);
        eventMapper=mock(EventMapper.class);
        userService=mock(UserService.class);
        trendingCachedEvents=mock(Set.class);
        eventTagRepository=mock(EventTagRepository.class);
        bookingService=mock(BookingService.class);

  authUtil=mock(AuthUtil.class);
        eventService=new EventServiceImpl(eventRepository,categoryRepository,tagRepository,eventTagRepository,eventMapper,userService,bookingService,trendingCachedEvents,authUtil);

    }
    @Test
    void createEvent_WhenEventAlreadyExist_ShouldThrowException(){
        String eventName="Data structures and Algorithms";
        EventCreationRequest eventCreationRequest=EventCreationRequest.builder()
                .name(eventName).build();
        when(eventRepository.checkEventExistence(eventName)).thenReturn(true);

       assertThrows(DuplicateResourceException.class,()->eventService.createEvent(eventCreationRequest));
    }
    @Test
   void deleteEvent_WhenEventNotExist_ShouldThrowException(){
        int eventId=5;
        Event event= Event.builder().id(2).build();

        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,()->eventService.deleteEvent(5));

   }

}
