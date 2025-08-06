package com.fortune.entity;

import com.fortune.entity.code.UserGenderCode;
import com.fortune.entity.code.converter.BooleanToYNConverter;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * 회원 테이블 entity
 */
@Entity
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Users extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;

    @Column(name = "user_id", unique = true, nullable = false, length = 20)
    private String userId;

    @Column(name = "pwd", nullable = false)
    private String pwd;

	@Column(nullable = false)
	private String name;

	@Column(name = "birth_dt",nullable = false)
	private LocalDateTime birthDt;

	@Column(nullable = false)
	private UserGenderCode gender;

	@Column(nullable = false)
	private String phone;
	
	@Column(nullable = false)
	private String email;
	
	/** 로그인 실패 횟수 */
    @Column(name = "login_fail_cnt", nullable = false)
	@Builder.Default
    private int loginFailCnt = 0;
	
	/** 마지막 로그인 여부 */
	@Column(name = "last_login_dt")
	private LocalDateTime lastLoginDt;
	
	/** 계정 잠금 여부 */
    @Column(name = "lock_yn", nullable = false, length = 1)
    @Convert(converter = BooleanToYNConverter.class)
    @Builder.Default
    private Boolean lockYn = false;

	/** 양력 여부 */
	@Column(name = "solar_yn", nullable = false, length = 1)
	@Convert(converter = BooleanToYNConverter.class)
	private Boolean solarYn;

	/**
	 * 로그인 실패 횟수 증가
	 */
    public void incLoginFailCnt() {
        this.loginFailCnt++;
		if(this.loginFailCnt >= 5 && !this.lockYn) //5회 이상시 계정 잠금
			this.lockYn = true;
    }

	/**
	 * 로그인 실패 횟수 초기화
	 */
    public void resetLoginFailCnt() {
		this.loginFailCnt = 0;
		if(this.lockYn) //계정 잠금 해제
			this.lockYn = false;
    }
}
