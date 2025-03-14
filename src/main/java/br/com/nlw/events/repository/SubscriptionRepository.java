package br.com.nlw.events.repository;

import br.com.nlw.events.DTO.SubscriptionRankingItemDTO;
import br.com.nlw.events.model.Event;
import br.com.nlw.events.model.Subscription;
import br.com.nlw.events.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubscriptionRepository extends CrudRepository<Subscription, Integer> {
    public Subscription findByEventAndSubscriber(Event event, User user);

    @Query(value = "select count(subscription_id) as quantidade, indication_user_id, name" +
            " from subscriptions inner join users" +
            " on subscriptions.indication_user_id = users.user_id " +
            " where indication_user_id is not null" +
            " and event_id = :eventId" +
            " group by indication_user_id" +
            " order by quantidade desc", nativeQuery = true)
    public List<SubscriptionRankingItemDTO> generateRanking(@Param("eventId") Integer eventId);
}
