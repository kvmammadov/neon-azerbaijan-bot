package com.kv.ms.bot.neonazerbaijan.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class UrlConfig {

    @Value("${url.instagram.get-post-ids}")
    private String postIds;

    @Value("${url.instagram.get-post-details}")
    private String postDetails;
}
