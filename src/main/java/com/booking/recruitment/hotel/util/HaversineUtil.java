package com.booking.recruitment.hotel.util;
/*
 * @created 1/29/2022 - 8:13 PM
 * @project b7c77491-4cdb-4966-bf78-570271a30d2b
 * @author adel.ramezani (adramazany@gmail.com)
 */

public class HaversineUtil {
    final static int R = 6371; // Radious of the earth
    public static Double distance(Double lat1,Double lon1,Double lat2,Double lon2){
        Double latDistance = toRad(lat2-lat1);
        Double lonDistance = toRad(lon2-lon1);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(toRad(lat1)) * Math.cos(toRad(lat2)) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        Double distance = R * c;
        return distance;
    }

    private static Double toRad(Double value) {
        return value * Math.PI / 180;
    }

}
