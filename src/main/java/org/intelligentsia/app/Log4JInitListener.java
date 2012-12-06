package org.intelligentsia.app;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.util.Log4jConfigurer;

/**
 * {@link Log4JInitListener} initialize Log4j from external configuration file.
 * 
 * <p>
 * Location could be (by priority order):
 * </p>
 * <ul>
 * <li>initial filter parameter named 'logConfigurationPath"</li>
 * <li>JNDI environment variable 'java:comp/env/logConfigurationPath'</li>
 * <li>/WEB-INF/log4j.xml</li>
 * <li>/WEB-INF/log4j.properties</li>
 * <li>classpath:log4j.xml</li>
 * <li>classpath:log4j.properties</li>
 * </ul>
 * 
 * TODO finalize on other resource
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 * 
 */
public class Log4JInitListener implements ServletContextListener {

	/**
	 * Default JNDI environnement variable
	 */
	public static final String JNDI_LOG_ENTRY = "java:comp/env/logConfigurationPath";

	/**
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(final ServletContextEvent servletContextEvent) {

		String environmentPath = servletContextEvent.getServletContext().getInitParameter("logConfigurationPath");
		if (environmentPath == null) {
			environmentPath = JNDI_LOG_ENTRY;
		}

		String configPath = null;
		try {
			final Context ctx = new InitialContext();
			final URL url = (URL) ctx.lookup(environmentPath);
			configPath = url.getFile();
		} catch (final NamingException ex) {
			servletContextEvent.getServletContext().log("unable to find " + environmentPath + ": " + ex, ex);
		}
		if (configPath == null) {
			URL fallBackResource = null;
			try {
				fallBackResource = servletContextEvent.getServletContext().getResource("/WEB-INF/log4j.properties");
			} catch (final MalformedURLException e) {

			}
			if (fallBackResource == null) {
				servletContextEvent.getServletContext().log("log4j configuration path was null, unable to configure log4j from JNDI env var of path to config file.");
				throw new IllegalStateException("log4j configuration path was null, unable to configure log4j from JNDI env var of path to config file.");
			}

			servletContextEvent.getServletContext().log("log4j configuration has been loaded from class path properties (WEB-INF/log4j.properties) instead of JNDI property");
			configPath = fallBackResource.getFile();
		}
		servletContextEvent.getServletContext().log("Use log4j configuration found at " + configPath);
		try {
			Log4jConfigurer.initLogging(configPath, 10000);
		} catch (final FileNotFoundException e) {
			servletContextEvent.getServletContext().log("log4j configuration path was not found, unable to configure log4j from JNDI env var of path to config file.");
			throw new IllegalArgumentException(e);
		}

	}

	/**
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(final ServletContextEvent evt) {
		Log4jConfigurer.shutdownLogging();
	}
}