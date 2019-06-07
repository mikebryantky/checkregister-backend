package com.mikebryant.checkregister.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

@Component
public class ConfigInfo implements ServletContextAware {

    private ServletContext servletContext;


    @Value("${info.app.name}")
    private String infoAppName;

    @Value("${info.app.version}")
    private String infoAppVersion;


    public String getInfoAppName() {
        return infoAppName;
    }

    public void setInfoAppName(String infoAppName) {
        this.infoAppName = infoAppName;
    }

    public String getInfoAppVersion() {
        return infoAppVersion;
    }

    public void setInfoAppVersion(String infoAppVersion) {
        this.infoAppVersion = infoAppVersion;
    }


    @PostConstruct
    public void init() {
        servletContext.setAttribute("configInfo", this);
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}
