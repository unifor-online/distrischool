package br.unifor.distrischool.teacher_service.kafka;

import br.unifor.distrischool.teacher_service.event.TeacherEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class TeacherEventProducer {

    private static final Logger logger = LoggerFactory.getLogger(TeacherEventProducer.class);
    private static final String TOPIC = "teacher-events";

    private final KafkaTemplate<String, TeacherEvent> kafkaTemplate;

    public TeacherEventProducer(KafkaTemplate<String, TeacherEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishTeacherEvent(TeacherEvent event) {
        logger.info("Publishing teacher event: {}", event);
        
        CompletableFuture<SendResult<String, TeacherEvent>> future = 
            kafkaTemplate.send(TOPIC, event.getTeacherId().toString(), event);
        
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                logger.info("Teacher event published successfully: topic={}, partition={}, offset={}",
                        result.getRecordMetadata().topic(),
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset());
            } else {
                logger.error("Failed to publish teacher event", ex);
            }
        });
    }
}
