package com.kv.ms.bot.neonazerbaijan.exception;

import static com.kv.ms.bot.neonazerbaijan.model.consts.ErrorMessages.ALL_POSTS_ARE_PUBLISHED;

public class AllPostsIsPublishedException extends Throwable {

    public AllPostsIsPublishedException() {
        super(ALL_POSTS_ARE_PUBLISHED);
    }
}
