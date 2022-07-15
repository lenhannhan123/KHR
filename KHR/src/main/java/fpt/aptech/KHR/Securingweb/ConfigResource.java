/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Securingweb;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @author Admin
 */
//@EnableWebMvc
//@Configuration
public class ConfigResource implements WebMvcConfigurer{
    
    @Override 
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        
    registry.addResourceHandler("/resources/static/**");
    }
    
    
}
