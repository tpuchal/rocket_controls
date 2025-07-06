package org.tpuchal.repository;

import org.tpuchal.model.Mission;
import org.tpuchal.model.Rocket;
import org.tpuchal.model.enums.RocketStatus;

import java.util.HashSet;
import java.util.Set;

public class MissionRepository {
    private static final Set<Mission> missionSet = new HashSet<>();

    public static void addMission(Mission mission) throws IllegalArgumentException {
        if (mission == null) {
            throw new IllegalArgumentException("Mission cannot be null");
        }

        if (!missionSet.contains(mission)) {
            missionSet.add(mission);
        } else {
            throw new IllegalArgumentException("This mission already exists");
        }
    }

    public static void deleteMission(Mission mission) {
        if (missionSet.contains(mission)) {
            for (Rocket r : mission.getRocketSet()) {
                r.setMission(null);
                if (r.getStatus().equals(RocketStatus.IN_SPACE)) {
                    RocketRepository.deleteRocket(r);
                } else {
                    r.setStatus(RocketStatus.ON_GROUND);
                }
            }
            mission.setRocketSet(new HashSet<>());
            missionSet.remove(mission);
        } else {
            throw new IllegalArgumentException("Cannot delete. No such mission");
        }
    }

    public static Set<Mission> getMissionSet() {
        return missionSet;
    }
}
