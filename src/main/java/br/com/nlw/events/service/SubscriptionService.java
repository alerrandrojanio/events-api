package br.com.nlw.events.service;

import br.com.nlw.events.DTO.SubscriptionRankingByUserDTO;
import br.com.nlw.events.DTO.SubscriptionRankingItemDTO;
import br.com.nlw.events.exception.EventNotFoundException;
import br.com.nlw.events.exception.UserIndicadorNotFoundException;
import br.com.nlw.events.model.Event;
import br.com.nlw.events.model.Subscription;
import br.com.nlw.events.model.User;
import br.com.nlw.events.repository.EventRepository;
import br.com.nlw.events.repository.SubscriptionRepository;
import br.com.nlw.events.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

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

    public List<SubscriptionRankingItemDTO> getRankingByEvent(String prettyName){
        Event event = eventRepository.findByPrettyName(prettyName);

        if (event == null)
            throw new EventNotFoundException(String.format("Ranking do evento %s não existe", prettyName));

        return subscriptionRepository.generateRanking(event.getEventId());
    }

    public SubscriptionRankingByUserDTO getRankingByEventAndUser(String prettyName, Integer userId){
        List<SubscriptionRankingItemDTO> ranking = getRankingByEvent(prettyName);

        SubscriptionRankingItemDTO item = ranking.stream().filter(i -> i.userId().equals(userId)).findFirst().orElse(null);

        if(item == null)
            throw new UserIndicadorNotFoundException(String.format("Não há inscrições com indicação do usuário %d", userId ));

        Integer index = IntStream.range(0, ranking.size()).filter(i -> ranking.get(i).userId().equals(userId)).findFirst().getAsInt();

        return new SubscriptionRankingByUserDTO(item, index + 1);
    }
}
