package com.example.Lee.config;

import com.example.Lee.model.ApiResponseSerializer;
import com.example.Lee.model.LoginRsltModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class ObjectMapperConfiguration {

	public static ObjectMapper getObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		module.addSerializer(LoginRsltModel.class, new ApiResponseSerializer());
		objectMapper.registerModule(module);
		return objectMapper;
	}
}
