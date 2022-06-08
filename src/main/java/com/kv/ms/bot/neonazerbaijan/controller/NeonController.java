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
    private final String next = "https://graph.instagram.com/v13.0/17841408227232043/media?access_token=IGQVJWNEJZASGs3Q2gtSzRQdEpkWm9VTHBYelh3NWpvTC1DTUpBcWxlLTRHMWJGZAVlHQ3Y5cWhxOVRQNkZAGTFp5Qm1lWEZAlbnJKcTAtdm4tOVNnSWk5R1diNTFja3FBa3ZAONWk3R1ZALS251N2ZAEeUNQZAgZDZD&fields=id%2Cmedia_type%2Cmedia_url%2Ctimestamp%2Ccaption&limit=100&after=QVFIUnFGSzY2UVZAqNmhhOF9GZAkxKLWtVTThjZAnBPcEprSWJKaHpESTluSUZArN2lxbU5SNW9NNzVfWTBCN18yck85LUdSclVnNG1KRnlqQjBaS1E4VFdBazF3";
            //"https://graph.instagram.com/v13.0/17841408227232043/media?access_token=IGQVJWNEJZASGs3Q2gtSzRQdEpkWm9VTHBYelh3NWpvTC1DTUpBcWxlLTRHMWJGZAVlHQ3Y5cWhxOVRQNkZAGTFp5Qm1lWEZAlbnJKcTAtdm4tOVNnSWk5R1diNTFja3FBa3ZAONWk3R1ZALS251N2ZAEeUNQZAgZDZD&fields=id%2Cmedia_type%2Cmedia_url%2Ctimestamp%2Ccaption&limit=100&after=QVFIUmN5QW9tXzlYNDBxaUJ1Mk5MWjNldUlMV3M3cFZARbDJORVJfMjdLVUUyNGF1XzlTRzhOenR1c3RlXzAxSUZAsODRxeGN3SzlkTlh0emw1MGM1OHdpSnpn";
            //"https://graph.instagram.com/5362713430462517/media?fields=id,media_type,media_url,timestamp,caption&access_token=IGQVJWNEJZASGs3Q2gtSzRQdEpkWm9VTHBYelh3NWpvTC1DTUpBcWxlLTRHMWJGZAVlHQ3Y5cWhxOVRQNkZAGTFp5Qm1lWEZAlbnJKcTAtdm4tOVNnSWk5R1diNTFja3FBa3ZAONWk3R1ZALS251N2ZAEeUNQZAgZDZD&limit=100";

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
