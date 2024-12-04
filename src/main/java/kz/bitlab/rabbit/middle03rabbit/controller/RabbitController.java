package kz.bitlab.rabbit.middle03rabbit.controller;

import kz.bitlab.rabbit.middle03rabbit.dto.OrderDto;
import kz.bitlab.rabbit.middle03rabbit.sender.MessageSender;
import kz.bitlab.rabbit.middle03rabbit.sender.OrderSender;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/message")
public class RabbitController {

    private final MessageSender rabbitSender;
    private final OrderSender orderSender;

    public RabbitController(MessageSender rabbitSender, OrderSender orderSender) {
        this.rabbitSender = rabbitSender;
        this.orderSender = orderSender;
    }

    @PostMapping(value = "/send")
    public ResponseEntity<String> sendMessage(@RequestBody String message){
        try{
            rabbitSender.sendMessage(message);
            return new ResponseEntity<>("Message Sent", HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>("Some errors on sending message", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/send-dlq")
    public ResponseEntity<String> sendMessageToDQL(@RequestBody String message){
        try{
            rabbitSender.sendMessageDQLTest(message);
            return new ResponseEntity<>("Message Sent", HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>("Some errors on sending message", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/send/{region}/{operation}")
    public ResponseEntity<String> sendOrder(@RequestBody OrderDto orderDto, @PathVariable(name = "region") String region, @PathVariable(name = "operation") String operation){
        try{
            orderSender.sendOrderToRegions(orderDto, region, operation);
            return new ResponseEntity<>("Message Sent", HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>("Error on sending", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/send-fanout")
    public ResponseEntity<String> sendOrderFanout(@RequestBody OrderDto orderDto){
        try{
            orderSender.sendTextToAll(orderDto);
            return new ResponseEntity<>("Message Sent", HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>("Error on sending", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}