package com.kv.ms.bot.neonazerbaijan.service;

import com.kv.ms.bot.neonazerbaijan.dao.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TelegramService {

    private final PostRepository postRepository;

    public void getFromTable() {
        var postEntity = postRepository.findLastPostIdWherePublishedIsFalse();
        System.out.println(postEntity);
    }
}
