package br.com.nlw.events.service;

import br.com.nlw.events.exception.EventNotFoundException;
import br.com.nlw.events.model.Event;
import br.com.nlw.events.model.Subscription;
import br.com.nlw.events.model.User;
import br.com.nlw.events.repository.EventRepository;
import br.com.nlw.events.repository.SubscriptionRepository;
import br.com.nlw.events.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public Subscription createSubscription(String eventName, User user) {
        Event event = eventRepository.findByPrettyName(eventName);

        if (event == null)
            throw new EventNotFoundException(String.format("Evento %s não existe", eventName));

        User userInDatabase = userRepository.findByEmail(user.getEmail());

        if (userInDatabase == null)
            userInDatabase = userRepository.save(user);

        Subscription subscription = new Subscription();
        subscription.setEvent(event);
        subscription.setSubscriber(userInDatabase);

        subscription = subscriptionRepository.save(subscription);

        return subscription;
    }
}
