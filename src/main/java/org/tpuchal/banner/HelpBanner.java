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
        sb.append("mission-add <mission name> - adds mission name\n");
        sb.append("mission-list - displays current missions \n");
        sb.append("mission-delete <mission_id> - deletes a mission and disconnects rockets from mission. \n     When deleted mission has rockets connected, they are presumed destroyed if in space and removed from the repository \n");
        sb.append("mission-add-rocket <rocket_id> <mission_id> - adds rocket to a mission\n");
        sb.append("rocket-status-change <rocket_id> <status> - changes rocket status. Certain inputs can change mission status too.\n   allowed inputs: 'in space', 'on ground', 'in repair'\n");
        sb.append("mission-end - ends mission and disconnects all rockets from current mission. Sets all rockets status to ON_GROUND\n");
        System.out.println(sb);
    }
}
