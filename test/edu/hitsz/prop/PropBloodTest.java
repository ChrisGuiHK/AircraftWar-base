package edu.hitsz.prop;

import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.aircraft.EliteEnemyFactory;
import edu.hitsz.aircraft.HeroAircraft;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PropBloodTest {

    private PropBloodFactory propBloodFactory = new PropBloodFactory();
    private EliteEnemy eliteEnemy = (EliteEnemy) new EliteEnemyFactory().createAircraft();
    private HeroAircraft heroAircraft = HeroAircraft.getInstance();
    private PropBlood propBlood;

    @BeforeEach
    void setUp() {
        propBlood = (PropBlood) propBloodFactory.createProp(eliteEnemy);
    }

    @AfterEach
    void tearDown() {
        propBlood = null;
    }

    @Test
    void crash() {
        System.out.println("**-- Test crash method executed --**");
        assertAll(()->assertTrue(propBlood.crash(eliteEnemy)),
                ()->assertFalse(propBlood.crash(heroAircraft)));
    }

    @Test
    void effect() {
        System.out.println("**-- Test effect method executed --**");
        heroAircraft.setHp(70);
        propBlood.effect();
        assertEquals(80,heroAircraft.getHp());
    }
}