package br.unifor.distrischool.teacher_service.service;

import br.unifor.distrischool.teacher_service.event.TeacherEvent;
import br.unifor.distrischool.teacher_service.exception.ResourceNotFoundException;
import br.unifor.distrischool.teacher_service.kafka.TeacherEventProducer;
import br.unifor.distrischool.teacher_service.model.Teacher;
import br.unifor.distrischool.teacher_service.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {

    private final TeacherRepository repository;
    private final TeacherEventProducer eventProducer;

    public TeacherService(TeacherRepository repository, TeacherEventProducer eventProducer) {
        this.repository = repository;
        this.eventProducer = eventProducer;
    }

    public Teacher create(Teacher t) {
        Teacher saved = repository.save(t);
        // Publish event
        TeacherEvent event = new TeacherEvent(
            saved.getId(), 
            saved.getNome(), 
            saved.getQualificacao(), 
            saved.getContato(), 
            "CREATED"
        );
        eventProducer.publishTeacherEvent(event);
        return saved;
    }

    public Teacher update(Long id, Teacher t) {
        Teacher exist = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));
        exist.setNome(t.getNome());
        exist.setQualificacao(t.getQualificacao());
        exist.setContato(t.getContato());
        Teacher updated = repository.save(exist);
        // Publish event
        TeacherEvent event = new TeacherEvent(
            updated.getId(), 
            updated.getNome(), 
            updated.getQualificacao(), 
            updated.getContato(), 
            "UPDATED"
        );
        eventProducer.publishTeacherEvent(event);
        return updated;
    }

    public Teacher getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));
    }

    public List<Teacher> listAll() {
        return repository.findAll();
    }

    public void delete(Long id) {
        Teacher teacher = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));
        repository.deleteById(id);
        // Publish event
        TeacherEvent event = new TeacherEvent(
            teacher.getId(), 
            teacher.getNome(), 
            teacher.getQualificacao(), 
            teacher.getContato(), 
            "DELETED"
        );
        eventProducer.publishTeacherEvent(event);
    }
}
