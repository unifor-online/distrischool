package br.unifor.distrischool.student_service.kafka;

import br.unifor.distrischool.student_service.event.StudentEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class StudentEventConsumer {

    private static final Logger logger = LoggerFactory.getLogger(StudentEventConsumer.class);

    @KafkaListener(topics = "student-events", groupId = "student-service")
    public void consumeStudentEvent(StudentEvent event) {
        logger.info("Received student event: {}", event);
        // Process the event (e.g., update local cache, trigger workflows)
    }
}
