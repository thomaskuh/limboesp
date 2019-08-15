package net.limbomedia.esp.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import net.limbomedia.esp.entity.AppEntity;

@Repository
@Transactional(propagation=Propagation.MANDATORY)
public interface RepoApp extends JpaRepository<AppEntity, Long> {
  
  Optional<AppEntity> findOneByName(String name);
  
}
