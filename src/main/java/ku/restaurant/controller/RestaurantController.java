package ku.restaurant.controller;

import ku.restaurant.dto.RestaurantRequest;
import ku.restaurant.entity.Restaurant;
import ku.restaurant.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class RestaurantController {

    private RestaurantService service;

    @Autowired
    public RestaurantController(RestaurantService service) {
        this.service = service;
    }

    @GetMapping("/restaurants")
    public Page<Restaurant> getAllRestaurants(
            @RequestParam(value = "offset", required = false) Integer offset, // what page am I on?
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "sortBy", required = false) String sortBy) {
        if(null == offset) offset = 0;
        if(null == pageSize) pageSize = 10;
        if(StringUtils.isEmpty(sortBy)) sortBy ="name";


        return service.getRestaurantsPage(PageRequest.of(offset, pageSize, Sort.by(sortBy)));
    }


    @PostMapping("/restaurants")
    public Restaurant create(@RequestBody RestaurantRequest restaurant) {
        return service.create(restaurant);
    }

    @GetMapping("restaurants/{id}")
    public Restaurant getRestaurantById(@PathVariable UUID id) {
        return service.getRestaurantById(id);
    }

    @PutMapping("/restaurants")
    public Restaurant updateRestaurant(@RequestBody Restaurant restaurant) {
        return service.update(restaurant);
    }

    @DeleteMapping("/restaurants/{id}")
    public Restaurant delete(@PathVariable UUID id) {
        return service.delete(id);
    }

    @GetMapping("/restaurants/name/{name}")
    public Restaurant getRestaurantByName(@PathVariable String name) {
        return service.getRestaurantByName(name);
    }

    @GetMapping("/restaurants/location/{location}")
    public List<Restaurant> getRestaurantByLocation(@PathVariable String location) {
        return service.getRestaurantByLocation(location);
    }

}
