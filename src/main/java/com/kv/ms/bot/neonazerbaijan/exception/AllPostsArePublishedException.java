package com.kv.ms.bot.neonazerbaijan.exception;

import static com.kv.ms.bot.neonazerbaijan.model.consts.ErrorMessages.ALL_POSTS_ARE_PUBLISHED;

public class AllPostsArePublishedException extends Throwable {

    public AllPostsArePublishedException() {
        super(ALL_POSTS_ARE_PUBLISHED);
    }
}
