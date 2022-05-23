package com.kv.ms.bot.neonazerbaijan.controller;

import com.kv.ms.bot.neonazerbaijan.annotation.Api;
import com.kv.ms.bot.neonazerbaijan.service.InstagramService;
import com.kv.ms.bot.neonazerbaijan.service.TelegramService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.OK;

@Api(path = "api/v1")
@RequiredArgsConstructor
public class TelegramController {

    private final TelegramService telegramService;

    @GetMapping("/getEntity")
    @ResponseStatus(OK)
    public void fillPostsTable(
    ) {
        telegramService.getFromTable();
    }
}
