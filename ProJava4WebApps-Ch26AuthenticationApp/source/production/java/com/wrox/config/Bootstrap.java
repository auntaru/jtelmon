/*
 * http://localhost:8080/authentication/login
 * http://java.dzone.com/articles/springmvc4-spring-data-jpa
 * http://www.sivalabs.in/2014/03/springmvc4-spring-data-jpa.html
 * http://www.javacodegeeks.com/2014/03/springmvc4-spring-data-jpa-springsecurity-configuration-using-javaconfig.html
 * https://github.com/sivaprasadreddy/sivalabs-blog-samples-code/tree/master/springmvc-datajpa-security-demo
 * 
 * http://dtr-trading.blogspot.ro/2014/02/spring-mvc-4-security-part-1.html
 * http://dtr-trading.blogspot.ro/2014/04/spring-security-32-authorization-part-1.html
 * http://dtr-trading.blogspot.ro/search/label/Security
 * 
 * http://www.raistudies.com/spring-security-tutorial-acegi/
 * http://www.raistudies.com/spring-security-tutorial/authentication-authorization-spring-security-mysql-database/
 * 
 */

package com.wrox.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

@SuppressWarnings("unused")
public class Bootstrap implements WebApplicationInitializer
{
    @Override
    public void onStartup(ServletContext container) throws ServletException
    {
        container.getServletRegistration("default").addMapping("/resource/*");

        AnnotationConfigWebApplicationContext rootContext =
                new AnnotationConfigWebApplicationContext();
        rootContext.register(RootContextConfiguration.class);
        container.addListener(new ContextLoaderListener(rootContext));

        AnnotationConfigWebApplicationContext webContext =
                new AnnotationConfigWebApplicationContext();
        webContext.register(WebServletContextConfiguration.class);
        ServletRegistration.Dynamic dispatcher = container.addServlet(
                "springWebDispatcher", new DispatcherServlet(webContext)
        );
        dispatcher.setLoadOnStartup(1);
        dispatcher.setMultipartConfig(new MultipartConfigElement(
                null, 20_971_520L, 41_943_040L, 512_000
        ));
        dispatcher.addMapping("/");

        AnnotationConfigWebApplicationContext restContext =
                new AnnotationConfigWebApplicationContext();
        restContext.register(RestServletContextConfiguration.class);
        DispatcherServlet servlet = new DispatcherServlet(restContext);
        servlet.setDispatchOptionsRequest(true);
        dispatcher = container.addServlet(
                "springRestDispatcher", servlet
        );
        dispatcher.setLoadOnStartup(2);
        dispatcher.addMapping("/services/Rest/*");
    }
}
