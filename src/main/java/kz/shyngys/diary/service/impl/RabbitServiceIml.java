package kz.shyngys.diary.service.impl;

import kz.shyngys.diary.service.RabbitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RabbitServiceIml implements RabbitService {

    private final AmqpTemplate amqpTemplate;

    public void sendRecordCreatedEvent(Long recordId, String exchangeName, String routingKey) {
        log.info("Отправляем событие о создании записи ID={}", recordId);
        amqpTemplate.convertAndSend(
                exchangeName,
                routingKey,
                recordId
        );
    }
}