package com.festapp.FestApplication.controller;


import org.springframework.web.bind.annotation.*;

import com.festapp.FestApplication.models.Bio;
import com.festapp.FestApplication.service.BioService;

import java.util.Optional;

@RestController
@RequestMapping("/api/bio")
public class BioController {
    private final BioService bioService;

    public BioController(BioService bioService) {
        this.bioService = bioService;
    }

    @PutMapping("/{userId}")
    public Bio updateBio(@PathVariable Long userId, @RequestBody Bio bio) {
        return bioService.updateBio(userId, bio);
    }

    @GetMapping("/{userId}")
    public Optional<Bio> getBio(@PathVariable Long userId) {
        return bioService.getBioByUserId(userId);
    }
}
