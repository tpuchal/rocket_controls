package org.tpuchal;

import junit.framework.TestCase;
import org.tpuchal.model.Mission;
import org.tpuchal.model.Rocket;
import org.tpuchal.repository.MissionRepository;
import org.tpuchal.repository.RocketRepository;

import java.util.Set;
import java.lang.reflect.Field;

public class RocketRepositoryTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
        clearRocketRepository();
    }

    protected void tearDown() throws Exception {
        clearRocketRepository();
        resetIdCounter();
        super.tearDown();
    }

    /**
     * Helper method to clear the static rocket repository using reflection
     */
    private void clearRocketRepository() throws Exception {
        Field rocketSetField = RocketRepository.class.getDeclaredField("rocketSet");
        rocketSetField.setAccessible(true);
        Set<Rocket> rocketSet = (Set<Rocket>) rocketSetField.get(null);
        rocketSet.clear();
    }

    /**
     * Helper method to reset the static ID counter in Rocket class
     */
    private void resetIdCounter() throws Exception {
        Field idCounterField = Rocket.class.getDeclaredField("idCounter");
        idCounterField.setAccessible(true);
        idCounterField.setInt(null, 0);
    }

    public void testAddRocket_NewRocket() {
        try {
            clearRocketRepository();
            Rocket testRocket = new Rocket("Test Rocket");

            Set<Rocket> rockets = RocketRepository.getRocketSet();
            assertEquals("Repository should contain exactly 1 rocket", 1, rockets.size());
            assertTrue("Repository should contain the added rocket", rockets.contains(testRocket));
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

    public void testAddRocket_DuplicateRocket() {
        try {
            clearRocketRepository();
            Rocket testRocket = new Rocket("Test Rocket");

            RocketRepository.addRocket(testRocket);

            Set<Rocket> rockets = RocketRepository.getRocketSet();
            assertEquals("Repository should contain exactly 1 rocket (no duplicates)", 1, rockets.size());
            assertTrue("Repository should contain the rocket", rockets.contains(testRocket));
        } catch (Exception e) {
            assertEquals("This rocket already exists", e.getMessage());
        }
    }

    public void testAddRocket_MultipleUniqueRockets() {
        try {
            clearRocketRepository();
            Rocket rocket_a = new Rocket("Rocket A");
            Rocket rocket_b = new Rocket("Rocket B");
            Rocket rocket_c = new Rocket("Rocket C");

            Set<Rocket> rockets = RocketRepository.getRocketSet();
            assertEquals("Repository should contain exactly 3 rockets", 3, rockets.size());
            assertTrue("Repository should contain rocket A", rockets.contains(rocket_a));
            assertTrue("Repository should contain rocket B", rockets.contains(rocket_b));
            assertTrue("Repository should contain rocket C", rockets.contains(rocket_c));
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

    public void testAddRocket_NullRocket_ThrowsException() {
        try {
            clearRocketRepository();

            try {
                RocketRepository.addRocket(null);
                fail("Adding null rocket should throw IllegalArgumentException");
            } catch (IllegalArgumentException e) {
                assertEquals("Exception message should be correct",
                        "Rocket cannot be null", e.getMessage());
            }

            Set<Rocket> rockets = RocketRepository.getRocketSet();
            assertEquals("Repository should remain empty after failed null addition", 0, rockets.size());
        } catch (Exception e) {
            fail("Unexpected exception occurred: " + e.getMessage());
        }
    }

    public void testDeleteRocket_ExistingRocket() {
        try {
            clearRocketRepository();
            Rocket testRocket = new Rocket("Test Rocket");
            assertEquals("Repository should have 1 rocket before deletion", 1, RocketRepository.getRocketSet().size());

            RocketRepository.deleteRocket(testRocket);

            Set<Rocket> rockets = RocketRepository.getRocketSet();
            assertEquals("Repository should be empty after deletion", 0, rockets.size());
            assertFalse("Repository should not contain the deleted rocket", rockets.contains(testRocket));
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

    public void testDeleteRocket_NonExistentRocket() {
        try {
            clearRocketRepository();
            Rocket existingRocket = new Rocket("Existing Rocket");
            Rocket nonExistentRocket = new Rocket("Non-existent Rocket");
            RocketRepository.deleteRocket(nonExistentRocket);

            assertEquals("Repository should have 1 rocket", 1, RocketRepository.getRocketSet().size());

            RocketRepository.deleteRocket(nonExistentRocket);

            Set<Rocket> rockets = RocketRepository.getRocketSet();
            assertEquals("Repository should still have 1 rocket", 1, rockets.size());
            assertTrue("Repository should still contain the existing rocket", rockets.contains(existingRocket));
        } catch (Exception e) {
            assertEquals("Cannot delete. No such rocket", e.getMessage());
        }
    }

    public void testDeleteRocket_FromEmptyRepository() {
        try {
            clearRocketRepository();
            Rocket testRocket = new Rocket("Test Rocket");
            RocketRepository.deleteRocket(testRocket);

            Set<Rocket> rockets = RocketRepository.getRocketSet();
            assertEquals("Repository should remain empty", 0, rockets.size());
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

    public void testDeleteRocket_NullRocket() {
        try {
            clearRocketRepository();
            Rocket testRocket = new Rocket("Test Rocket");

            assertEquals("Repository should contain 1 rocket", 1, RocketRepository.getRocketSet().size());

            RocketRepository.deleteRocket(null);

            Set<Rocket> rockets = RocketRepository.getRocketSet();
            assertEquals("Repository should still contain 1 rocket after trying to delete null", 1, rockets.size());
            assertTrue("Repository should still contain the test rocket", rockets.contains(testRocket));
        } catch (Exception e) {
            assertEquals("Cannot delete. No such rocket", e.getMessage());
        }
    }

    public void testGetRocketSet_EmptyRepository() {
        try {
            clearRocketRepository();

            Set<Rocket> rockets = RocketRepository.getRocketSet();

            assertNotNull("getRocketSet() should never return null", rockets);
            assertEquals("Empty repository should return empty set", 0, rockets.size());
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

    public void testGetRocketSet_WithRockets() {
        try {
            clearRocketRepository();
            Rocket rocket_x = new Rocket("Rocket X");
            Rocket rocket_y = new Rocket("Rocket Y");

            Set<Rocket> rockets = RocketRepository.getRocketSet();

            assertNotNull("getRocketSet() should never return null", rockets);
            assertEquals("Repository should contain 2 rockets", 2, rockets.size());
            assertTrue("Set should contain rocket X", rockets.contains(rocket_x));
            assertTrue("Set should contain rocket Y", rockets.contains(rocket_y));
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

    public void testRepositoryIntegration_AddDeleteCycle() {
        try {
            clearRocketRepository();
            Rocket rocket = new Rocket("Test Rocket");
            clearRocketRepository();

            RocketRepository.addRocket(rocket);
            assertEquals("After adding: repository should have 1 rocket", 1, RocketRepository.getRocketSet().size());

            RocketRepository.deleteRocket(rocket);
            assertEquals("After deleting: repository should be empty", 0, RocketRepository.getRocketSet().size());

            RocketRepository.addRocket(rocket);
            assertEquals("After re-adding: repository should have 1 rocket again", 1, RocketRepository.getRocketSet().size());
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

    public void testAddRocket_MultipleNullAttempts() {
        try {
            clearRocketRepository();

            for (int i = 0; i < 3; i++) {
                try {
                    RocketRepository.addRocket(null);
                    fail("Adding null rocket should always throw IllegalArgumentException (attempt " + (i + 1) + ")");
                } catch (IllegalArgumentException e) {
                    assertEquals("Exception message should be correct on attempt " + (i + 1),
                            "Rocket cannot be null", e.getMessage());
                }
            }

            Set<Rocket> rockets = RocketRepository.getRocketSet();
            assertEquals("Repository should remain empty after multiple failed null additions", 0, rockets.size());
        } catch (Exception e) {
            fail("Unexpected exception occurred: " + e.getMessage());
        }
    }

    public void testAddRocket_NullDoesNotAffectValidOperations() {
        try {
            clearRocketRepository();
            Rocket validRocket = new Rocket("Valid Rocket");

            assertEquals("Repository should contain 1 rocket", 1, RocketRepository.getRocketSet().size());

            try {
                RocketRepository.addRocket(null);
                fail("Adding null should throw exception");
            } catch (IllegalArgumentException e) {
            }

            Set<Rocket> rockets = RocketRepository.getRocketSet();
            assertEquals("Repository should still contain 1 rocket after failed null addition", 1, rockets.size());
            assertTrue("Repository should still contain the valid rocket", rockets.contains(validRocket));

            Rocket anotherRocket = new Rocket("Another Rocket");
            clearRocketRepository();
            RocketRepository.addRocket(validRocket);
            RocketRepository.addRocket(anotherRocket);
            assertEquals("Repository should contain 2 rockets after adding another valid rocket", 2, RocketRepository.getRocketSet().size());

        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

    public void testDeleteRocket_DeleteCorrectlyNullifiesRocketMissions() {
        try {
            clearRocketRepository();
            Mission testMission = new Mission("Test Mission");
            Rocket testRocket = new Rocket("Test Rocket");

            testRocket.setMission(testMission);
            testMission.getRocketSet().add(testRocket);


            assertEquals("Repository should contain 1 Mission", 1, MissionRepository.getMissionSet().size());
            assertEquals("Mission's rockets should contain 1 Rocket", 1, testMission.getRocketSet().size());

            RocketRepository.deleteRocket(testRocket);
            assertFalse("Mission should not contain rocket", testMission.getRocketSet().contains(testRocket));
            assertNull("Rocket's mission should be null", testRocket.getMission());

        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }
}