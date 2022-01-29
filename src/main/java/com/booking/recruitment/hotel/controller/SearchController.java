package com.booking.recruitment.hotel.controller;
/*
 * @created 1/29/2022 - 8:15 PM
 * @project b7c77491-4cdb-4966-bf78-570271a30d2b
 * @author adel.ramezani (adramazany@gmail.com)
 */

import com.booking.recruitment.hotel.model.Hotel;
import com.booking.recruitment.hotel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {
    private final HotelService hotelService;

    @Autowired
    public SearchController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping("/{cityId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Hotel> searchHotelByCity(@PathVariable Long cityId, @RequestParam String sortBy){
        return hotelService.searchHotelByCityTop3Distance(cityId);
    }

}
