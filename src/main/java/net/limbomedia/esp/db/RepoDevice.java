package net.limbomedia.esp.db;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface RepoDevice extends JpaRepository<DeviceEntity, Long> {

	Optional<DeviceEntity> findOneByUuid(String identifier);

	List<DeviceEntity> findByApp(AppEntity app);

}
