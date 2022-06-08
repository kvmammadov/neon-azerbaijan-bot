package com.kv.ms.bot.neonazerbaijan.service;

import com.kv.ms.bot.neonazerbaijan.client.InstagramClient;
import com.kv.ms.bot.neonazerbaijan.client.TelegramClient;
import com.kv.ms.bot.neonazerbaijan.client.response.Data;
import com.kv.ms.bot.neonazerbaijan.config.ApplicationProperty;
import com.kv.ms.bot.neonazerbaijan.dao.repository.PostsRepository;
import com.kv.ms.bot.neonazerbaijan.exception.AllPostsIsPublishedException;
import com.kv.ms.bot.neonazerbaijan.model.mapper.PostMapper;
import com.kv.ms.bot.neonazerbaijan.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import static com.kv.ms.bot.neonazerbaijan.util.DateUtil.getCurrentDate;
import static com.kv.ms.bot.neonazerbaijan.util.DateUtil.isMoreThanNHours;

@Slf4j
@Service
@RequiredArgsConstructor
public class NeonService {

    private final TelegramClient telegramClient;
    private final InstagramClient instagramClient;
    private final PostsRepository postsRepository;
    private final ApplicationProperty applicationProperty;
    private Integer counter = 0;

    //check every 1 hour
    @Scheduled(cron = "@hourly")
    @SneakyThrows
    public void publishPost() {

        var postDetails = instagramClient.getLatestPostId().getData().get(0);
        var postId = postDetails.getId();

        checkTheLastPostIdExistInDb(postDetails);

        if (isMoreThanNHours(3L)) { //TODO may me refactor code
            DateUtil.lastPublishingDate = getCurrentDate();
            if (!postsRepository.existsByPostId(postId)) {
                logger.info("Post cannot be find in DB");
                telegramClient.sendMedia(postDetails.getMediaUrl(), postDetails.getCaption());
                postsRepository.save(PostMapper.INSTANCE.toEntity(
                        postDetails,
                        true,
                        applicationProperty.getProfileId()));
                return;
            }

            logger.info("Post with id: {} exists in DB", postId);

            var archivedPost = postsRepository.findLastPostIdWherePublishedIsFalse()
                    .orElseThrow(AllPostsIsPublishedException::new);

            telegramClient.sendMedia(archivedPost.getMediaUrl(), archivedPost.getCaption());
            postsRepository.save(archivedPost.setIsPublished(true));
        }
        logger.info("Last post publish time is {}", DateUtil.lastPublishingDate);
    }

    private void checkTheLastPostIdExistInDb(Data postDetails) {
        if (!postsRepository.existsByPostId(postDetails.getId())) {
            postsRepository.save(PostMapper.INSTANCE.toEntity(
                    postDetails,
                    false,
                    applicationProperty.getProfileId()));
            logger.info("Detected and saved new post with id {}", postDetails.getId());
        }
    }

    public void fillTable(String next) {

        if (next == null) {
            System.out.println(counter);
            return;
        }

        var response = instagramClient.getNextPostId(next);
        response.getData().forEach(
                data -> {
                    postsRepository.save(PostMapper.INSTANCE.toEntity(data, false, applicationProperty.getProfileId()));
                    try {
                        counter++;
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