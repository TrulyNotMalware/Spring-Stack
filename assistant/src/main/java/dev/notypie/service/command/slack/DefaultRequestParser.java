package dev.notypie.service.command.slack;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.notypie.infrastructure.impl.command.slack.contexts.SlackContext;
import dev.notypie.infrastructure.impl.command.slack.event.SlackEvent;
import dev.notypie.infrastructure.impl.command.slack.event.UrlVerificationEvent;
import dev.notypie.global.constants.Constants;
import dev.notypie.global.error.ArgumentError;
import dev.notypie.global.error.exceptions.SlackDomainException;
import dev.notypie.global.error.exceptions.SlackErrorCodeImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Getter
@Service
@RequiredArgsConstructor
@Deprecated(forRemoval = true)
public class DefaultRequestParser implements SlackRequestParser {

    private final ObjectMapper objectMapper;

    @Value("${slack.api.channel}")
    private String channel;

    @Override
    public SlackEvent<SlackContext> parseSlackEventFromRequest(Map<String, List<String>> headers, Map<String, Object> payload)  {
        if(!payload.containsKey("type")){
            List<ArgumentError> argumentErrors = new ArrayList<>();
            argumentErrors.add(new ArgumentError("type","null","type cannot be NULL"));
            throw new SlackDomainException(SlackErrorCodeImpl.NOT_A_VALID_REQUEST, argumentErrors);
        }
        String payloadType = payload.get("type").toString();
        return switch (payloadType) {
            case Constants.URL_VERIFICATION -> new UrlVerificationEvent(headers, payload, this.objectMapper);
//            case Constants.EVENT_CALLBACK -> this.handleEventCallbackCommand(
//                        this.objectMapper.convertValue(payload.get("event"), AppMentionEventType.class),
//                        headers, payload);
            default -> {
                log.info("Except Unsupported events. type is {}", payloadType);
                throw new SlackDomainException(SlackErrorCodeImpl.EVENT_NOT_SUPPORTED, null);
            }
        };
    }

//    private SlackEvent<SlackContext> handleEventCallbackCommand(AppMentionEventType event, Map<String, List<String>> headers, Map<String, Object> payload){
//        String payloadType = payload.get("type").toString();
//        return switch (event.getType()) {
//            case Constants.APP_MENTION -> new AppMentionEvent(this.channel, headers, payload, this.objectMapper);
//            case Constants.MESSAGE_EVENT -> {
//                //FIXME Update later.
//                log.info("Except Unsupported events. type is {}", payloadType);
//                throw new SlackDomainException(SlackErrorCodeImpl.EVENT_NOT_SUPPORTED, null);
//            }
//            default -> {
//                log.info("Except Unsupported events. type is {}", payloadType);
//                throw new SlackDomainException(SlackErrorCodeImpl.EVENT_NOT_SUPPORTED, null);
//            }
//        };
//    }
}