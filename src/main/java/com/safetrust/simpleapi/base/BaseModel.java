package com.safetrust.simpleapi.base;

import com.safetrust.simpleapi.util.IdConverter;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

import static com.safetrust.simpleapi.util.IdConverter.fromId;

@SuppressWarnings("serial")
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public abstract class BaseModel extends Loggable implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "time_created", updatable = false)
    @CreatedDate
    @CreationTimestamp
    private LocalDateTime timeCreated;

    @Column(name = "time_updated")
    @UpdateTimestamp
    @LastModifiedDate
    private LocalDateTime timeUpdated;

}
