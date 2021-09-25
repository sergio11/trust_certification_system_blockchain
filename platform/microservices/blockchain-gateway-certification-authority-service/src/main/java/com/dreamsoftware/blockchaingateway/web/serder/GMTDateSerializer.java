package com.dreamsoftware.blockchaingateway.web.serder;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author ssanchez
 */
public class GMTDateSerializer extends StdSerializer<Date> {

    private final SimpleDateFormat formatter
            = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    public GMTDateSerializer() {
        this(null);
    }

    public GMTDateSerializer(Class t) {
        super(t);
    }

    @Override
    public void serialize(Date value, JsonGenerator gen, SerializerProvider arg2)
            throws IOException, JsonProcessingException {
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        gen.writeString(formatter.format(value));
    }
}
