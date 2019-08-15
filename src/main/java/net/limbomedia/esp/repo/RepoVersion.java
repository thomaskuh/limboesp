package net.limbomedia.esp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import net.limbomedia.esp.entity.VersionEntity;

@Repository
@Transactional(propagation=Propagation.MANDATORY)
public interface RepoVersion extends JpaRepository<VersionEntity, Long> {
  

}
