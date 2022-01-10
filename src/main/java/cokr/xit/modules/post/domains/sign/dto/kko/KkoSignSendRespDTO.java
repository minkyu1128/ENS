package cokr.xit.modules.post.domains.sign.dto.kko;

import cokr.xit.modules.post.domains.sign.dto.kko.item.KkoSignSendRespData;
import lombok.Data;

/**
 * 카카오 인증톡 전송요청 DTO
 * @author 박민규
 */
@Data
public class KkoSignSendRespDTO {
	
	private KkoSignSendRespData data;
	
}
