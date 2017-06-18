package br.com.bomrastreio.api.setting;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface SettingMapper {

  SettingResource map(Setting setting);
  List<SettingResource> map(List<Setting> setting);

}
