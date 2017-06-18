package br.com.bomrastreio.api.setting;

import br.com.bomrastreio.api.exception.BadRequestException;
import br.com.bomrastreio.api.support.validation.UpdateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class SettingService {

  private final SettingRepository settingRepository;

  @Autowired
  public SettingService(SettingRepository settingRepository) {
    this.settingRepository = settingRepository;
  }

  @Transactional
  public Setting update(String key, @Validated(UpdateGroup.class) SettingResource resource) {
    SettingKey settingKey = convertKeyFrom(key);
    Setting setting = settingRepository.findByKey(settingKey)
      .orElse(Setting.builder()
        .withRandomId()
        .withKey(settingKey)
        .build());

    setting.updateValue(resource.getValue());
    return settingRepository.save(setting);
  }

  @Transactional(readOnly = true)
  public List<Setting> findAll() {
    return settingRepository.findAll();
  }

  @Transactional
  public boolean isActive(SettingKey key) {
    Optional<Setting> optionalSetting = settingRepository.findByKey(key);
    return optionalSetting.isPresent() && optionalSetting.get().asBoolean();
  }

  /**
   * Try to convert a string key into a {@link SettingKey}.
   *
   * @param key String key name
   * @return Key if match any existent setting key
   * @throws BadRequestException if the key don't exist
   */
  private SettingKey convertKeyFrom(String key) {
    try {
      return SettingKey.valueOf(key);
    } catch (IllegalArgumentException ex) {
      throw new BadRequestException("setting.not_found", key);
    }
  }

}
