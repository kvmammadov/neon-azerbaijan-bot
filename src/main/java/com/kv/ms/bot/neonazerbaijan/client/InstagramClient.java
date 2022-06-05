package com.kv.ms.bot.neonazerbaijan.client;

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

        var response = restClient.getForObject(
                String.format(
                        urlConfig.getLatestPostDetails(),
                        applicationProperty.getProfileId(),
                        applicationProperty.getFields(),
                        applicationProperty.getAccessToken()
                ),
                PostIdResponse.class
        );
        logger.info("RESPONSE from instagram-api: {}", response);
        return response;
    }

    public PostIdResponse getNextPostId() {

        var response = restClient.getForObject(
                String.format(
                        urlConfig.getAllPostsDetails(),
                        applicationProperty.getProfileId(),
                        applicationProperty.getFields(),
                        applicationProperty.getAccessToken()
                ),
                PostIdResponse.class
        );
        logger.info("RESPONSE from instagram-api: {}", response);
        return response;
    }
}
