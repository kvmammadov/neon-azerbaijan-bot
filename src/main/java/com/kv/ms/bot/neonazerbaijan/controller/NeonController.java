package com.kv.ms.bot.neonazerbaijan.controller;

import com.kv.ms.bot.neonazerbaijan.annotation.Api;
import com.kv.ms.bot.neonazerbaijan.dao.entity.PostEntity;
import com.kv.ms.bot.neonazerbaijan.service.NeonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.OK;

@Api(path = "api/v1")
@RequiredArgsConstructor
public class NeonController {

    private final NeonService neonService;

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
        neonService.fillTable();
    }

    @GetMapping("/get-from-db/{id}")
    @ResponseStatus(OK)
    public PostEntity getFromDb(
            @PathVariable("id") String id
    ) {
        return neonService.getFromDb(id);
    }
}
