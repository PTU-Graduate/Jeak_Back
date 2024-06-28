package com.example.Lee.model;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class ApiResponseSerializer extends JsonSerializer<LoginRsltModel> {
	@Override
	public void serialize(LoginRsltModel apiResponse, JsonGenerator jsonGenerator,
			SerializerProvider serializerProvider) throws IOException {
		jsonGenerator.writeStartObject();
		jsonGenerator.writeStringField("RSLT_CD", apiResponse.getRSLT_CD());
		jsonGenerator.writeEndObject();
	}
}
