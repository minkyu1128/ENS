package cokr.xit.config;

import java.util.LinkedHashMap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cokr.xit.code.CodeMapperFactory;
import cokr.xit.code.impl.Sample;
import cokr.xit.modules.common.code.InternalErrCd;
import cokr.xit.modules.post.code.KkoCardCd;
import cokr.xit.modules.post.code.KkoRespCd;
import cokr.xit.modules.post.code.NvRespCd;
import cokr.xit.modules.post.code.PaymentStatCd;

@Configuration
public class CodeMapperConfig {

	@Bean
	public CodeMapperFactory codeMapperFactory() {
		//CodeMapperFactory 초기화
		CodeMapperFactory codeMapperFactory = new CodeMapperFactory(new LinkedHashMap<>());
		
		//Enum 추가
		codeMapperFactory.put(Sample.class);
		codeMapperFactory.put(InternalErrCd.class);
		codeMapperFactory.put(KkoCardCd.class);
		codeMapperFactory.put(KkoRespCd.class);
		codeMapperFactory.put(NvRespCd.class);
		codeMapperFactory.put(PaymentStatCd.class);
		
		return codeMapperFactory;
	}
}
