package kz.bitlab.rabbit.middle03rabbit.sender;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import kz.bitlab.rabbit.middle03rabbit.dto.OrderDto;

@Service
@Slf4j
public class OrderSender {

    private final RabbitTemplate rabbitTemplate;

    @Value("${mq.topic.order.exchange}")
    private String orderTopicExchange;

    @Value("${mq.fanout.order.exchange}")
    private String orderFanoutExchange;

    public OrderSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendOrderToRegions(OrderDto orderDto, String region, String operation){
        String key = "order." + region + "." + operation;
        rabbitTemplate.convertAndSend(orderTopicExchange, key, orderDto);
    }

    public void sendTextToAll(OrderDto orderDto){
        rabbitTemplate.convertAndSend(orderFanoutExchange, "", orderDto);
    }

}
