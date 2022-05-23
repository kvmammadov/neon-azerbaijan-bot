package com.kv.ms.bot.neonazerbaijan.service;

import com.kv.ms.bot.neonazerbaijan.client.InstagramClient;
import com.kv.ms.bot.neonazerbaijan.dao.repository.PostRepository;
import com.kv.ms.bot.neonazerbaijan.model.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InstagramService {

    private final InstagramClient instagramClient;
    private final PostRepository postRepository;

    public void getPostIds() {
        var postIdsResponse = instagramClient.getPostIds();

        postIdsResponse.getData().forEach(data -> {
            postRepository.save(PostMapper.INSTANCE.toEntity(data.getId()));
        });
    }
}
