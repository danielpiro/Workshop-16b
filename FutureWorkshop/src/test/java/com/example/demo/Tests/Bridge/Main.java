package com.example.demo.Tests.Bridge;

import com.example.demo.Controllers.BigController;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("Unit Tests running...");
        Real real = new Real(new BigController());
        real.searchProduct("dan","rotman");
//        System.out.println("Integration Tests running...");
//        System.out.println("Acceptance Tests running...");
    }
}