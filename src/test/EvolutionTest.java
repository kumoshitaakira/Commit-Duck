package test;

import main.Evolution;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EvolutionTest {

    @Test
    public void testGetStageForCommits_EggStage() {
        assertEquals(Evolution.Stage.EGG, Evolution.getStageForCommits(0));
        assertEquals(Evolution.Stage.EGG, Evolution.getStageForCommits(2));
        assertEquals(Evolution.Stage.EGG, Evolution.getStageForCommits(4));
    }

    @Test
    public void testGetStageForCommits_DucklingStage() {
        assertEquals(Evolution.Stage.DUCKLING, Evolution.getStageForCommits(5));
        assertEquals(Evolution.Stage.DUCKLING, Evolution.getStageForCommits(7));
        assertEquals(Evolution.Stage.DUCKLING, Evolution.getStageForCommits(9));
    }

    @Test
    public void testGetStageForCommits_TeenStage() {
        assertEquals(Evolution.Stage.TEEN, Evolution.getStageForCommits(10));
        assertEquals(Evolution.Stage.TEEN, Evolution.getStageForCommits(15));
        assertEquals(Evolution.Stage.TEEN, Evolution.getStageForCommits(24));
    }

    @Test
    public void testGetStageForCommits_AdultStage() {
        assertEquals(Evolution.Stage.ADULT, Evolution.getStageForCommits(25));
        assertEquals(Evolution.Stage.ADULT, Evolution.getStageForCommits(35));
        assertEquals(Evolution.Stage.ADULT, Evolution.getStageForCommits(49));
    }

    @Test
    public void testGetStageForCommits_LegendStage() {
        assertEquals(Evolution.Stage.LEGEND, Evolution.getStageForCommits(50));
        assertEquals(Evolution.Stage.LEGEND, Evolution.getStageForCommits(100));
        assertEquals(Evolution.Stage.LEGEND, Evolution.getStageForCommits(1000));
    }

    @Test
    public void testGetStageNameForCommits() {
        assertEquals("EGG", Evolution.getStageNameForCommits(3));
        assertEquals("DUCKLING", Evolution.getStageNameForCommits(7));
        assertEquals("TEEN", Evolution.getStageNameForCommits(15));
        assertEquals("ADULT", Evolution.getStageNameForCommits(35));
        assertEquals("LEGEND", Evolution.getStageNameForCommits(100));
    }

    @Test
    public void testGetEmojiForCommits() {
        assertEquals("🥚", Evolution.getEmojiForCommits(3));
        assertEquals("🐣", Evolution.getEmojiForCommits(7));
        assertEquals("🦆", Evolution.getEmojiForCommits(15));
        assertEquals("🦆", Evolution.getEmojiForCommits(35));
        assertEquals("🦆✨", Evolution.getEmojiForCommits(100));
    }

    @Test
    public void testStageEnumProperties() {
        Evolution.Stage eggStage = Evolution.Stage.EGG;
        assertEquals("🥚", eggStage.getEmoji());
        assertEquals(0, eggStage.getMinCommits());
        assertEquals(4, eggStage.getMaxCommits());

        Evolution.Stage legendStage = Evolution.Stage.LEGEND;
        assertEquals("🦆✨", legendStage.getEmoji());
        assertEquals(50, legendStage.getMinCommits());
        assertEquals(Integer.MAX_VALUE, legendStage.getMaxCommits());
    }
}
