package com.kv.ms.bot.neonazerbaijan.client.response;


import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostIdResponse {

    private List<Data> data;
    private Paging paging;
}
