package org.tpuchal.utils;

import org.tpuchal.model.Mission;
import org.tpuchal.model.Rocket;
import org.tpuchal.model.enums.RocketStatus;
import org.tpuchal.repository.RocketRepository;

import java.util.Set;

public interface RocketStatusHandlers {
    static void changeRocketStatus(int id, String status) {
        Set<Rocket> rockets = RocketRepository.getRocketSet();
        for (Rocket r : rockets) {
            if (r.getId() == id) {
                switch (status) {
                    case "ON_GROUND":
                        r.setStatus(RocketStatus.ON_GROUND);
                        Mission m = r.getMission();
                        r.getMission().getRocketSet().remove(r);
                        r.setMission(null);
                        RelationHandlers.handleRocketStatusChange(r, m);
                        break;
                    case "IN_SPACE":
                        if (r.getMission() != null) {
                            r.setStatus(RocketStatus.IN_SPACE);
                            RelationHandlers.handleRocketStatusChange(r);
                        } else {
                            System.out.println("Rocket needs to be connected to mission to have it's status changed to IN_SPACE");
                        }
                        break;
                    case "IN_REPAIR":
                        if (r.getMission() != null) {
                            r.setStatus(RocketStatus.IN_REPAIR);
                            RelationHandlers.handleRocketStatusChange(r);
                        } else {
                            System.out.println("Rocket needs to be connected to mission to have it's status changed to IN_REPAIR");
                        }

                        break;
                    default:
                        System.out.println("Unknown command");
                        break;
                }
            }
        }
    }
}
