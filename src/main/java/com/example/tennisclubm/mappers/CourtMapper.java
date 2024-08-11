package com.example.tennisclubm.mappers;

import com.example.tennisclubm.dto.CourtDTOview;
import com.example.tennisclubm.dto.CourtDTOcreate;
import com.example.tennisclubm.model.Court;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {SurfaceTypeMapper.class})
public interface CourtMapper {

    @Mapping(source = "surfaceType", target = "surfaceType")
    CourtDTOview toDTO(Court court);

    @Mapping(source = "surfaceType", target = "surfaceType")
    Court toEntity(CourtDTOview courtDTOview);

    @Mapping(source = "surfaceTypeId", target = "surfaceType.id")
    Court toEntity(CourtDTOcreate courtDTOcreate);
}
