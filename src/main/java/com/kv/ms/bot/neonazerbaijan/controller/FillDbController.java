package com.kv.ms.bot.neonazerbaijan.controller;

import com.kv.ms.bot.neonazerbaijan.annotation.Api;
import com.kv.ms.bot.neonazerbaijan.service.InstagramService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.OK;


@Api(path = "api/v1")
@RequiredArgsConstructor
public class FillDbController {

    private final InstagramService instagramService;

    @GetMapping("/fillDb")
    @ResponseStatus(OK)
    public void fillPostsTable(
    ) {
        instagramService.getPostIds();
    }
}
