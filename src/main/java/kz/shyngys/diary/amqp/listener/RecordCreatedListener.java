package kz.shyngys.diary.amqp.listener;

import kz.shyngys.diary.amqp.config.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RecordCreatedListener {

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME)
    public void handleRecordCreated(String message) {
        log.info("📩 Получено сообщение из очереди 'records': {}", message);

        // Здесь можно вызвать сервис, чтобы обработать сообщение
        // Например, сохранить в БД или обновить кэш
    }
}
