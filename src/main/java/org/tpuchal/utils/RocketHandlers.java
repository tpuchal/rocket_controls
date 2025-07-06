package org.tpuchal.utils;

import org.tpuchal.model.Rocket;
import org.tpuchal.repository.RocketRepository;

public interface RocketHandlers {
    static void handleRocketList() {
        if (RocketRepository.getRocketSet().isEmpty()) {
            System.out.println("There are no currently no rockets");
            return;
        }
        System.out.println("Rockets");
        for (Rocket r : RocketRepository.getRocketSet()) {
            System.out.println(r.toString());
        }
    }

    static void handleRocketAdd(String name) throws Exception {
        Rocket r = new Rocket(name);
        System.out.println("New rocket added: \n" + r);
    }

    static void handleRocketDelete(int id) {
        for (Rocket r : RocketRepository.getRocketSet()) {
            if (r.getId() == id) {
                RocketRepository.deleteRocket(r);
                System.out.println("Rocket removed: " + r);
                return;
            }
        }
        System.out.println("No rocket with id " + id + " found");
    }
}
