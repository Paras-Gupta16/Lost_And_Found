package com.example.Lost_And_Found.controller;

import com.example.Lost_And_Found.dto.ItemMatchResult;
import com.example.Lost_And_Found.service.ItemMatchingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/match")
public class MatchController {

    private final ItemMatchingService itemMatchingService;

    public MatchController(ItemMatchingService itemMatchingService) {
        this.itemMatchingService = itemMatchingService;
    }

    @GetMapping("/{userItemId}")
    public ResponseEntity<List<ItemMatchResult>> findMatches(
            @PathVariable String userItemId) {

        List<ItemMatchResult> matches =
                itemMatchingService.findMatches(userItemId);

        return ResponseEntity.ok(matches);
    }
}

