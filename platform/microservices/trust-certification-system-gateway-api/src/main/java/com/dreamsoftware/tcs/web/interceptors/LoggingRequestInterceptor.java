package com.dreamsoftware.tcs.web.interceptors;

import com.google.common.io.ByteStreams;
import java.io.IOException;
import java.nio.charset.Charset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

/**
 *
 * @author ssanchez
 */
public class LoggingRequestInterceptor implements ClientHttpRequestInterceptor {

    final static Logger log = LoggerFactory.getLogger(LoggingRequestInterceptor.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        traceRequest(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        traceResponse(response);
        return response;
    }

    private void traceRequest(HttpRequest request, byte[] body) throws IOException {
        log.debug("===========================request begin================================================");
        log.debug("URI         : {}", request.getURI());
        log.debug("Method      : {}", request.getMethod());
        log.debug("Headers     : {}", request.getHeaders());
        log.debug("Request body: {}", new String(body, "UTF-8"));
        log.debug("==========================request end================================================");
    }

    private void traceResponse(ClientHttpResponse response) {
        try {
            log.debug("============================response begin==========================================");
            log.debug("Status code  : {}", response.getStatusCode());
            log.debug("Status text  : {}", response.getStatusText());
            log.debug("Headers      : {}", response.getHeaders());
            try {
                final String bodyAsString = new String(ByteStreams.toByteArray(response.getBody()), Charset.forName("UTF-8"));
                log.debug("Body: ", bodyAsString);
            } catch (final Exception ex) {
                log.debug("Parse Body Exception -> " + ex.getMessage());
            }
            log.debug("=======================response end=================================================");
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
    }
}
