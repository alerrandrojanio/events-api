package br.com.nlw.events.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.nlw.events.model.Event;
import br.com.nlw.events.service.EventService;

@RestController
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping("/events")
    public Event createEvent(@RequestBody Event event) {
        return eventService.createEvent(event);
    }

    @GetMapping("/events")
    public List<Event> getEvents() {
        return eventService.getEvents();
    }

    @GetMapping("/events/{prettyName}")
    public ResponseEntity<Event> getEventByPrettyName(@PathVariable String prettyName) {
        Event event = eventService.getByPrettyName(prettyName);

        if (event != null)
            return ResponseEntity.ok().body(event);

        return ResponseEntity.notFound().build();
    }
}
