package cokr.xit.modules.usermng.domain.embeded;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.PrePersist;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Embeddable
@Getter
@RequiredArgsConstructor
public class FieldCreate {

//	@CreationTimestamp
//	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime REGIST_DT    ; //등록 일시
	@Column(length = 50)
	private String REGISTER            ; //등록자


	@PrePersist
	public void preUpdate() {
		this.REGIST_DT = LocalDateTime.now();
	}
}
