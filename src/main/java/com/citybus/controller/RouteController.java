package com.citybus.controller;

import com.citybus.model.Route;
import com.citybus.service.BusTrackingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RouteController {

    private final BusTrackingService busTrackingService;

    public RouteController(BusTrackingService busTrackingService) {
        this.busTrackingService = busTrackingService;
    }

    @GetMapping("/routes/{id}")
    public ResponseEntity<Route> getRoute(@PathVariable Long id) {
        Route route = busTrackingService.getRoute(id.toString());
        if (route != null) {
            return ResponseEntity.ok(route);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}