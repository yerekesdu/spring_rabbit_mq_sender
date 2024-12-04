package kz.bitlab.rabbit.middle03rabbit.sender;


import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageSender {

    private final RabbitTemplate rabbitTemplate;

    public MessageSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(String message){
        rabbitTemplate.convertAndSend("my-message-exchange", "key555", message);
    }

    public void sendMessageDQLTest(String message){
        rabbitTemplate.convertAndSend("my-message-exchange-test", "12345", message);
    }

}
