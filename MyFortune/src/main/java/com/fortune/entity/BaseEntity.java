package com.fortune.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fortune.entity.code.converter.BooleanToYNConverter;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@SuperBuilder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BaseEntity {

    @Column(nullable = false, updatable = false, name = "ins_date")
    @CreatedDate
    private LocalDateTime insDate;

    @LastModifiedDate
    @Column(nullable = false, name = "upt_date")
    private LocalDateTime uptDate;

    @Column(name = "del_yn", nullable = false, length = 1)
    @Convert(converter = BooleanToYNConverter.class)
    @Builder.Default
    private Boolean delYn = false;
}

