package com.kv.ms.bot.neonazerbaijan.client;

import com.kv.ms.bot.neonazerbaijan.client.response.Data;
import com.kv.ms.bot.neonazerbaijan.client.response.PostIdResponse;
import com.kv.ms.bot.neonazerbaijan.config.ApplicationProperty;
import com.kv.ms.bot.neonazerbaijan.config.UrlConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class InstagramClient {

    private final RestClient restClient;
    private final UrlConfig urlConfig;
    private final ApplicationProperty applicationProperty;

    public PostIdResponse getLatestPostId() {
        return restClient.getForObject(
                String.format(
                        urlConfig.getLatestPostDetails(),
                        applicationProperty.getProfileId(),
                        applicationProperty.getFields(),
                        applicationProperty.getAccessToken()
                ),
                PostIdResponse.class
        );
        //logger.info("RESPONSE from instagram-api: {}", response);
    }

    public Data getPostIdDetails(String postId) {
        var response = restClient.get(
                String.format(
                        urlConfig.getActualPostIdDetails(),
                        postId,
                        applicationProperty.getFields(),
                        applicationProperty.getAccessToken()),
                Data.class
        ).getBody();

        logger.info("RESPONSE from telegram-api: {}", response);
        return response;
    }

    public PostIdResponse getNextPostId(String nextValue) {
        return restClient.getForObject(
                nextValue,
                PostIdResponse.class
        );
        //logger.info("RESPONSE from instagram-api: {}", response);
    }
}
