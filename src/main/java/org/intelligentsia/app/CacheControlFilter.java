package org.intelligentsia.app;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * {@link CacheControlFilter}r add cache control directive on http header.
 * CacheControlMaxAge parameter can set max age (in second) or disabled filter
 * if value is negative. By default max age is set to one hour.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 * @version 1.0.0
 */
public class CacheControlFilter implements Filter {

	private static final transient String EXPIRES = "Expires";
	private static final transient String LAST_MODIFIED = "Last-Modified";
	private static final transient String CACHE_CONTROL = "Cache-Control";
	private static final transient String PUBLIC_MAX_AGE = "public, max-age=";
	private static final transient long DEFAULT_MAX_AGE = 60 * 60; // second

	private long seconds = -1;

	@Override
	public void init(final FilterConfig config) throws ServletException {
		seconds = DEFAULT_MAX_AGE;
		final String value = config.getInitParameter("CacheControlMaxAge");
		if (value != null) {
			try {
				seconds = Integer.parseInt(value);
			} catch (final NumberFormatException e) {
				seconds = -1;
			}
		}
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
		// if filter enabled
		if (seconds >= 0) {
			// do process and wrap http status
			final HttpServletResponse httpServletResponse = ((HttpServletResponse) response);
			final GetStatusWrapper wrapper = new GetStatusWrapper(httpServletResponse);
			// process request
			chain.doFilter(request, wrapper);
			// if status is ok we add header
			if (wrapper.getStatus() == HttpServletResponse.SC_OK) {
				// add cache control
				wrapper.addHeader(CACHE_CONTROL, PUBLIC_MAX_AGE + seconds);
				final long current = new Date().getTime();
				// add last modified if necessary
				if (!wrapper.containsHeader(LAST_MODIFIED)) {
					wrapper.setDateHeader(LAST_MODIFIED, current);
				}
				// add expires
				wrapper.setDateHeader(EXPIRES, System.currentTimeMillis() + (seconds * 1000L));
			}
		} else {
			// normal way
			chain.doFilter(request, response);
		}
	}

	/**
	 * Inner wrapper in order to access on http status (thank to Servlet Api
	 * since 1.0 do not change that...)
	 * 
	 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
	 */
	private static class GetStatusWrapper extends HttpServletResponseWrapper {
		/**
		 * Default is ok in case of status code not set.
		 */
		private int status = HttpServletResponse.SC_OK;

		GetStatusWrapper(final HttpServletResponse response) {
			super(response);
		}

		@Override
		public void setStatus(final int sc) {
			super.setStatus(sc);
			status = sc;
		}

		public int getStatus() {
			return status;
		}

		// remove ETag
		@Override
		public void setHeader(final String name, final String value) {
			if (!"ETag".equalsIgnoreCase(name)) {
				super.setHeader(name, value);
			}
		}

	}

}
