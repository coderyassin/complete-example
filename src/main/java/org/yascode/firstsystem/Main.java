package org.yascode.firstsystem;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            String absolutePath = new ClassPathResource("data/applications-data.csv").getFile().getAbsolutePath();
            System.out.println(absolutePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
