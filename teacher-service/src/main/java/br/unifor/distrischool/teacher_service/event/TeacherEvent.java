package br.unifor.distrischool.teacher_service.event;

import java.time.LocalDateTime;

public class TeacherEvent {

    private Long teacherId;
    private String nome;
    private String qualificacao;
    private String contato;
    private String eventType; // CREATED, UPDATED, DELETED
    private LocalDateTime timestamp;

    public TeacherEvent() {
        this.timestamp = LocalDateTime.now();
    }

    public TeacherEvent(Long teacherId, String nome, String qualificacao, String contato, String eventType) {
        this.teacherId = teacherId;
        this.nome = nome;
        this.qualificacao = qualificacao;
        this.contato = contato;
        this.eventType = eventType;
        this.timestamp = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getQualificacao() {
        return qualificacao;
    }

    public void setQualificacao(String qualificacao) {
        this.qualificacao = qualificacao;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
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
        return "TeacherEvent{" +
                "teacherId=" + teacherId +
                ", nome='" + nome + '\'' +
                ", qualificacao='" + qualificacao + '\'' +
                ", contato='" + contato + '\'' +
                ", eventType='" + eventType + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
