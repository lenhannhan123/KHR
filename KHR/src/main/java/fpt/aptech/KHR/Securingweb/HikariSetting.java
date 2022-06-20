/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Securingweb;

import com.zaxxer.hikari.HikariConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Admin
 */
@Configuration
public class HikariSetting{

    @Bean
    public HikariConfig config() {
        HikariConfig hikariConfig = new HikariConfig();
        
        // other setting
        
        hikariConfig.addDataSourceProperty("socketTimeout", 80000000);
        hikariConfig.setMaxLifetime(80000000);
        
        return hikariConfig;
    }
    
}
