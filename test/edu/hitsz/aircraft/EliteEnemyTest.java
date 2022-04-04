package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.prop.AbstractProp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EliteEnemyTest {

    private EliteEnemyFactory eliteEnemyFactory = new EliteEnemyFactory();
    private EliteEnemy eliteEnemy;

    @BeforeEach
    void setUp() {
        eliteEnemy = (EliteEnemy) eliteEnemyFactory.createAircraft();
    }

    @AfterEach
    void tearDown() {
        eliteEnemy = null;
    }

    @Test
    void forward() {
        System.out.println("**-- Test forward method executed --**");
        eliteEnemy.setLocation(eliteEnemy.getLocationX(), Main.WINDOW_HEIGHT + 1);
        eliteEnemy.forward();
        assertTrue(eliteEnemy.notValid());
    }

    @Test
    void createProp() {
        System.out.println("**-- Test createProp method executed --**");
        assertTrue(eliteEnemy.createProp() instanceof AbstractProp);
    }
}