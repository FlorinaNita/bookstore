package sda.store;


import org.springframework.boot.SpringApplication;
import sda.store.config.AppConfig;

public class App
{
    public static void main( String[] args ) {

        SpringApplication.run(AppConfig.class);
    }
}
