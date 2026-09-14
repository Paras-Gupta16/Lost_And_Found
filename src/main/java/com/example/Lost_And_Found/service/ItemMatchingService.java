package com.example.Lost_And_Found.service;

import com.example.Lost_And_Found.dto.ImageMatchResult;
import com.example.Lost_And_Found.dto.ItemMatchResult;
import com.example.Lost_And_Found.entity.ItemClass;
import com.example.Lost_And_Found.entity.ItemClass_User;
import com.example.Lost_And_Found.repository.ItemClassRepo;
import com.example.Lost_And_Found.repository.ItemClass_UserRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ItemMatchingService {

    private final ItemClassRepo itemClassRepo;
    private final ItemClass_UserRepo itemClassUserRepo;
    private final ImageMatchingService imageMatchingService;

    public ItemMatchingService(
            ItemClassRepo itemClassRepo,
            ItemClass_UserRepo itemClassUserRepo,
            ImageMatchingService imageMatchingService) {

        this.itemClassRepo = itemClassRepo;
        this.itemClassUserRepo = itemClassUserRepo;
        this.imageMatchingService = imageMatchingService;
    }

    public List<ItemMatchResult> findMatches(String userItemId) {

        // 1. Get the user's lost-item report
        ItemClass_User userItem =
                itemClassUserRepo.findById(userItemId)
                                 .orElseThrow(() ->
                                         new RuntimeException(
                                                 "User item not found: " + userItemId
                                         ));

        // 2. Get all reported items
        List<ItemClass> reportedItems =
                itemClassRepo.findAll();

        List<ItemMatchResult> matches = new ArrayList<>();

        // 3. Get user's uploaded images
        List<byte[]> userImages =
                userItem.getUserImageList();

        if (userImages == null || userImages.isEmpty()) {
            return matches;
        }

        // 4. Compare user's images with every reported item
        for (ItemClass reportedItem : reportedItems) {


            List<byte[]> reportedImages =
                    reportedItem.getImage();

            if (reportedImages == null ||
                    reportedImages.isEmpty()) {
                continue;
            }

            double bestConfidence = 0.0;
            String bestReason = "";

            // 5. Compare every image combination
            for (byte[] userImage : userImages) {

                for (byte[] reportedImage : reportedImages) {

                    ImageMatchResult result =
                            imageMatchingService.compareImages(
                                    userImage,
                                    reportedImage
                            );

                    // 6. Keep the best image comparison
                    if (result.getConfidence() >
                            bestConfidence) {

                        bestConfidence =
                                result.getConfidence();

                        bestReason =
                                result.getReason();
                    }
                }
            }

            // 7. Only consider reasonably good matches
            if (bestConfidence >= 0.70) {

                matches.add(
                        new ItemMatchResult(
                                reportedItem,
                                bestConfidence,
                                bestReason
                        )
                );
            }
        }

        // 8. Highest confidence first
        matches.sort(
                Comparator.comparing(
                        ItemMatchResult::getConfidence
                ).reversed()
        );

        return matches;
    }
}
