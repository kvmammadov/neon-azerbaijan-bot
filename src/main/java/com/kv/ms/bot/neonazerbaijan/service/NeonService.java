package com.kv.ms.bot.neonazerbaijan.service;

import com.kv.ms.bot.neonazerbaijan.client.InstagramClient;
import com.kv.ms.bot.neonazerbaijan.client.TelegramClient;
import com.kv.ms.bot.neonazerbaijan.client.response.PostIdResponse;
import com.kv.ms.bot.neonazerbaijan.config.ApplicationProperty;
import com.kv.ms.bot.neonazerbaijan.dao.entity.PostEntity;
import com.kv.ms.bot.neonazerbaijan.dao.repository.PostsRepository;
import com.kv.ms.bot.neonazerbaijan.exception.AllPostsIsPublishedException;
import com.kv.ms.bot.neonazerbaijan.model.mapper.PostMapper;
import com.kv.ms.bot.neonazerbaijan.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.procedure.spi.ParameterRegistrationImplementor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.kv.ms.bot.neonazerbaijan.model.consts.ErrorMessages.ALL_POSTS_ARE_PUBLISHED;
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

    @SneakyThrows
    public void publishPost() {

        if (isMoreThanNHours(1L)) {
            var postDetails = instagramClient.getLatestPostId().getData().get(0);
            var postId = instagramClient.getLatestPostId().getData().get(0).getId();

            if (!postsRepository.existsByPostId(postId)) {
                logger.info("Post cannot be find in DB");
                telegramClient.sendMedia(postDetails.getMediaUrl(), postDetails.getCaption());
                postsRepository.save(PostMapper.INSTANCE.toEntity(postDetails));

//                postsRepository.save(PostEntity.of(
//                        postDetails.getId(),
//                        postDetails.getMediaType(),
//                        postDetails.getMediaUrl(),
//                        postDetails.getCaption(),
//                        true,
//                        applicationProperty.getProfileId()
//                ));
            } else {
                logger.info("Post with id: {} exists in DB", postId);

                var archivedPost = postsRepository.findLastPostIdWherePublishedIsFalse()
                        .orElseThrow(AllPostsIsPublishedException::new);
                telegramClient.sendMedia(archivedPost.getMediaUrl(), archivedPost.getCaption());
                postsRepository.save(archivedPost.setIsPublished(true));
            }

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
                    postsRepository.save(new PostEntity()
                            .setPostId(data.getId())
                            .setMediaType(data.getMediaType())
                            .setMediaUrl(data.getMediaUrl())
                            .setCaption(data.getCaption())
                            .setProfileId(applicationProperty.getProfileId()));
//                    try {
                    counter++;
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                }
        );

        next = response.getPaging().getNext();
        System.out.println(next);
        fillTable(next);
    }
}
