package cokr.xit.modules.usermng.domain.embeded;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.PreUpdate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Embeddable
@Getter
@RequiredArgsConstructor
public class FieldUpdate {

//	@UpdateTimestamp
//	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime UPDT_DT             ; //수정 일시
	@Column(length = 50)
	private String UPDUSR            ; //수정자

	@PreUpdate
	public void preUpdate() {
		this.UPDT_DT = LocalDateTime.now();
	}
}
