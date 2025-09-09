package test;

import main.Evolution;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EvolutionTest {

    @Test
    public void testDecideStage_EggStage() {
        assertEquals(Evolution.Stage.EGG, Evolution.decideStage(0));
        assertEquals(Evolution.Stage.EGG, Evolution.decideStage(2));
        assertEquals(Evolution.Stage.EGG, Evolution.decideStage(4));
    }

    @Test
    public void testDecideStage_DucklingStage() {
        assertEquals(Evolution.Stage.DUCKLING, Evolution.decideStage(5));
        assertEquals(Evolution.Stage.DUCKLING, Evolution.decideStage(7));
        assertEquals(Evolution.Stage.DUCKLING, Evolution.decideStage(9));
    }

    @Test
    public void testDecideStage_TeenStage() {
        assertEquals(Evolution.Stage.TEEN, Evolution.decideStage(10));
        assertEquals(Evolution.Stage.TEEN, Evolution.decideStage(15));
        assertEquals(Evolution.Stage.TEEN, Evolution.decideStage(24));
    }

    @Test
    public void testDecideStage_AdultStage() {
        assertEquals(Evolution.Stage.ADULT, Evolution.decideStage(25));
        assertEquals(Evolution.Stage.ADULT, Evolution.decideStage(35));
        assertEquals(Evolution.Stage.ADULT, Evolution.decideStage(49));
    }

    @Test
    public void testDecideStage_LegendStage() {
        assertEquals(Evolution.Stage.LEGEND, Evolution.decideStage(50));
        assertEquals(Evolution.Stage.LEGEND, Evolution.decideStage(100));
        assertEquals(Evolution.Stage.LEGEND, Evolution.decideStage(1000));
    }

    @Test
    public void testStageLabel() {
        assertEquals("Egg", Evolution.stageLabel(Evolution.Stage.EGG));
        assertEquals("Duckling", Evolution.stageLabel(Evolution.Stage.DUCKLING));
        assertEquals("Teen", Evolution.stageLabel(Evolution.Stage.TEEN));
        assertEquals("Adult", Evolution.stageLabel(Evolution.Stage.ADULT));
        assertEquals("Legend", Evolution.stageLabel(Evolution.Stage.LEGEND));
    }

    @Test
    public void testAscii() {
        // ASCIIアートが返されることを確認（具体的な内容は検証しない）
        assertNotNull(Evolution.ascii(Evolution.Stage.EGG));
        assertNotNull(Evolution.ascii(Evolution.Stage.DUCKLING));
        assertNotNull(Evolution.ascii(Evolution.Stage.TEEN));
        assertNotNull(Evolution.ascii(Evolution.Stage.ADULT));
        assertNotNull(Evolution.ascii(Evolution.Stage.LEGEND));
    }
}
