package net.iyiguo.html5.serversentevents.domain;

import net.iyiguo.html5.serversentevents.enums.MessageTypeEnum;

import java.time.LocalDateTime;

public class Message {

    private Long id;

    private String context;

    private LocalDateTime createTime;

    /**
     * second
     */
    private Long ttl;

    private MessageTypeEnum type;

    public Message() {
    }

    public Message(Long id, String context, LocalDateTime createTime, Long ttl, MessageTypeEnum type) {
        this.id = id;
        this.context = context;
        this.createTime = createTime;
        this.ttl = ttl;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Long getTtl() {
        return ttl;
    }

    public void setTtl(Long ttl) {
        this.ttl = ttl;
    }

    public MessageTypeEnum getType() {
        return type;
    }

    public void setType(MessageTypeEnum type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", context='" + context + '\'' +
                ", createTime=" + createTime +
                ", ttl=" + ttl +
                ", type=" + type +
                '}';
    }
}
