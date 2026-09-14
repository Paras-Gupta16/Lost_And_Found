package com.example.Lost_And_Found.service;

import com.example.Lost_And_Found.dto.ImageMatchResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.content.Media;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

@Service
public class ImageMatchingService {

    private final ChatClient chatClient;
    private final ObjectMapper objectMapper;

    public ImageMatchingService(
            ChatClient.Builder chatClientBuilder,
            ObjectMapper objectMapper) {

        this.chatClient = chatClientBuilder.build();
        this.objectMapper = objectMapper;
    }

    public ImageMatchResult compareImages(
            byte[] userImage,
            byte[] reportedImage) {

        try {

            Media userMedia = Media.builder()
                                   .mimeType(MimeTypeUtils.IMAGE_JPEG)
                                   .data(new ByteArrayResource(userImage))
                                   .build();

            Media reportedMedia = Media.builder()
                                       .mimeType(MimeTypeUtils.IMAGE_JPEG)
                                       .data(new ByteArrayResource(reportedImage))
                                       .build();

            String response = chatClient.prompt()
                                        .user(user -> user
                                                .text("""
                                Compare these two images.

                                Determine whether they show the SAME
                                physical item.

                                Carefully compare:
                                - shape
                                - color
                                - brand
                                - model
                                - scratches
                                - cracks
                                - stickers
                                - marks
                                - patterns
                                - damage
                                - unique characteristics

                                Return ONLY valid JSON:

                                {
                                  "match": true,
                                  "confidence": 0.95,
                                  "reason": "short explanation"
                                }

                                confidence must be between 0 and 1.
                                """)
                                                .media(userMedia, reportedMedia))
                                        .call()
                                        .content();

            return objectMapper.readValue(
                    response,
                    ImageMatchResult.class
            );

        } catch (Exception e) {

            return new ImageMatchResult(
                    false,
                    0.0,
                    "Unable to compare images"
            );
        }
    }
}