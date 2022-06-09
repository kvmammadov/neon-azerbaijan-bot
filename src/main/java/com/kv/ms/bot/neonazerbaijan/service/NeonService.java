package com.kv.ms.bot.neonazerbaijan.service;

import com.kv.ms.bot.neonazerbaijan.client.InstagramClient;
import com.kv.ms.bot.neonazerbaijan.client.TelegramClient;
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

import static com.kv.ms.bot.neonazerbaijan.model.consts.MediaTypes.VIDEO;
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

    @Scheduled(cron = "0 0/10 * * * *")
    private void checkTheLastPostIdExistInDb() {

        var postDetails = instagramClient.getLatestPostId().getData().get(0);

        if (!postsRepository.existsByPostId(postDetails.getId())) {
            postsRepository.save(PostMapper.INSTANCE.toEntity(
                    postDetails,
                    false,
                    applicationProperty.getProfileId()));
            logger.info("Detected and saved new post with id {}", postDetails.getId());
        }
    }

    @SneakyThrows
    @Scheduled(cron = "@hourly")
    public void publishPost() {

        if (isMoreThanNHours(3L)) { //TODO may be refactor code
            DateUtil.lastPublishingDate = getCurrentDate();

            var postDetails = instagramClient.getLatestPostId().getData().get(0);
            var mediaId = postDetails.getId();
            var mediaType = postDetails.getMediaType();

            if (!postsRepository.existsByPostId(mediaId)) {
                logger.info("Post cannot be find in DB");

                if (mediaType.equalsIgnoreCase(VIDEO))
                    telegramClient.sendVideo(postDetails.getMediaUrl(), postDetails.getCaption());
                else
                    telegramClient.sendPhoto(postDetails.getMediaUrl(), postDetails.getCaption());

                postsRepository.save(PostMapper.INSTANCE.toEntity(
                        postDetails,
                        true,
                        applicationProperty.getProfileId()));
                return;
            }

            logger.info("Post with id: {} exists in DB", mediaId);

            var archivedPost = postsRepository.findLastPostIdWherePublishedIsFalse()
                    .orElseThrow(AllPostsIsPublishedException::new);
            mediaType = archivedPost.getMediaType();

            if (mediaType.equalsIgnoreCase(VIDEO))
                telegramClient.sendVideo(archivedPost.getMediaUrl(), archivedPost.getCaption());
            else
                telegramClient.sendPhoto(archivedPost.getMediaUrl(), archivedPost.getCaption());

            postsRepository.save(archivedPost.setIsPublished(true));
        }
        logger.info("Last post publish time is {}", DateUtil.lastPublishingDate);
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