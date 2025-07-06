package org.tpuchal.utils;

import org.tpuchal.model.Mission;
import org.tpuchal.model.Rocket;
import org.tpuchal.model.enums.MissionStatus;
import org.tpuchal.model.enums.RocketStatus;
import org.tpuchal.repository.MissionRepository;

import java.util.HashSet;

public interface MissionHandlers {
    static void handleMissionList() {
        if (MissionRepository.getMissionSet().isEmpty()) {
            System.out.println("There are currently no missions");
            return;
        }
        System.out.println("Missions");
        for (Mission m : MissionRepository.getMissionSet()) {
            System.out.println(m.toString());
        }
    }

    static void handleMissionAdd(String name) throws Exception {
        Mission m = new Mission(name);
        System.out.println("New mission added: \n" + m);
    }

    static void handleMissionDelete(int id) {
        for (Mission m : MissionRepository.getMissionSet()) {
            if (m.getId() == id) {
                MissionRepository.deleteMission(m);
                System.out.println("Mission removed: \n" + m);
                return;
            }
        }
        System.out.println("No mission with id " + id + " found");
    }

    static void handleMissionEnd(int id) {
        for(Mission m : MissionRepository.getMissionSet()) {
            if(m.getId() == id) {
                m.setStatus(MissionStatus.ENDED);
                for(Rocket r : m.getRocketSet()) {
                    r.setMission(null);
                    r.setStatus(RocketStatus.ON_GROUND);
                }
                m.setRocketSet(new HashSet<>());
                System.out.println("Mission " + m.getMissionName() + " of ID: " + m.getId() + "\n SUCCESSFULLY ENDED");
                return;
            }
        }
    }
}
