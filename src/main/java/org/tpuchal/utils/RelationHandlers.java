package org.tpuchal.utils;

import org.tpuchal.model.Mission;
import org.tpuchal.model.Rocket;
import org.tpuchal.model.enums.MissionStatus;
import org.tpuchal.model.enums.RocketStatus;
import org.tpuchal.repository.MissionRepository;
import org.tpuchal.repository.RocketRepository;

import java.util.Optional;
import java.util.Set;

public interface RelationHandlers {
    static void handleRocketToMissionConnection(int rocketId, int missionId) throws Exception {
        if ((rocketId < 0) || (missionId < 0)) {
            throw new IllegalArgumentException("ID cannot be less than 0");
        }

        Optional<Rocket> rocket = Optional.empty();
        Optional<Mission> mission = Optional.empty();

        for (Rocket r : RocketRepository.getRocketSet()) {
            if (r.getId() == rocketId) {
                rocket = Optional.of(r);
            }
        }

        for (Mission m : MissionRepository.getMissionSet()) {
            if (m.getId() == missionId) {
                mission = Optional.of(m);
            }
        }

        if (!rocket.isPresent() || !mission.isPresent()) {
            throw new Exception("Could not find objects with given IDs");
        }

        if (rocket.get().isMissionNull()) {
            rocket.get().setMission(mission.get());
            mission.get().getRocketSet().add(rocket.get());
            rocket.get().setStatus(RocketStatus.IN_SPACE);
            updateMissionStatus(mission.get());
            System.out.println("Successfully added rocket " + rocket.get().getName() + " (ID: " + rocket.get().getId()
                    + ") to mission " + mission.get().getMissionName() + " (ID: " + mission.get().getId() + ")");
        } else {
            System.out.println("You cannot add this rocket to the mission! It already has mission of ID:"
                    + rocket.get().getMission().getId());
        }

    }

    static void updateMissionStatus(Mission mission) {
        Set<Rocket> assignedRockets = mission.getRocketSet();

        if (assignedRockets.isEmpty()) {
            mission.setStatus(MissionStatus.SCHEDULED);
        } else {
            boolean hasRocketInRepair = assignedRockets.stream()
                    .anyMatch(rocket -> rocket.getStatus() == RocketStatus.IN_REPAIR);
            if (hasRocketInRepair) {
                mission.setStatus(MissionStatus.PENDING);
            } else {
                mission.setStatus(MissionStatus.IN_PROGRESS);
            }
        }
    }

    static void handleRocketStatusChange(Rocket rocket) {
        if (rocket.getStatus() != null) {
            updateMissionStatus(rocket.getMission());
        }

    }

    static void handleRocketStatusChange(Rocket rocket, Mission m) {
        if (rocket.getStatus() != null) {
            updateMissionStatus(m);
        }
    }
}
