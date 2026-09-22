package com.example.Lost_And_Found.service;

import java.util.Comparator;
import java.util.List;

import com.example.Lost_And_Found.dto.DescriptionMatchingResultDTO;
import com.example.Lost_And_Found.entity.ItemClass;
import com.example.Lost_And_Found.repository.ItemClassRepo;
import org.springframework.stereotype.Service;

@Service
public class DescriptionBasedResultService {

    private final ItemClassRepo itemClassRepo;
    private final DescriptionBasedMatchingService descriptionBasedMatchingService;

    public DescriptionBasedResultService(ItemClassRepo itemClassRepo, DescriptionBasedMatchingService descriptionBasedMatchingService) {
        this.itemClassRepo = itemClassRepo;
        this.descriptionBasedMatchingService = descriptionBasedMatchingService;
    }

    public List<DescriptionMatchingResultDTO> matchResult(float[] embedding,double threshold){
        List<ItemClass> itemList = itemClassRepo.findAll();

        return itemList
                .stream()
                .filter(item -> item.getDescriptionEmbedding()!=null)
                .map(item->{
                    double similarity =
                            descriptionBasedMatchingService
                                    .getMatchPercentage(embedding,
                                            item.getDescriptionEmbedding());


                    return new DescriptionMatchingResultDTO(item,similarity);
                })
                .filter(result ->
                        result.getPercentage() >= threshold)
                .sorted(
                        Comparator.comparing(
                                DescriptionMatchingResultDTO::getPercentage
                        ).reversed()
                )
                .toList();
    }
}
