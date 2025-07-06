package org.tpuchal;

import junit.framework.TestCase;
import org.tpuchal.model.Mission;
import org.tpuchal.model.Rocket;
import org.tpuchal.repository.MissionRepository;
import org.tpuchal.repository.RocketRepository;

import java.lang.reflect.Field;
import java.util.Set;

public class MissionRepositoryTest extends TestCase {
    protected void setUp() throws Exception {
        super.setUp();
        clearMissionRepository();
    }

    /**
     * Helper method to clear the static rocket repository using reflection
     */
    private void clearMissionRepository() throws Exception {
        Field missionSetField = MissionRepository.class.getDeclaredField("missionSet");
        missionSetField.setAccessible(true);
        Set<Mission> missionSet = (Set<Mission>) missionSetField.get(null);
        missionSet.clear();
    }

    /**
     * Helper method to reset the static ID counter in Mission class
     */
    private void resetIdCounter() throws Exception {
        Field idCounterField = Mission.class.getDeclaredField("idCounter");
        idCounterField.setAccessible(true);
        idCounterField.setInt(null, 0);
    }

    public void testAddMission_NewMission() {
        try {
            clearMissionRepository();
            Mission testMission = new Mission("Test Mission");

            Set<Mission> missions = MissionRepository.getMissionSet();
            assertEquals("Repository should contain exactly 1 mission", 1, missions.size());
            assertTrue("Repository should contain the added mission", missions.contains(testMission));
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

    public void testAddMission_DuplicateMission() {
        try {
            clearMissionRepository();
            Mission testMission = new Mission("Test Mission");

            MissionRepository.addMission(testMission);

            Set<Mission> missions = MissionRepository.getMissionSet();
            assertEquals("Repository should contain exactly 1 mission (no duplicates)", 1, missions.size());
            assertTrue("Repository should contain the mission", missions.contains(testMission));

        } catch (Exception e) {
            assertEquals("This mission already exists", e.getMessage());
        }
    }

    public void testAddMission_MultipleUniqueRockets() {
        try {
            clearMissionRepository();
            Mission mission_a = new Mission("Mission A");
            Mission mission_b = new Mission("Mission B");
            Mission mission_c = new Mission("Mission C");

            Set<Mission> missions = MissionRepository.getMissionSet();
            assertEquals("Repository should contain exactly 3 missions", 3, missions.size());
            assertTrue("Repository should contain mission A", missions.contains(mission_a));
            assertTrue("Repository should contain mission B", missions.contains(mission_b));
            assertTrue("Repository should contain mission C", missions.contains(mission_c));
        } catch (Exception e) {
            fail("Exception occured: " + e.getMessage());
        }
    }

    public void testAddMission_NullMission_ThrowsException() {
        try {
            clearMissionRepository();

            try {
                MissionRepository.addMission(null);
                fail("Adding null mission should throw IllegalArgumentException");
            } catch (IllegalArgumentException e) {
                assertEquals("Mission cannot be null", e.getMessage());
            }

            Set<Mission> missions = MissionRepository.getMissionSet();
            assertEquals("Repository should remain empty after failed null addition", 0, missions.size());
        } catch (Exception e) {
            fail("Unexpected exception occured: " + e.getMessage());
        }
    }

    public void testDeleteRocket_ExistingRocket() {
        try {
            clearMissionRepository();
            Mission testMission = new Mission("Test Mission");
            assertEquals("Repository should have 1 rocket before deletion", 1, MissionRepository.getMissionSet().size());

            MissionRepository.deleteMission(testMission);

            Set<Mission> missions = MissionRepository.getMissionSet();
            assertEquals("Repository should be empty after deletion", 0, missions.size());
            assertFalse("Repository should not contain the deleted mission", missions.contains(testMission));
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

    public void testDeleteMission_NonExistentMission() {
        try {
            clearMissionRepository();
            Mission existingMission = new Mission("Existing Mission");
            Mission nonExistentMission = new Mission("Non Existent Mission");
            MissionRepository.deleteMission(nonExistentMission);

            assertEquals("Repository should have one mission", 1, MissionRepository.getMissionSet().size());

            MissionRepository.deleteMission(nonExistentMission);

            Set<Mission> missions = MissionRepository.getMissionSet();
            assertEquals("Repository should still have 1 rocket", 1, missions.size());
            assertEquals("Repository should still contain existing rocket", missions.contains(existingMission));
        } catch (Exception e) {
            assertEquals("Cannot delete. No such mission", e.getMessage());
        }
    }

    public void testDeleteRocket_FromEmptyRepository() {
        try {
            clearMissionRepository();
            Mission testMission = new Mission("Test Mission");
            MissionRepository.deleteMission(testMission);

            Set<Mission> Missions = MissionRepository.getMissionSet();
            assertEquals("Repository should remain empty", 0, Missions.size());
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

    public void testDeleteMission_NullMission() {
        try {
            clearMissionRepository();
            Mission testMission = new Mission("Test Mission");

            assertEquals("Repository should contain 1 Mission", 1, MissionRepository.getMissionSet().size());

            MissionRepository.deleteMission(null);

            Set<Mission> Missions = MissionRepository.getMissionSet();
            assertEquals("Repository should still contain 1 Mission after trying to delete null", 1, Missions.size());
            assertTrue("Repository should still contain the test Mission", Missions.contains(testMission));
        } catch (Exception e) {
            assertEquals("Cannot delete. No such mission", e.getMessage());
        }
    }

    public void testGetMissionSet_EmptyRepository() {
        try {
            clearMissionRepository();

            Set<Mission> Missions = MissionRepository.getMissionSet();

            assertNotNull("getMissionSet() should never return null", Missions);
            assertEquals("Empty repository should return empty set", 0, Missions.size());
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

    public void testGetMissionSet_WithMissions() {
        try {
            clearMissionRepository();
            Mission Mission_x = new Mission("Mission X");
            Mission Mission_y = new Mission("Mission Y");

            Set<Mission> Missions = MissionRepository.getMissionSet();

            assertNotNull("getMissionSet() should never return null", Missions);
            assertEquals("Repository should contain 2 Missions", 2, Missions.size());
            assertTrue("Set should contain Mission X", Missions.contains(Mission_x));
            assertTrue("Set should contain Mission Y", Missions.contains(Mission_y));
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

    public void testRepositoryIntegration_AddDeleteCycle() {
        try {
            clearMissionRepository();
            Mission Mission = new Mission("Test Mission");
            clearMissionRepository();

            MissionRepository.addMission(Mission);
            assertEquals("After adding: repository should have 1 Mission", 1, MissionRepository.getMissionSet().size());

            MissionRepository.deleteMission(Mission);
            assertEquals("After deleting: repository should be empty", 0, MissionRepository.getMissionSet().size());

            MissionRepository.addMission(Mission);
            assertEquals("After re-adding: repository should have 1 Mission again", 1, MissionRepository.getMissionSet().size());
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

    public void testAddMission_MultipleNullAttempts() {
        try {
            clearMissionRepository();

            for (int i = 0; i < 3; i++) {
                try {
                    MissionRepository.addMission(null);
                    fail("Adding null Mission should always throw IllegalArgumentException (attempt " + (i + 1) + ")");
                } catch (IllegalArgumentException e) {
                    assertEquals("Exception message should be correct on attempt " + (i + 1),
                            "Mission cannot be null", e.getMessage());
                }
            }

            Set<Mission> Missions = MissionRepository.getMissionSet();
            assertEquals("Repository should remain empty after multiple failed null additions", 0, Missions.size());
        } catch (Exception e) {
            fail("Unexpected exception occurred: " + e.getMessage());
        }
    }

    public void testAddMission_NullDoesNotAffectValidOperations() {
        try {
            clearMissionRepository();
            Mission validMission = new Mission("Valid Mission");

            assertEquals("Repository should contain 1 Mission", 1, MissionRepository.getMissionSet().size());

            try {
                MissionRepository.addMission(null);
                fail("Adding null should throw exception");
            } catch (IllegalArgumentException e) {
            }

            Set<Mission> Missions = MissionRepository.getMissionSet();
            assertEquals("Repository should still contain 1 Mission after failed null addition", 1, Missions.size());
            assertTrue("Repository should still contain the valid Mission", Missions.contains(validMission));

            Mission anotherMission = new Mission("Another Mission");
            clearMissionRepository();
            MissionRepository.addMission(validMission);
            MissionRepository.addMission(anotherMission);
            assertEquals("Repository should contain 2 Missions after adding another valid Mission", 2, MissionRepository.getMissionSet().size());

        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

    public void testDeleteMission_DeleteCorrectlyNullifiesRocketMissions() {
        try {
            clearMissionRepository();
            Mission testMission = new Mission("Test Mission");
            Rocket testRocket = new Rocket("Test Rocket");

            testMission.getRocketSet().add(testRocket);
            testRocket.setMission(testMission);

            assertEquals("Repository should contain 1 Mission", 1, MissionRepository.getMissionSet().size());
            assertEquals("Mission's rockets should contain 1 Rocket", 1, testMission.getRocketSet().size());

            MissionRepository.deleteMission(testMission);
            assertEquals("Repository should be empty", 0, MissionRepository.getMissionSet().size());
            assertEquals("Rocket's mission should be null", null, testRocket.getMission());

        } catch (Exception e) {
            fail("Exception occured: " + e.getMessage());
        }
    }
}
