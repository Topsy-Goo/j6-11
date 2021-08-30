package ru.gb.antonov.j611;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication (scanBasePackages = "ru.gb.antonov.j611.beans")
public class J611Application
{

    public static void main (String[] args)
    {
        SpringApplication.run(J611Application.class, args);
    }
}
