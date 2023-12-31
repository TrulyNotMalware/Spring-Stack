package dev.notypie.aggregate.slack.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AppMentionEventType {

    /**
     * Required scopes : app_mention:read
     */
    @JsonProperty("client_msg_id")
    private String clientMessageId;

    @JsonProperty("type")
    private String type;

    //event time stamp
    @JsonProperty("event_ts")
    private Double eventTs;

    @JsonProperty("user")
    private String user;

    @JsonProperty("text")
    private String text;

    //FIXME What is Blocks do?
//    @JsonProperty("blocks")
//    private List<Blocks> blockList;

    @JsonProperty("ts")
    private Double timeSeconds;

    @JsonProperty("channel")
    private String channel;
}
