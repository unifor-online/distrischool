package br.unifor.distrischool.student_service.kafka;

import br.unifor.distrischool.student_service.event.StudentEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class StudentEventProducer {

    private static final Logger logger = LoggerFactory.getLogger(StudentEventProducer.class);
    private static final String TOPIC = "student-events";

    private final KafkaTemplate<String, StudentEvent> kafkaTemplate;

    public StudentEventProducer(KafkaTemplate<String, StudentEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishStudentEvent(StudentEvent event) {
        logger.info("Publishing student event: {}", event);
        
        CompletableFuture<SendResult<String, StudentEvent>> future = 
            kafkaTemplate.send(TOPIC, event.getStudentId().toString(), event);
        
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                logger.info("Student event published successfully: topic={}, partition={}, offset={}",
                        result.getRecordMetadata().topic(),
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset());
            } else {
                logger.error("Failed to publish student event", ex);
            }
        });
    }
}
