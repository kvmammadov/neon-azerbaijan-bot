package com.kv.ms.bot.neonazerbaijan.service;

import com.kv.ms.bot.neonazerbaijan.client.InstagramClient;
import com.kv.ms.bot.neonazerbaijan.client.TelegramClient;
import com.kv.ms.bot.neonazerbaijan.dao.entity.PostEntity;
import com.kv.ms.bot.neonazerbaijan.dao.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.kv.ms.bot.neonazerbaijan.util.DateUtil.isMoreThanNHours;

@Slf4j
@Service
@RequiredArgsConstructor
public class NeonService {

    private final TelegramClient telegramClient;
    private final InstagramClient instagramClient;
    private final PostsRepository postsRepository;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Date lastPublishingDate;

    {
        try {
            String lastPublishingDate_OLD = "2022-06-06 00:00:59";
            lastPublishingDate = sdf.parse(lastPublishingDate_OLD);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    public void publishPost() {

        String currentDate = sdf.format(new Date());
        Date currentDateTime = sdf.parse(currentDate);

        if (isMoreThanNHours(1L, currentDateTime, lastPublishingDate)) {
            var postDetails = instagramClient.getLatestPostId().getData().get(0);
            var postId = instagramClient.getLatestPostId().getData().get(0).getId();

            if (!postsRepository.existsByPostId(postId)) {
                logger.info("Post cannot be find in DB");
                telegramClient.sendMedia(postDetails.getMediaUrl(), postDetails.getCaption());
                postsRepository.save(PostEntity.of(
                        postDetails.getId(),
                        postDetails.getMediaType(),
                        postDetails.getMediaUrl(),
                        postDetails.getCaption(),
                        true,
                        "5362713430462517"
                ));
            } else {
                logger.info("Post with id: {} exists in DB", postId);
                var archivedPost = postsRepository.findLastPostIdWherePublishedIsFalse();
                telegramClient.sendMedia(archivedPost.getMediaUrl(), archivedPost.getCaption());
                postsRepository.save(archivedPost.setIsPublished(true));
            }
            lastPublishingDate = sdf.parse(currentDate);
        }
        else logger.info("Last post publish time is {}", lastPublishingDate);
    }

    public void fillTable() {

        var response = instagramClient.getNextPostId();
        var nextValue = response.getPaging().getNext();

        response.getData().forEach(
                data -> {
                    postsRepository.save(new PostEntity()
                            .setPostId(data.getId())
                            .setMediaType(data.getMediaType())
                            .setMediaUrl(data.getMediaUrl())
                            .setCaption(data.getCaption())
                            .setProfileId("5362713430462517"));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );

        System.out.println(nextValue);
    }

    public PostEntity getFromDb(String id) {
        return postsRepository.findByPostId(id);
    }
}
