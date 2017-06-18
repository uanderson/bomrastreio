package br.com.bomrastreio.api.setting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/settings", produces = "application/json")
public class SettingApi {

  private final SettingService settingService;
  private final SettingMapper settingMapper;

  @Autowired
  public SettingApi(SettingService settingService, SettingMapper settingMapper) {
    this.settingService = settingService;
    this.settingMapper = settingMapper;
  }

  @PutMapping("/{key}")
  public ResponseEntity<SettingResource> put(
    @PathVariable String key, @RequestBody SettingResource resource
  ) {
    Setting savedSetting = settingService.update(key, resource);
    SettingResource savedResource = settingMapper.map(savedSetting);
    return ResponseEntity.ok(savedResource);
  }

  @GetMapping
  public ResponseEntity<List<SettingResource>> get() {
    List<Setting> settings = settingService.findAll();
    List<SettingResource> resources = settingMapper.map(settings);
    return ResponseEntity.ok(resources);
  }

}
