package dev.notypie.infrastructure.impl.command.slack.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.notypie.aggregate.history.entity.History;
import dev.notypie.infrastructure.impl.command.slack.dto.SlackRequestHeaders;
import dev.notypie.infrastructure.impl.command.slack.dto.UrlVerificationDto;
import dev.notypie.infrastructure.impl.command.slack.contexts.SlackChallengeContext;
import dev.notypie.infrastructure.impl.command.slack.dto.SlackEventContents;
import dev.notypie.infrastructure.impl.command.slack.dto.SlackUrlVerificationContents;
import dev.notypie.global.constants.Constants;
import dev.notypie.infrastructure.impl.command.slack.contexts.SlackContext;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
@Getter
public class UrlVerificationEvent extends SlackEvent<SlackContext> {

    private final SlackRequestHeaders requestHeaders;
    private final SlackChallengeContext context;

    private final String requestType = Constants.URL_VERIFICATION;

    public UrlVerificationEvent(Map<String, List<String>> headers, Map<String, Object> payload, ObjectMapper mapper){
        this.requestHeaders = new SlackRequestHeaders(headers);
        this.context = SlackChallengeContext.builder()
                .urlVerificationDto(mapper.convertValue(payload, UrlVerificationDto.class))
                .requestType(this.requestType)
                .headers(headers)
                .payload(payload)
                .build();

    }

    @Override
    public SlackChallengeContext getContext() {
        return this.context;
    }

    @Override
    public String getRequestType() {return this.requestType;}

    @Override
    public String getRequestBodyAsString() {
        return null;
    }

    @Override
    public History getEventHistory() {
        return this.context.buildEventHistory();
    }


    @Override
    public SlackEventContents buildEventContents() {
        return SlackUrlVerificationContents.builder()
                .challenge(this.context.getUrlVerificationDto().getChallenge())
                .ok(true)
                .build();
    }

}
