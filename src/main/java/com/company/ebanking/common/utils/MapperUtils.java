package com.company.ebanking.common.utils;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public final class MapperUtils {

	private static final ObjectMapper mapper = new ObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,
			false);

	private MapperUtils() {
		// not called
	}

	public static String toJsonAsTring(Object object) throws IOException {
		return mapper.writeValueAsString(object);
	}

	public static byte[] toJsonAsBytes(Object object) throws IOException {
		return mapper.writeValueAsBytes(object);
	}

}
