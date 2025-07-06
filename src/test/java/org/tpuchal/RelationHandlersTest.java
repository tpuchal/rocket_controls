package org.tpuchal;

import junit.framework.TestCase;
import org.tpuchal.model.Mission;
import org.tpuchal.model.Rocket;
import org.tpuchal.model.enums.MissionStatus;
import org.tpuchal.model.enums.RocketStatus;
import org.tpuchal.repository.MissionRepository;
import org.tpuchal.repository.RocketRepository;
import org.tpuchal.utils.RelationHandlers;

import java.util.Set;
import java.lang.reflect.Field;

public class RelationHandlersTest extends TestCase {

    private Rocket testRocket1;
    private Rocket testRocket2;
    private Mission testMission1;
    private Mission testMission2;

    protected void setUp() throws Exception {
        super.setUp();
        clearRepositories();
        resetIdCounters();

        testRocket1 = new Rocket("Test Rocket 1");
        testRocket2 = new Rocket("Test Rocket 2");
        testMission1 = new Mission("Test Mission 1");
        testMission2 = new Mission("Test Mission 2");
    }

    protected void tearDown() throws Exception {
        clearRepositories();
        resetIdCounters();
        super.tearDown();
    }

    /**
     * Helper method to clear both repositories using reflection
     */
    private void clearRepositories() throws Exception {
        Field rocketSetField = RocketRepository.class.getDeclaredField("rocketSet");
        rocketSetField.setAccessible(true);
        Set<Rocket> rocketSet = (Set<Rocket>) rocketSetField.get(null);
        rocketSet.clear();

        Field missionSetField = MissionRepository.class.getDeclaredField("missionSet");
        missionSetField.setAccessible(true);
        Set<Mission> missionSet = (Set<Mission>) missionSetField.get(null);
        missionSet.clear();
    }

    /**
     * Helper method to reset ID counters
     */
    private void resetIdCounters() throws Exception {
        Field rocketIdCounterField = Rocket.class.getDeclaredField("idCounter");
        rocketIdCounterField.setAccessible(true);
        rocketIdCounterField.setInt(null, 0);

        Field missionIdCounterField = Mission.class.getDeclaredField("idCounter");
        missionIdCounterField.setAccessible(true);
        missionIdCounterField.setInt(null, 0);
    }


    public void testHandleRocketToMissionConnection_ValidConnection() {
        try {
            int rocketId = testRocket1.getId();
            int missionId = testMission1.getId();

            RelationHandlers.handleRocketToMissionConnection(rocketId, missionId);

            assertEquals("Rocket should be assigned to the mission", testMission1, testRocket1.getMission());

            assertTrue("Mission should contain the rocket", testMission1.getRocketSet().contains(testRocket1));

            assertEquals("Rocket status should be IN_SPACE", RocketStatus.IN_SPACE, testRocket1.getStatus());

            assertEquals("Mission status should be IN_PROGRESS", MissionStatus.IN_PROGRESS, testMission1.getStatus());

        } catch (Exception e) {
            fail("Valid connection should not throw exception: " + e.getMessage());
        }
    }

    public void testHandleRocketToMissionConnection_DifferentRocketAndMission() {
        try {
            int rocketId = testRocket2.getId();
            int missionId = testMission2.getId();

            RelationHandlers.handleRocketToMissionConnection(rocketId, missionId);

            assertEquals("Rocket2 should be assigned to Mission2", testMission2, testRocket2.getMission());
            assertTrue("Mission2 should contain Rocket2", testMission2.getRocketSet().contains(testRocket2));
            assertEquals("Rocket2 status should be IN_SPACE", RocketStatus.IN_SPACE, testRocket2.getStatus());
            assertEquals("Mission2 status should be IN_PROGRESS", MissionStatus.IN_PROGRESS, testMission2.getStatus());

        } catch (Exception e) {
            fail("Valid connection should not throw exception: " + e.getMessage());
        }
    }


    public void testHandleRocketToMissionConnection_NegativeRocketId() {
        int missionId = testMission1.getId();

        try {
            RelationHandlers.handleRocketToMissionConnection(-1, missionId);
            fail("Should throw IllegalArgumentException for negative rocket ID");
        } catch (IllegalArgumentException e) {
            assertEquals("Should have correct error message for negative rocket ID",
                    "ID cannot be less than 0", e.getMessage());
        } catch (Exception e) {
            fail("Should throw IllegalArgumentException, not " + e.getClass().getSimpleName());
        }
    }

    public void testHandleRocketToMissionConnection_NegativeMissionId() {
        int rocketId = testRocket1.getId();

        try {
            RelationHandlers.handleRocketToMissionConnection(rocketId, -1);
            fail("Should throw IllegalArgumentException for negative mission ID");
        } catch (IllegalArgumentException e) {
            assertEquals("Should have correct error message for negative mission ID",
                    "ID cannot be less than 0", e.getMessage());
        } catch (Exception e) {
            fail("Should throw IllegalArgumentException, not " + e.getClass().getSimpleName());
        }
    }

    public void testHandleRocketToMissionConnection_BothIdsNegative() {
        try {
            RelationHandlers.handleRocketToMissionConnection(-5, -10);
            fail("Should throw IllegalArgumentException for both negative IDs");
        } catch (IllegalArgumentException e) {
            assertEquals("Should have correct error message for negative IDs",
                    "ID cannot be less than 0", e.getMessage());
        } catch (Exception e) {
            fail("Should throw IllegalArgumentException, not " + e.getClass().getSimpleName());
        }
    }

    public void testHandleRocketToMissionConnection_RocketNotFound() {
        int missionId = testMission1.getId();
        int nonExistentRocketId = 999;

        try {
            RelationHandlers.handleRocketToMissionConnection(nonExistentRocketId, missionId);
            fail("Should throw Exception when rocket not found");
        } catch (IllegalArgumentException e) {
            fail("Should not throw IllegalArgumentException for missing rocket");
        } catch (Exception e) {
            assertEquals("Should have correct error message for missing objects",
                    "Could not find objects with given IDs", e.getMessage());
        }
    }

    public void testHandleRocketToMissionConnection_MissionNotFound() {
        int rocketId = testRocket1.getId();
        int nonExistentMissionId = 999;

        try {
            RelationHandlers.handleRocketToMissionConnection(rocketId, nonExistentMissionId);
            fail("Should throw Exception when mission not found");
        } catch (IllegalArgumentException e) {
            fail("Should not throw IllegalArgumentException for missing mission");
        } catch (Exception e) {
            assertEquals("Should have correct error message for missing objects",
                    "Could not find objects with given IDs", e.getMessage());
        }
    }

    public void testHandleRocketToMissionConnection_BothObjectsNotFound() {
        int nonExistentRocketId = 888;
        int nonExistentMissionId = 999;

        try {
            RelationHandlers.handleRocketToMissionConnection(nonExistentRocketId, nonExistentMissionId);
            fail("Should throw Exception when both objects not found");
        } catch (IllegalArgumentException e) {
            fail("Should not throw IllegalArgumentException for missing objects");
        } catch (Exception e) {
            assertEquals("Should have correct error message for missing objects",
                    "Could not find objects with given IDs", e.getMessage());
        }
    }

    public void testHandleRocketToMissionConnection_ZeroIds() {
        try {
            int rocketId = testRocket1.getId(); // Should be 0
            int missionId = testMission1.getId(); // Should be 0

            RelationHandlers.handleRocketToMissionConnection(rocketId, missionId);

            assertEquals("Connection should work with ID 0", testMission1, testRocket1.getMission());

        } catch (Exception e) {
            fail("Zero ID should be valid if objects exist: " + e.getMessage());
        }
    }

    public void testHandleRocketToMissionConnection_StateChangesOnly() {
        try {
            int rocketId = testRocket1.getId();
            int missionId = testMission1.getId();

            assertNull("Rocket should initially have no mission", testRocket1.getMission());
            assertEquals("Rocket should initially be ON_GROUND", RocketStatus.ON_GROUND, testRocket1.getStatus());

            RelationHandlers.handleRocketToMissionConnection(rocketId, missionId);

            assertNotNull("Rocket should now have a mission", testRocket1.getMission());
            assertEquals("Rocket status should change to IN_SPACE", RocketStatus.IN_SPACE, testRocket1.getStatus());
            assertEquals("Mission status should change to IN_PROGRESS", MissionStatus.IN_PROGRESS, testMission1.getStatus());

        } catch (Exception e) {
            fail("State changes should work correctly: " + e.getMessage());
        }
    }

    public void testHandleRocketToMissionConnection_MultipleRocketsToSameMission() {
        try {
            int rocket1Id = testRocket1.getId();
            int rocket2Id = testRocket2.getId();
            int missionId = testMission1.getId();

            RelationHandlers.handleRocketToMissionConnection(rocket1Id, missionId);

            RelationHandlers.handleRocketToMissionConnection(rocket2Id, missionId);

            assertEquals("Rocket1 should be connected to mission", testMission1, testRocket1.getMission());
            assertEquals("Rocket2 should be connected to mission", testMission1, testRocket2.getMission());

            assertTrue("Mission should contain rocket1", testMission1.getRocketSet().contains(testRocket1));
            assertTrue("Mission should contain rocket2", testMission1.getRocketSet().contains(testRocket2));
            assertEquals("Mission should contain exactly 2 rockets", 2, testMission1.getRocketSet().size());

        } catch (Exception e) {
            fail("Multiple rockets should be able to connect to same mission: " + e.getMessage());
        }
    }

    public void testHandleRocketToMissionConnection_ReconnectRocketToDifferentMission() {
        try {
            int rocketId = testRocket1.getId();
            int mission1Id = testMission1.getId();
            int mission2Id = testMission2.getId();

            RelationHandlers.handleRocketToMissionConnection(rocketId, mission1Id);
            assertEquals("Rocket should be connected to mission1", testMission1, testRocket1.getMission());

            RelationHandlers.handleRocketToMissionConnection(rocketId, mission2Id);
            assertEquals("Rocket should now be connected to mission2", testMission2, testRocket1.getMission());

            assertTrue("Mission2 should contain the rocket", testMission2.getRocketSet().contains(testRocket1));

        } catch (Exception e) {
            fail("Rocket reconnection should work: " + e.getMessage());
        }
    }

    public void testHandleRocketToMissionConnection_RepositoryStateUnchanged() {
        try {
            int initialRocketCount = RocketRepository.getRocketSet().size();
            int initialMissionCount = MissionRepository.getMissionSet().size();

            int rocketId = testRocket1.getId();
            int missionId = testMission1.getId();

            RelationHandlers.handleRocketToMissionConnection(rocketId, missionId);

            assertEquals("Rocket repository size should not change",
                    initialRocketCount, RocketRepository.getRocketSet().size());
            assertEquals("Mission repository size should not change",
                    initialMissionCount, MissionRepository.getMissionSet().size());

        } catch (Exception e) {
            fail("Repository state should remain unchanged: " + e.getMessage());
        }
    }
}