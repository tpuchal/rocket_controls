package org.tpuchal.repository;

import org.tpuchal.model.Rocket;

import java.util.HashSet;
import java.util.Set;

public class RocketRepository {
    private static Set<Rocket> rocketSet = new HashSet<>();

    public static void addRocket(Rocket rocket) throws Exception {
        if(rocket == null) {
            throw new IllegalArgumentException("Rocket cannot be null");
        }

        if(!rocketSet.contains(rocket)) {
            rocketSet.add(rocket);
        }
    }

    public static void deleteRocket(Rocket rocket) {
        if(rocketSet.contains(rocket)) {
            rocketSet.remove(rocket);
        }
    }

    public static Set<Rocket> getRocketSet() {
        return rocketSet;
    }
}
