package com.fortune.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fortune.entity.code.converter.BooleanToYNConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@SuperBuilder
@Getter
@Setter
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

