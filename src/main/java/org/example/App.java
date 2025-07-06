package org.example;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome. Type 'exit' to quit:");
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();
            if(input.equalsIgnoreCase("exit")) {
                System.out.println("Exiting");
                break;
            } else {
                System.out.println("> " + input);
            }
        }
    }
}
