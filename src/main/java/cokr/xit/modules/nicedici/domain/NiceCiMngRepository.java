package cokr.xit.modules.nicedici.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NiceCiMngRepository extends JpaRepository<NiceCiMng, Long>, NiceCiMngRepositoryCustom {

}
