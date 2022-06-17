package com.kv.ms.bot.neonazerbaijan.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class UrlConfig {

    @Value("${url.instagram.get-latest-post-details}")
    private String latestPostDetails;

    @Value("${url.instagram.get-all-posts-details}")
    private String allPostsDetails;


    @Value("${url.telegram.send-photo}")
    private String sendPhoto;

    @Value("${url.telegram.send-video}")
    private String sendVideo;

    @Value("${url.instagram.get-post-id-details}")
    private String actualPostIdDetails;

//    @Value("${url.instagram.get-post-details}")
//    private String postDetails;
}
