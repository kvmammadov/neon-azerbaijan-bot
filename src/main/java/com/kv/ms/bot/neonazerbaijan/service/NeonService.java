package com.kv.ms.bot.neonazerbaijan.service;

import com.kv.ms.bot.neonazerbaijan.client.InstagramClient;
import com.kv.ms.bot.neonazerbaijan.client.TelegramClient;
import com.kv.ms.bot.neonazerbaijan.client.response.Data;
import com.kv.ms.bot.neonazerbaijan.config.ApplicationProperty;
import com.kv.ms.bot.neonazerbaijan.dao.entity.PostEntity;
import com.kv.ms.bot.neonazerbaijan.dao.repository.PostsRepository;
import com.kv.ms.bot.neonazerbaijan.exception.AllPostsArePublishedException;
import com.kv.ms.bot.neonazerbaijan.model.mapper.PostMapper;
import com.kv.ms.bot.neonazerbaijan.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import javax.annotation.PostConstruct;

import static com.kv.ms.bot.neonazerbaijan.model.consts.MediaTypes.VIDEO;
import static com.kv.ms.bot.neonazerbaijan.model.consts.PublishingStatus.ERROR;
import static com.kv.ms.bot.neonazerbaijan.model.consts.PublishingStatus.OK;
import static com.kv.ms.bot.neonazerbaijan.util.DateUtil.isMoreThanNHours;

@Slf4j
@Service
@RequiredArgsConstructor
public class NeonService {

    private final TelegramClient telegramClient;
    private final InstagramClient instagramClient;
    private final PostsRepository postsRepository;
    private final ApplicationProperty applicationProperty;
    private Integer postsCount = 0;

    @PostConstruct
    private void consoleLastPostingDate() {
        logger.info("Default post publishing time is {}", DateUtil.lastPublishingDate);
    }

    @Scheduled(cron = "0 0/10 * * * *")
    private void checkTheLastPostIdExistInDb() {

        logger.info("--------------------------------------");
        logger.info("Checking for new posts");
        var postDetails = instagramClient.getLatestPostId().getData().get(0);

        if (!postsRepository.existsByPostId(postDetails.getId())) {
            postsRepository.save(PostMapper.INSTANCE.toEntity(
                    postDetails,
                    false,
                    applicationProperty.getProfileId()));
            logger.info("Detected and saved new post with id {}", postDetails.getId());
        }
        logger.info("--------------------------------------");
    }


    //    @Scheduled(cron = "0 * * * * *")
    @SneakyThrows
    @Scheduled(cron = "@hourly")
    public void publishPost() {

        if (isMoreThanNHours(3L)) {

            var archivedPost = postsRepository.findLastPostIdWherePublishedIsFalse()
                    .orElseThrow(AllPostsArePublishedException::new);

            try {
                sendMediaToTelegram(instagramClient.getPostIdDetails(archivedPost.getPostId()));
                insertDb(archivedPost, true, OK);
            } catch (HttpClientErrorException exception) {
                logger.info("Exception when trying sending request: {}", exception.getMessage());
                insertDb(archivedPost, false, ERROR + ": " + exception.getMessage());
            }

            logger.info("Last post publish time is {}", DateUtil.lastPublishingDate);
        }
    }

    private void insertDb(PostEntity archivedPost, Boolean isPublished, String publishingStatus) {
        archivedPost.setIsPublished(isPublished);
        archivedPost.setPublishingStatus(publishingStatus);
        postsRepository.save(archivedPost);
    }

    private void sendMediaToTelegram(Data archivedPostDetails) {
        logger.info("Sending post with id {}", archivedPostDetails.getId());
        if (archivedPostDetails.getMediaType().equalsIgnoreCase(VIDEO)) {
            telegramClient.sendVideo(archivedPostDetails.getMediaUrl(), archivedPostDetails.getCaption());
        } else
            telegramClient.sendPhoto(archivedPostDetails.getMediaUrl(), archivedPostDetails.getCaption());
    }

    public void fillTable(String next) {

        if (next == null) {
            System.out.println(postsCount);
            return;
        }

        var response = instagramClient.getNextPostId(next);
        response.getData().forEach(
                data -> {
                    postsRepository.save(PostMapper.INSTANCE.toEntity(data, false, applicationProperty.getProfileId()));
                    try {
                        postsCount++;
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        next = response.getPaging().getNext();
        System.out.println(next);
        fillTable(next);
    }
}