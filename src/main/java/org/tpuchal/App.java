package org.tpuchal;

import org.tpuchal.banner.AppBanner;
import org.tpuchal.banner.HelpBanner;
import org.tpuchal.utils.RocketHandlers;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
        Scanner scanner = new Scanner(System.in);
        AppBanner.printBanner();
        System.out.print("Welcome. Type 'exit' to quit:\n" +
                "type 'help' to see available commands \n");
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            if(input.equalsIgnoreCase("exit")) {
                System.out.println("Exiting");
                break;
            }
            processCommand(input);
        }
    }

    private static void processCommand(String input) {
        if(input.isEmpty()) {
            return;
        }

        String[] parts = input.split("\\s+");
        String command = parts[0].toLowerCase();

        try {
            switch (command) {
                case "rocket-add":
                    StringBuilder sb = new StringBuilder();
                    for(int i = 1; i < parts.length ; i++) {
                        sb.append(parts[i]);
                        sb.append(" ");
                    }
                    RocketHandlers.handleRocketAdd(sb.toString().trim());
                    break;
                case "rocket-list":
                    RocketHandlers.handleRocketList();
                    break;
                case "rocket-delete":
                    int id = Integer.parseInt(parts[1]);
                    RocketHandlers.handleRocketDelete(id);
                    break;
                case "help":
                    HelpBanner.displayHelpBanner();
                    break;
                default:
                    System.out.println("Unknown command. Type 'help' to see available commands");
                    break;
            }

        } catch (Exception e) {
            System.out.println("Error executing command: " + e.getMessage());
        }
    }
}
