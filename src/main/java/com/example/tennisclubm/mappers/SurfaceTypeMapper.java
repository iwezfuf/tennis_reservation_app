package com.example.tennisclubm.mappers;

import com.example.tennisclubm.dto.SurfaceTypeDTOview;
import com.example.tennisclubm.dto.SurfaceTypeDTOcreate;
import com.example.tennisclubm.model.SurfaceType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SurfaceTypeMapper {

    SurfaceTypeDTOview toDTO(SurfaceType surfaceType);

    SurfaceType toEntity(SurfaceTypeDTOview surfaceTypeDTOview);

    SurfaceType toEntity(SurfaceTypeDTOcreate surfaceTypeDTOcreate);
}
