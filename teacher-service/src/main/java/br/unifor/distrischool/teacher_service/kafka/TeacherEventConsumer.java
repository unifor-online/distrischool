package br.unifor.distrischool.teacher_service.kafka;

import br.unifor.distrischool.teacher_service.event.TeacherEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TeacherEventConsumer {

    private static final Logger logger = LoggerFactory.getLogger(TeacherEventConsumer.class);

    @KafkaListener(topics = "teacher-events", groupId = "teacher-service")
    public void consumeTeacherEvent(TeacherEvent event) {
        logger.info("Received teacher event: {}", event);
        // Process the event (e.g., update local cache, trigger workflows)
    }
}
