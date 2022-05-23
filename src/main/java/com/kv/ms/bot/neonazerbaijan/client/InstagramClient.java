package com.kv.ms.bot.neonazerbaijan.client;

import com.kv.ms.bot.neonazerbaijan.client.response.PostIdsResponse;
import com.kv.ms.bot.neonazerbaijan.config.ApplicationProperty;
import com.kv.ms.bot.neonazerbaijan.config.UrlConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InstagramClient {

    private final RestClient restClient;
    private final UrlConfig urlConfig;
    private final ApplicationProperty applicationProperty;

    public PostIdsResponse getPostIds() {
         return restClient.getForObject(
                String.format(
                        urlConfig.getPostIds(),
                        applicationProperty.getProfileId(),
                        applicationProperty.getAccessToken()
                ),
                PostIdsResponse.class
        );
    }
}
