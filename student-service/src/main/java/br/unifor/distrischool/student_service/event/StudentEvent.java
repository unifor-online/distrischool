package br.unifor.distrischool.student_service.event;

import java.time.LocalDateTime;

public class StudentEvent {

    private Long studentId;
    private String nome;
    private String matricula;
    private String eventType; // CREATED, UPDATED, DELETED
    private LocalDateTime timestamp;

    public StudentEvent() {
        this.timestamp = LocalDateTime.now();
    }

    public StudentEvent(Long studentId, String nome, String matricula, String eventType) {
        this.studentId = studentId;
        this.nome = nome;
        this.matricula = matricula;
        this.eventType = eventType;
        this.timestamp = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "StudentEvent{" +
                "studentId=" + studentId +
                ", nome='" + nome + '\'' +
                ", matricula='" + matricula + '\'' +
                ", eventType='" + eventType + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
