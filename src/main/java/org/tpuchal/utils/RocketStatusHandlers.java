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
                        if(compareStatus(r.getStatus(), RocketStatus.ON_GROUND)) {
                            break;
                        }

                        r.setStatus(RocketStatus.ON_GROUND);
                        Mission m = r.getMission();
                        r.getMission().getRocketSet().remove(r);
                        r.setMission(null);
                        RelationHandlers.handleRocketStatusChange(r, m);
                        System.out.println(successChange(r) + "ON_GROUND. Current mission disconnected");
                        break;
                    case "IN_SPACE":
                        if(compareStatus(r.getStatus(), RocketStatus.IN_SPACE)) {
                            break;
                        }

                        if (r.getMission() != null) {
                            r.setStatus(RocketStatus.IN_SPACE);
                            RelationHandlers.handleRocketStatusChange(r);
                            System.out.println(successChange(r) + "IN_SPACE");
                        } else {
                            System.out.println("Rocket needs to be connected to mission to have it's status changed to IN_SPACE");
                        }
                        break;
                    case "IN_REPAIR":
                        if(compareStatus(r.getStatus(), RocketStatus.IN_SPACE)) {
                            break;
                        }
                        if (r.getMission() != null) {
                            r.setStatus(RocketStatus.IN_REPAIR);
                            RelationHandlers.handleRocketStatusChange(r);
                            System.out.println(successChange(r) + "IN_REPAIR");
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

    static String successChange(Rocket r) {
        return "Successfully changed " + r.getName() + " (ID: " + r.getId() + ") status to ";
    }

    static String thisDoesNotMakeSense() {
        return "Why would you change the status to the same as before?";
    }

    static boolean compareStatus(RocketStatus s1, RocketStatus s2) {
        if (s1 == s2) {
            System.out.println(thisDoesNotMakeSense());
        }
        return s1 == s2;
    }
}
