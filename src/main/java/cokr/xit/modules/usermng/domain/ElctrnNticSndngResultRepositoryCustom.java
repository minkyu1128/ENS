package cokr.xit.modules.usermng.domain;

import java.util.List;

public interface ElctrnNticSndngResultRepositoryCustom {


	public List<ElctrnNticSndngResult> findAllByElctrnNticSndngId(String elctrnNticSndngId);


	public Long saveFetchStatus(ElctrnNticSndng sndng);
}
