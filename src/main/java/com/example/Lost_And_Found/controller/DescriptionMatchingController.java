package com.example.Lost_And_Found.controller;

import java.util.ArrayList;
import java.util.List;

import com.example.Lost_And_Found.dto.DescriptionMatchingResultDTO;
import com.example.Lost_And_Found.service.DescriptionBasedMatchingService;
import com.example.Lost_And_Found.service.DescriptionBasedResultService;
import com.example.Lost_And_Found.repository.ItemClassRepo;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/matching")
public class DescriptionMatchingController {

    private final  DescriptionBasedMatchingService descriptionBasedMatchingService;
    private final ItemClassRepo itemClassRepo;
    private final DescriptionBasedResultService descriptionBasedResultService;

    public DescriptionMatchingController(DescriptionBasedMatchingService descriptionBasedMatchingService,
                                         ItemClassRepo itemClassRepo,
                                         DescriptionBasedResultService descriptionBasedResultService) {
        this.descriptionBasedMatchingService = descriptionBasedMatchingService;
        this.itemClassRepo = itemClassRepo;
        this.descriptionBasedResultService = descriptionBasedResultService;
    }

    @GetMapping("/find")
    public List<DescriptionMatchingResultDTO> findMatches(
            @RequestParam String description) {

        List<String> descriptions =
                itemClassRepo.findAll()
                             .stream()
                             .map(item -> item.getDescription())
                             .filter(d -> d != null && !d.isBlank())
                             .toList();

        descriptions =
                new ArrayList<>(descriptions);

        descriptions.add(description);

        float[] userEmbedding =
                descriptionBasedMatchingService.generateEmbedding(
                        description,
                        descriptions
                );

        return descriptionBasedResultService.matchResult(
                userEmbedding,
                70.0
        );
    }
}