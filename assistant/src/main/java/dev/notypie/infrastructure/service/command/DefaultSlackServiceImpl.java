package dev.notypie.infrastructure.service.command;

import dev.notypie.aggregate.commands.entity.EventHandler;
import dev.notypie.infrastructure.impl.command.slack.dto.SlackEventResponse;
import dev.notypie.infrastructure.impl.command.slack.commands.SlackCommand;
import dev.notypie.infrastructure.impl.command.slack.dto.SlackEventContents;
import dev.notypie.infrastructure.impl.command.slack.dto.contexts.SlackContext;
import dev.notypie.infrastructure.impl.command.slack.event.SlackEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultSlackServiceImpl implements SlackService {

    private final EventHandler<SlackEventContents, SlackEventResponse> responseHandler;
    private final SlackRequestParser requestParser;
    private final SlackCommandHandler handler;

    @Override
    public ResponseEntity<SlackEventResponse> categorization(Map<String, List<String>> headers, Map<String, Object> payload){
        SlackEvent<? extends SlackContext> slackEvent = this.requestParser.parseRequest(headers, payload);
        SlackCommand slackCommand = this.handler.generateSlackCommand(slackEvent);
        return this.responseHandler.generateEventResponse(slackCommand.generateEventContents());
    }

}