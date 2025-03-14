package br.com.nlw.events.controller;

import br.com.nlw.events.DTO.ErrorMessage;
import br.com.nlw.events.exception.EventNotFoundException;
import br.com.nlw.events.exception.SubscriptionConflictException;
import br.com.nlw.events.exception.UserIndicadorNotFoundException;
import br.com.nlw.events.model.Subscription;
import br.com.nlw.events.model.User;
import br.com.nlw.events.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
            return buildErrorResponse(ex, HttpStatus.NOT_FOUND);

        } catch (SubscriptionConflictException ex) {
            return buildErrorResponse(ex, HttpStatus.CONFLICT);
        }

        return ResponseEntity.badRequest().build();
    }

    private ResponseEntity<ErrorMessage> buildErrorResponse(Exception ex, HttpStatus status) {
        return ResponseEntity.status(status).body(new ErrorMessage(ex.getMessage()));
    }
}
