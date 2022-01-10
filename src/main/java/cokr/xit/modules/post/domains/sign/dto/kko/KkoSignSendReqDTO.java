package cokr.xit.modules.post.domains.sign.dto.kko;

import cokr.xit.modules.post.domains.sign.dto.kko.item.KkoSignSendReqData;
import lombok.Data;

/**
 * 카카오 인증톡 전송요청 DTO
 * @author 박민규
 */
@Data
public class KkoSignSendReqDTO {

	private KkoSignSendReqData data;
}
