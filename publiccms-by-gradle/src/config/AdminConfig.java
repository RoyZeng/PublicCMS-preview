package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import com.publiccms.common.interceptor.admin.AdminContextInterceptor;
import com.publiccms.common.view.admin.AdminFreeMarkerView;

/**
 * 
 * AdminConfig AdminServlet配置类
 *
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.publiccms.views.controller.admin", useDefaultFilters = false, includeFilters = {
        @ComponentScan.Filter(value = { Controller.class }) })
public class AdminConfig extends WebMvcConfigurerAdapter {
    /**
     * 视图层解析器
     * 
     * @return
     */
    @Bean
    public ViewResolver jspViewResolver() {
        InternalResourceViewResolver bean = new InternalResourceViewResolver();
        bean.setOrder(1);
        bean.setPrefix("/WEB-INF/template/");
        bean.setSuffix(".jsp");
        bean.setContentType("text/html;charset=UTF-8");
        return bean;
    }

    /**
     * 视图层解析器
     * 
     * @return
     */
    @Bean
    public ViewResolver viewResolver() {
        FreeMarkerViewResolver bean = new FreeMarkerViewResolver();
        bean.setOrder(0);
        bean.setViewClass(AdminFreeMarkerView.class);
        bean.setPrefix("/template/");
        bean.setSuffix(".html");
        bean.setContentType("text/html;charset=UTF-8");
        return bean;
    }

    /**
     * 拦截器
     * 
     * @return
     */
    @Bean
    public AdminContextInterceptor initializingInterceptor() {
        AdminContextInterceptor bean = new AdminContextInterceptor();
        bean.setLoginUrl("/login.html");
        bean.setUnauthorizedUrl("/common/unauthorizedUrl.html");
        bean.setLoginJsonUrl("/common/ajaxTimeout.html");
        bean.setNeedNotLoginUrls(new String[] { "/logout", "/common/", "/login" });
        bean.setNeedNotAuthorizedUrls(new String[] { "/index", "/main", "/menus" });
        return bean;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(initializingInterceptor());
    }
}
