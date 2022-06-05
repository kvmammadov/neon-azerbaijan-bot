package com.kv.ms.bot.neonazerbaijan.service;

import com.kv.ms.bot.neonazerbaijan.client.InstagramClient;
import com.kv.ms.bot.neonazerbaijan.dao.repository.PostsRepository;
import com.kv.ms.bot.neonazerbaijan.model.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InstagramService {

    private final InstagramClient instagramClient;
    private final PostsRepository postsRepository;

    public void getPostIds() {
        var postIdsResponse = instagramClient.getLatestPostId();

        postIdsResponse.getData().forEach(data -> {
            postsRepository.save(PostMapper.INSTANCE.toEntity(data.getId()));
        });
    }
}
