package br.com.bomrastreio.api.setting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SettingRepository extends JpaRepository<Setting, String>, JpaSpecificationExecutor<Setting> {

  Optional<Setting> findByKey(SettingKey key);

}
