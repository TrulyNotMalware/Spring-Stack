package dev.notypie.aggregate.slack;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.notypie.aggregate.slack.dto.AppMentionEventType;
import dev.notypie.aggregate.slack.event.AppMentionEvent;
import dev.notypie.aggregate.slack.event.SlackEvent;
import dev.notypie.aggregate.slack.event.UrlVerificationEvent;
import dev.notypie.constants.Constants;
import dev.notypie.global.error.ArgumentError;
import dev.notypie.global.error.exceptions.SlackDomainException;
import dev.notypie.global.error.exceptions.SlackErrorCodeImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Getter
@Service
@RequiredArgsConstructor
public class DefaultRequestParser implements SlackRequestParser{

    private final ObjectMapper objectMapper;

    @Override
    public SlackEvent<?> parseRequest(SlackRequestHeaders headers, Map<String, Object> payload)  {
        if(!payload.containsKey("type")){
            List<ArgumentError> argumentErrors = new ArrayList<>();
            argumentErrors.add(new ArgumentError("type","null","type cannot be NULL"));
            throw new SlackDomainException(SlackErrorCodeImpl.NOT_A_VALID_REQUEST, argumentErrors);
        }
        String payloadType = payload.get("type").toString();
//        if(payloadType.equals(Constants.URL_VERIFICATION)) return new UrlVerificationEvent(headers);

        return switch (payloadType) {
            case Constants.URL_VERIFICATION -> new UrlVerificationEvent(headers, payload, this.objectMapper);
            case Constants.EVENT_CALLBACK -> {
                AppMentionEventType event = this.objectMapper.convertValue(payload.get("event"), AppMentionEventType.class);
                yield switch (event.getType()) {
                    case Constants.APP_MENTION -> new AppMentionEvent(headers, payload, this.objectMapper);
                    case Constants.MESSAGE_EVENT -> {
                        //FIXME Update later.
                        log.info("Except Unsupported events. type is {}", payloadType);
                        log.info("headers: {}",headers);
                        log.info("payload: {}",payload);
                        throw new SlackDomainException(SlackErrorCodeImpl.EVENT_NOT_SUPPORTED, null);
                    }
                    default -> {
                        log.info("Except Unsupported events. type is {}", payloadType);
                        log.info("headers: {}",headers);
                        log.info("payload: {}",payload);
                        throw new SlackDomainException(SlackErrorCodeImpl.EVENT_NOT_SUPPORTED, null);
                    }
                };
            }
            default -> {
                log.info("Except Unsupported events. type is {}", payloadType);
                log.info("headers: {}",headers);
                log.info("payload: {}",payload);
                throw new SlackDomainException(SlackErrorCodeImpl.EVENT_NOT_SUPPORTED, null);
            }
        };
    }
}
