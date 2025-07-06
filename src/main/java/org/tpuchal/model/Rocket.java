package org.tpuchal.model;

import org.tpuchal.model.enums.RocketStatus;
import org.tpuchal.repository.RocketRepository;

public class Rocket {
    private static int idCounter = 0;
    private int id;
    private String name;
    private Mission mission;
    private RocketStatus status;

    public Rocket() throws Exception {
        setId();
        RocketRepository.addRocket(this);
        setStatus(RocketStatus.ON_GROUND);
    }

    public Rocket(String name) throws Exception {
        setId();
        setName(name);
        RocketRepository.addRocket(this);
        setStatus(RocketStatus.ON_GROUND);

    }

    public int getId() {
        return id;
    }

    public void setId() {
        this.id = idCounter++;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }

    public RocketStatus getStatus() {
        return status;
    }

    public void setStatus(RocketStatus status) {
        this.status = status;
    }

    public boolean isMissionNull() {
        return this.mission == null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nRocket information:\n");
        sb.append("Rocket id: ").append(this.id).append("\n");
        sb.append("Rocket name: ").append(this.name).append("\n");
        if (mission == null) {
            sb.append("Mission: none\n");
        } else {
            sb.append("Mission: ").append(this.mission.getMissionName()).append(" (ID: ").append(this.mission.getId()).append(")\n");
        }
        sb.append("Rocket status: ").append(this.status).append("\n");

        return sb.toString();
    }
}
