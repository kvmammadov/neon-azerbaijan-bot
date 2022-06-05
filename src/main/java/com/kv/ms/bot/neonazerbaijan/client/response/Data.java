package com.kv.ms.bot.neonazerbaijan.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Data {

    private String id;
    @JsonProperty("media_type")
    private String mediaType;
    @JsonProperty("media_url")
    private String mediaUrl;
    private String caption;
    @JsonProperty("timestamp")
    private String timeStamp;
}
