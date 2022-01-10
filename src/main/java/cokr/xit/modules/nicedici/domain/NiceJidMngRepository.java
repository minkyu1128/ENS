package cokr.xit.modules.nicedici.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NiceJidMngRepository extends JpaRepository<NiceJidMng, Long>, NiceJidMngRepositoryCustom {

}
