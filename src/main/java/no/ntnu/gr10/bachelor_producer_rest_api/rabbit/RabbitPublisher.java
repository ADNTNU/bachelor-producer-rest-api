package no.ntnu.gr10.bachelor_producer_rest_api.rabbit;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class RabbitPublisher {

  private final Logger logger = Logger.getLogger(getClass().getName());
  private final RabbitTemplate rabbitTemplate;
  private final ConnectionFactory connectionFactory;

  /**
   * Publish a stringified‐map (i.e. valid JSON) to the given queue.
   *
   * @param queueName the target queue
   * @param message   the payload (stringified map)
   */
  public void publish(String queueName, String message) {

    RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);

    rabbitAdmin.declareQueue(new Queue(queueName, true));

    rabbitTemplate.convertAndSend(queueName, message);

    logger.info("Published message to queue " + queueName);
  }
}
