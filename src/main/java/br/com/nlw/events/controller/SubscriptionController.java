package br.com.nlw.events.controller;

import br.com.nlw.events.DTO.SubscriptionRankingItemDTO;
import br.com.nlw.events.exception.EventNotFoundException;
import br.com.nlw.events.exception.SubscriptionConflictException;
import br.com.nlw.events.exception.UserIndicadorNotFoundException;
import br.com.nlw.events.extensions.ErrorBuilderExtensions;
import br.com.nlw.events.model.Subscription;
import br.com.nlw.events.model.User;
import br.com.nlw.events.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping("/subscription/{prettyName}")
    public ResponseEntity<?> createSubscription(@PathVariable String prettyName, @RequestBody User subscriber){
        try {
            Subscription subscription = subscriptionService.createSubscription(prettyName, subscriber);

            if (subscription != null)
                return ResponseEntity.ok().body(subscription);
        }
        catch (EventNotFoundException | UserIndicadorNotFoundException ex) {
            return ErrorBuilderExtensions.buildErrorResponse(ex, HttpStatus.NOT_FOUND);

        }
        catch (SubscriptionConflictException ex) {
            return ErrorBuilderExtensions.buildErrorResponse(ex, HttpStatus.CONFLICT);
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/subscription/{prettyName}/ranking")
    public ResponseEntity<?> getRankingByEvent(@PathVariable String prettyName){
        try{
            List<SubscriptionRankingItemDTO> subscriptionRanking = subscriptionService.getRankingByEvent(prettyName);

            return ResponseEntity.ok(subscriptionRanking.subList(0, Math.min(3, subscriptionRanking.size())));
        }
        catch(EventNotFoundException ex){
            return ErrorBuilderExtensions.buildErrorResponse(ex, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/subscription/{prettyName}/ranking/{userId}")
    public ResponseEntity<?> getRankingByEventAndUser(@PathVariable String prettyName, @PathVariable Integer userId){
        try{
            return ResponseEntity.ok(subscriptionService.getRankingByEventAndUser(prettyName, userId));
        }
        catch(Exception ex){
            return ErrorBuilderExtensions.buildErrorResponse(ex, HttpStatus.NOT_FOUND);
        }
    }
}
