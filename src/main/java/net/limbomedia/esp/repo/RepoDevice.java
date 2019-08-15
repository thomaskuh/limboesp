package net.limbomedia.esp.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import net.limbomedia.esp.api.Platform;
import net.limbomedia.esp.entity.DeviceEntity;

@Repository
@Transactional(propagation=Propagation.MANDATORY)
public interface RepoDevice extends JpaRepository<DeviceEntity, Long> {
  
  Optional<DeviceEntity> findOneByPlatformAndUuid(Platform platform, String identifier);

}
