package com.kv.ms.bot.neonazerbaijan.controller;

import com.kv.ms.bot.neonazerbaijan.annotation.Api;
import com.kv.ms.bot.neonazerbaijan.service.NeonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.OK;

@Api(path = "api/v1")
@RequiredArgsConstructor
public class NeonController {

    private final NeonService neonService;
    private final String next = "https://graph.instagram.com/5362713430462517/media?fields=id,media_type,media_url,timestamp,caption&access_token=IGQVJWNEJZASGs3Q2gtSzRQdEpkWm9VTHBYelh3NWpvTC1DTUpBcWxlLTRHMWJGZAVlHQ3Y5cWhxOVRQNkZAGTFp5Qm1lWEZAlbnJKcTAtdm4tOVNnSWk5R1diNTFja3FBa3ZAONWk3R1ZALS251N2ZAEeUNQZAgZDZD&limit=100";

    @GetMapping("/getLatestPost")
    @ResponseStatus(OK)
    public void publishPost(
    ) {
        neonService.publishPost();
    }

    @GetMapping("/fillDb")
    @ResponseStatus(OK)
    public void fillPostsTable(
    ) {
        neonService.fillTable(next);
    }
}
