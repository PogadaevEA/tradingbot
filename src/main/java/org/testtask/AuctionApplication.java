package org.testtask;

import org.testtask.demo.console.ConsoleExecutor;

public class AuctionApplication {

    private static final ConsoleExecutor consoleExecutor = new ConsoleExecutor();

    public static void main(String[] args) {
        consoleExecutor.runConsoleApplication();
        System.out.println("The application is being terminated.");
    }
}