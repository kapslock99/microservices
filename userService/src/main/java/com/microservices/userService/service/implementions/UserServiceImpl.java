package com.microservices.userService.service.implementions;

import com.microservices.userService.entities.Hotel;
import com.microservices.userService.entities.Rating;
import com.microservices.userService.entities.User;
import com.microservices.userService.exceptions.ResourceNotFoundException;
import com.microservices.userService.external.services.HotelService;
import com.microservices.userService.repositaries.UserRepositary;
import com.microservices.userService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepositary userRepositary;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;


    @Override
    public User saveUser(User user) {
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepositary.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepositary.findAll();
    }

    @Override
    public User getUser(String userId) {
        User user =  userRepositary.findById(userId).orElseThrow( () -> new ResourceNotFoundException("User with given id is not found on server : " + userId));

        Rating[] ratings = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+userId , Rating[].class);

        for (Rating rating : ratings){
            String hotelId = rating.getHotelId();
            //Hotel hotel = restTemplate.getForObject("http://HOTEL-SERVICE/hotels/"+hotelId , Hotel.class);
            Hotel hotel = hotelService.getHotelById(hotelId);
            System.out.println("Hotel Name : " + hotel.getName());
            rating.setHotel(hotel);
        }

        user.setRatings(Arrays.asList(ratings));
        return user;
    }
}
