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
        return name;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Rocket information:\n");
        sb.append("Rocket id: " + this.id + "\n");
        sb.append("Rocket name: " + this.name + "\n");
        if (mission == null) {
            sb.append("Mission: none\n");
        } else {
            sb.append("Mission: " + this.mission.getMissionName() + " (ID: " + this.mission.getId() + ")\n");
        }
        sb.append("Rocket status: " + this.status + "\n");

        return sb.toString();
    }
}
