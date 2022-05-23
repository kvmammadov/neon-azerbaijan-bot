package com.kv.ms.bot.neonazerbaijan.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class ApplicationProperty {

    @Value("${instagram.profile-id}")
    private String profileId;
    @Value("${instagram.fields}")
    private String fields;
    @Value("${instagram.access-token}")
    private String accessToken;
}
