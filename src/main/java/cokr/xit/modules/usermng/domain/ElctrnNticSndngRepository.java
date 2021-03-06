package cokr.xit.modules.usermng.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ElctrnNticSndngRepository extends JpaRepository<ElctrnNticSndng, String>, ElctrnNticSndngRepositoryCustom {


	List<ElctrnNticSndng> findAllBySndngProcessSttus(String sndngProcessSttus);


	List<ElctrnNticSndng> findAllBySndngProcessSttusAndSndngDtBetween(String sndngProcessSttus, String beginSndngDt, String endSndngDt);
}
