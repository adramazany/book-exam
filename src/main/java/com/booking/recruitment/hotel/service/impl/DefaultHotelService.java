package com.booking.recruitment.hotel.service.impl;

import com.booking.recruitment.hotel.exception.BadRequestException;
import com.booking.recruitment.hotel.exception.ElementNotFoundException;
import com.booking.recruitment.hotel.model.Hotel;
import com.booking.recruitment.hotel.repository.HotelRepository;
import com.booking.recruitment.hotel.service.HotelService;
import com.booking.recruitment.hotel.util.HaversineUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
class DefaultHotelService implements HotelService {
  private final HotelRepository hotelRepository;

  @Autowired
  DefaultHotelService(HotelRepository hotelRepository) {
    this.hotelRepository = hotelRepository;
  }

  @Override
  public List<Hotel> getAllHotels() {
    return hotelRepository.findAll();
  }

  @Override
  public List<Hotel> getHotelsByCity(Long cityId) {
    return hotelRepository.findAll().stream()
        .filter((hotel) -> cityId.equals(hotel.getCity().getId()))
        .collect(Collectors.toList());
  }

  @Override
  public Hotel createNewHotel(Hotel hotel) {
    if (hotel.getId() != null) {
      throw new BadRequestException("The ID must not be provided when creating a new Hotel");
    }

    return hotelRepository.save(hotel);
  }

  @Override
  public Hotel getHotelById(Long id) {
    return hotelRepository.findById(id)
            .orElseThrow(()->new ElementNotFoundException("Hotel not found by ID "+id));
  }

  @Override
  public String deleteHotel(Long id) {
    return hotelRepository.findById(id)
            .map(hotel -> {
              hotel.setDeleted(true);
              hotelRepository.save(hotel);
              return "Deleted";
            })
            .orElseThrow(()->new ElementNotFoundException("Hotel not found for deletion by ID "+id));
  }

  @Override
  public List<Hotel> searchHotelByCityTop3Distance(Long cityId) {
    List<Hotel> result = hotelRepository.findAll().stream()
            .filter((hotel) -> cityId.equals(hotel.getCity().getId()))
            .sorted( Comparator.comparingDouble( (hotel)-> {
              Double lat1=hotel.getLatitude();
              Double lon1=hotel.getLongitude();
              Double lat2=hotel.getCity().getCityCentreLatitude();
              Double lon2=hotel.getCity().getCityCentreLongitude();
              return HaversineUtil.distance(lat1,lon1,lat2,lon2);
            }  )  )
            .collect(Collectors.toList());
    Collections.reverse(result);
    int limit=3;
    if(result.size()<3)limit=result.size();
    return result.subList(0,limit);
  }
}
