package com.fortune.entity;

import com.fortune.code.converter.BooleanToYNConverter;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

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
    private String nickname;

    @Column(name = "login_fail_cnt", nullable = false)
    private int loginFailCnt = 0;

    @Column(name = "lock_yn", nullable = false, length = 1)
    @Convert(converter = BooleanToYNConverter.class)
    @Builder.Default
    private Boolean lockYn = false;

    public void incLoginFailCnt() {
        this.loginFailCnt++;
		if(this.loginFailCnt >= 5)
			this.lockYn = true;
    }

    public void resetLoginFailCnt() {
		this.loginFailCnt = 0;
		if(this.lockYn)
			this.lockYn = false;
    }
}
