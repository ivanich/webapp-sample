package org.intelligentsia.app;

import javax.inject.Inject;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

/**
 * WebConfig.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 */
@EnableWebMvc
@Configuration
@ImportResource( { "classpath*:/META-INF/config/webapp-context.xml" } )
@ComponentScan(basePackages = {"org.springframework.samples.mvc.basic"})
public class WebConfig extends WebMvcConfigurerAdapter {

	@Inject
	private Environment environment;

	/**
	 * LocaleChangeInterceptor class changes the locale when a 'locale' request
	 * parameter is sent; e.g. /?locale=de
	 * 
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry)
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LocaleChangeInterceptor());
	}

	/**
	 * Forwards requests to the "/" resource to the "welcome" view.
	 * 
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#addViewControllers(org.springframework.web.servlet.config.annotation.ViewControllerRegistry)
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");
	}

	/**
	 * Handles HTTP GET requests for /resources/** by efficiently serving up
	 * static resources in the ${webappRoot}/resources/ directory
	 * 
	 * <code>
	 * <spring:eval expression="@applicationProps['application.version']" var="applicationVersion"/>
	 * <spring:url value="/resources-{applicationVersion}" var="resourceUrl"><spring:param name="applicationVersion" value="${applicationVersion}"/></spring:url>
	 * <script src="${resourceUrl}/dojo/dojo.js" type="text/javascript"> </script>
	 * </code>
	 * 
	 * Here we configure /resources-${application.version} lookup to
	 * <ul>
	 * <li>/resources/</li>
	 * <li>/WEB-INF/static/</li>
	 * <li>classpath:/META-INF/web-root/</li>
	 * </ul>
	 * 
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#addResourceHandlers(org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry)
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources-" + environment.getProperty("application.version") + "/**")//
				.addResourceLocations("/resources/", "/WEB-INF/static/", "classpath:/META-INF/web-root/")//
				.setCachePeriod(31556926);
	}

	/**
	 * Allows for mapping the DispatcherServlet to "/" by forwarding static
	 * resource requests to the container's default Servlet
	 * 
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#configureDefaultServletHandling(org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer)
	 */
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
		// configurer.enable("myCustomDefaultServlet");
	}

}
