package com.kv.ms.bot.neonazerbaijan.client;

import com.kv.ms.bot.neonazerbaijan.client.request.TelegramSendPhotoRequest;
import com.kv.ms.bot.neonazerbaijan.client.request.TelegramSendVideoRequest;
import com.kv.ms.bot.neonazerbaijan.config.ApplicationProperty;
import com.kv.ms.bot.neonazerbaijan.config.UrlConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramClient {

    private final RestClient restClient;
    private final UrlConfig urlConfig;
    private final ApplicationProperty applicationProperty;

    public void sendPhoto(String photoUrl, String caption) {

        var response = restClient.post(
                String.format(
                        urlConfig.getSendPhoto(),
                        applicationProperty.getTelegramToken()
                ),
                TelegramSendPhotoRequest.builder()
                        .chatId(applicationProperty.getChatId())
                        .photo(photoUrl)
                        .caption(caption)
                        .build(),
                Void.class
        );
        logger.info("RESPONSE from telegram-api: {}", response);
    }

    public void sendVideo(String videoUrl, String caption) {

        var response = restClient.post(
                String.format(
                        urlConfig.getSendVideo(),
                        applicationProperty.getTelegramToken()
                ),
                TelegramSendVideoRequest.builder()
                        .chatId(applicationProperty.getChatId())
                        .video(videoUrl)
                        .caption(caption)
                        .build(),
                Void.class
        );
        logger.info("RESPONSE from telegram-api: {}", response);
    }
}
