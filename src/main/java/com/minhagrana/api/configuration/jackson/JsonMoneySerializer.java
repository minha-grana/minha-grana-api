package com.minhagrana.api.configuration.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.joda.money.Money;

import java.io.IOException;


public class JsonMoneySerializer extends StdSerializer<Money> {

    public JsonMoneySerializer() {
        super(Money.class);
    }

    @Override
    public void serialize(Money value, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeObjectField("currency", value.getCurrencyUnit().toString());
        jsonGenerator.writeObjectField("value", value.getAmountMinor().toString());
        jsonGenerator.writeEndObject();
    }
}
