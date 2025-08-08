package kz.shyngys.diary.service;

public interface RabbitService {
    void sendRecordCreatedEvent(Long recordId, String exchangeName, String routingKey);
}