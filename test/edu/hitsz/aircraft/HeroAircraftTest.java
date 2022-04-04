package edu.hitsz.aircraft;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeroAircraftTest {

    private HeroAircraft heroAircraft;

    @BeforeEach
    void setUp() {
        heroAircraft = HeroAircraft.getInstance();
    }

    @AfterEach
    void tearDown() {
        heroAircraft = null;
    }

    @Test
    void getInstance() {
        System.out.println("**-- Test getInstance method executed --**");
        assertEquals(heroAircraft,HeroAircraft.getInstance());
    }

    @Test
    void setHp() {
        System.out.println("**-- Test setHp method executed --**");
        heroAircraft.setHp(90);
        assertEquals(90,heroAircraft.getHp());
        heroAircraft.setHp(110);
        assertEquals(100,heroAircraft.getHp());
    }
}