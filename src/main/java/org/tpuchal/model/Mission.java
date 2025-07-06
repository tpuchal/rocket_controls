package org.tpuchal.model;

import java.util.HashSet;
import java.util.Set;

public class Mission {
    private static int idCounter = 0;
    private int id;
    private Set<Rocket> rocketSet = new HashSet<>();
}
