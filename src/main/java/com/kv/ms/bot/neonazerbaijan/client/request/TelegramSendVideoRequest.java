package com.kv.ms.bot.neonazerbaijan.client.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class TelegramSendVideoRequest {

    @JsonProperty("chat_id")
    private String chatId;
    private String caption;
    private String video;
}
