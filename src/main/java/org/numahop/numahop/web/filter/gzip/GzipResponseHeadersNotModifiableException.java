package org.numahop.numahop.web.filter.gzip;

import jakarta.servlet.ServletException;

public class GzipResponseHeadersNotModifiableException extends ServletException {

	public GzipResponseHeadersNotModifiableException(String message) {
		super(message);
	}

}
