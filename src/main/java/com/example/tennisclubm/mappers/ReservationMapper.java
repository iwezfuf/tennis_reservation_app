package com.example.tennisclubm.mappers;

import com.example.tennisclubm.dto.ReservationDTOview;
import com.example.tennisclubm.dto.ReservationDTOcreate;
import com.example.tennisclubm.model.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CourtMapper.class, UserMapper.class})
public interface ReservationMapper {

    @Mapping(source = "court", target = "court")
    @Mapping(source = "user", target = "user")
    ReservationDTOview toDTO(Reservation reservation);

    @Mapping(source = "court", target = "court")
    @Mapping(source = "user", target = "user")
    Reservation toEntity(ReservationDTOview reservationDTOview);

    @Mapping(source = "courtId", target = "court.id")
    Reservation toEntity(ReservationDTOcreate reservationDTOcreate);
}
