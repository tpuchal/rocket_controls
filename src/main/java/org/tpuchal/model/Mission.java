package org.tpuchal.model;

import org.tpuchal.model.enums.MissionStatus;
import org.tpuchal.repository.MissionRepository;

import java.util.HashSet;
import java.util.Set;

public class Mission {
    private static int idCounter = 0;
    private int id;
    private String missionName;
    private Set<Rocket> rocketSet = new HashSet<>();
    private MissionStatus status;

    public Mission(String missionName) {
        this.setId();
        this.setMissionName(missionName);
        this.setStatus(MissionStatus.SCHEDULED);
        MissionRepository.addMission(this);
    }

    public int getId() {
        return id;
    }

    public void setId() {
        this.id = idCounter++;
    }

    public String getMissionName() {
        return missionName;
    }

    public void setMissionName(String missionName) {
        this.missionName = missionName;
    }

    public Set<Rocket> getRocketSet() {
        return rocketSet;
    }

    public void setRocketSet(Set<Rocket> rocketSet) {
        this.rocketSet = rocketSet;
    }

    public MissionStatus getStatus() {
        return status;
    }

    public void setStatus(MissionStatus status) {
        this.status = status;
    }

    private String rockets() {
        StringBuilder sb = new StringBuilder();
        if (this.getRocketSet().isEmpty()) {
            return "No rockets assigned";
        }
        for (Rocket rocket : this.getRocketSet()) {
            sb.append(rocket);
        }
        return sb.toString();
    }

    @Override
    public String toString() {

        return "=========MISSION: " +
                this.getMissionName() + "=========\n" +
                "Mission Id: " + this.getId() + "\n" +
                "Mission Status: " + this.getStatus() + "\n" +
                "Rockets: \n" +
                rockets() + "\n"
                + "===========================\n";
    }
}
