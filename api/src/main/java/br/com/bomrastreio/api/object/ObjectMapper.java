package br.com.bomrastreio.api.object;

import org.mapstruct.Mapper;

@Mapper
public interface ObjectMapper {

  ObjectResource map(TrackingObject object);

}
