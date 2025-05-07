package no.ntnu.gr10.bachelor_producer_rest_api.rabbit;

public class RabbitQueueUtils {
  private RabbitQueueUtils() {
    // Private constructor to prevent instantiation
  }

  public static String getDynamicQueueName(String companyId, String entity, RabbitQueueType type) {
    return "producer." + entity + "." + type.toString() + "." + "company-" + companyId;
  }
}
