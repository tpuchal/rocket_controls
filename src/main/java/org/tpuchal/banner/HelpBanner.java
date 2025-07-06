package org.tpuchal.banner;

public class HelpBanner {
    public static void displayHelpBanner() {
        StringBuilder sb = new StringBuilder();
        sb.append("=========HELP=========\n");
        sb.append("Here is the list of the commands you can use:\n");
        sb.append("exit - exits the app\n");
        sb.append("rocket-add <rocket_name> - adds a new rocket\n");
        sb.append("rocket-list - displays all the rockets in the repository\n");
        sb.append("rocket-delete <rocket_id> - deletes a rocket from the repository\n");
        sb.append("mission-add <mission name> - adds mission name");
        sb.append("mission-list - displays current missions \n");
        sb.append("mission-delete <mission_id> - deletes a mission and disconnects rockets from mission TODO: handle situation where rockets are still in space \n");
        System.out.println(sb.toString());
    }
}
