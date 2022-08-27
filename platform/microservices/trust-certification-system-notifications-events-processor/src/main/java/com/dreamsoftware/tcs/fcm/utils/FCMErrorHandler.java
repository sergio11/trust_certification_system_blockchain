package com.dreamsoftware.tcs.fcm.utils;

import com.dreamsoftware.tcs.fcm.exception.FCMOperationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author ssanchez
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FCMErrorHandler extends DefaultResponseErrorHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        super.handleError(response);
        log.debug("FCMErrorHandler called ... ");
        log.debug("response -> " + response.toString());
        String body = IOUtils.toString(response.getBody());
        log.debug("Error Body -> " + body);
        final Map<String, String> bodyMap = objectMapper.readValue(response.getBody(), new TypeReference<Map<String, String>>() {
        });
        log.debug("bodyMap -> " + bodyMap.toString());

        FCMOperationException operationException = new FCMOperationException();
        Map<String, Object> properties = new HashMap<>();
        properties.put("code", response.getStatusCode().toString());
        properties.put("body", body);
        properties.put("header", response.getHeaders());
        operationException.setProperties(properties);
        throw operationException;
    }
}
