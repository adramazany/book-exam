package com.booking.recruitment.hotel.service;

import com.booking.recruitment.hotel.model.Hotel;

import java.util.List;

public interface HotelService {
  List<Hotel> getAllHotels();

  List<Hotel> getHotelsByCity(Long cityId);

  Hotel createNewHotel(Hotel hotel);

  Hotel getHotelById(Long id);

  String deleteHotel(Long id);

  List<Hotel> searchHotelByCityTop3Distance(Long cityId);

}
