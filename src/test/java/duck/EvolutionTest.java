package duck;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EvolutionTest {

    @Test
    public void testDecideStage_EggStage() {
        // EGGステージ内にとどまる場合（上限3以下）
        assertEquals(Evolution.Stage.EGG, Evolution.decideStage(0, Evolution.Stage.EGG));
        assertEquals(Evolution.Stage.EGG, Evolution.decideStage(1, Evolution.Stage.EGG));
        assertEquals(Evolution.Stage.EGG, Evolution.decideStage(2, Evolution.Stage.EGG));
        assertEquals(Evolution.Stage.EGG, Evolution.decideStage(3, Evolution.Stage.EGG));
        // EGGステージから次のステージへの進化（上限3を超える）
        assertEquals(Evolution.Stage.CRACKED_EGG, Evolution.decideStage(4, Evolution.Stage.EGG));
    }

    @Test
    public void testDecideStage_CrackedEggStage() {
        // CRACKED_EGGステージ内にとどまる場合（上限5以下）
        assertEquals(Evolution.Stage.CRACKED_EGG, Evolution.decideStage(3, Evolution.Stage.CRACKED_EGG));
        assertEquals(Evolution.Stage.CRACKED_EGG, Evolution.decideStage(4, Evolution.Stage.CRACKED_EGG));
        assertEquals(Evolution.Stage.CRACKED_EGG, Evolution.decideStage(5, Evolution.Stage.CRACKED_EGG));
        // CRACKED_EGGステージから次のステージへの進化（上限5を超える）
        assertEquals(Evolution.Stage.HATCHING, Evolution.decideStage(6, Evolution.Stage.CRACKED_EGG));
    }

    @Test
    public void testDecideStage_HatchingStage() {
        // HATCHINGステージ内にとどまる場合（上限8以下）
        assertEquals(Evolution.Stage.HATCHING, Evolution.decideStage(6, Evolution.Stage.HATCHING));
        assertEquals(Evolution.Stage.HATCHING, Evolution.decideStage(7, Evolution.Stage.HATCHING));
        assertEquals(Evolution.Stage.HATCHING, Evolution.decideStage(8, Evolution.Stage.HATCHING));
        // HATCHINGステージから次のステージへの進化（上限8を超える）
        assertEquals(Evolution.Stage.DUCKLING, Evolution.decideStage(9, Evolution.Stage.HATCHING));
    }

    @Test
    public void testDecideStage_DucklingStage() {
        // DUCKLINGステージ内にとどまる場合（上限13以下）
        assertEquals(Evolution.Stage.DUCKLING, Evolution.decideStage(10, Evolution.Stage.DUCKLING));
        assertEquals(Evolution.Stage.DUCKLING, Evolution.decideStage(12, Evolution.Stage.DUCKLING));
        assertEquals(Evolution.Stage.DUCKLING, Evolution.decideStage(13, Evolution.Stage.DUCKLING));
        // DUCKLINGステージから次のステージへの進化（上限13を超える）
        assertEquals(Evolution.Stage.MATCHING, Evolution.decideStage(14, Evolution.Stage.DUCKLING));
    }

    @Test
    public void testDecideStage_MatchingStage() {
        // MATCHINGステージ内にとどまる場合（上限21以下）
        assertEquals(Evolution.Stage.MATCHING, Evolution.decideStage(15, Evolution.Stage.MATCHING));
        assertEquals(Evolution.Stage.MATCHING, Evolution.decideStage(20, Evolution.Stage.MATCHING));
        assertEquals(Evolution.Stage.MATCHING, Evolution.decideStage(21, Evolution.Stage.MATCHING));
        // MATCHINGステージから次のステージへの進化（上限21を超える）
        assertEquals(Evolution.Stage.MARRIED, Evolution.decideStage(22, Evolution.Stage.MATCHING));
    }

    @Test
    public void testDecideStage_MarriedStage() {
        // MARRIEDステージ内にとどまる場合（上限34以下）
        assertEquals(Evolution.Stage.MARRIED, Evolution.decideStage(25, Evolution.Stage.MARRIED));
        assertEquals(Evolution.Stage.MARRIED, Evolution.decideStage(33, Evolution.Stage.MARRIED));
        assertEquals(Evolution.Stage.MARRIED, Evolution.decideStage(34, Evolution.Stage.MARRIED));
        // MARRIEDステージから次のステージへの進化（上限34を超える）
        assertEquals(Evolution.Stage.BIRTH, Evolution.decideStage(35, Evolution.Stage.MARRIED));
    }

    @Test
    public void testDecideStage_BirthStage() {
        // BIRTHステージ内にとどまる場合（上限55以下）
        assertEquals(Evolution.Stage.BIRTH, Evolution.decideStage(40, Evolution.Stage.BIRTH));
        assertEquals(Evolution.Stage.BIRTH, Evolution.decideStage(54, Evolution.Stage.BIRTH));
        assertEquals(Evolution.Stage.BIRTH, Evolution.decideStage(55, Evolution.Stage.BIRTH));
        // BIRTHステージから次のステージへの進化（上限55を超える）は、ランダムなので結果は予測不可
        // テストは省略
    }

    @Test
    public void testDecideStage_SpecialStages() {
        // 特殊ステージは上限がnullなので現在のステージを維持
        assertEquals(Evolution.Stage.SICKLY, Evolution.decideStage(60, Evolution.Stage.SICKLY));
        assertEquals(Evolution.Stage.INJURED, Evolution.decideStage(70, Evolution.Stage.INJURED));
        assertEquals(Evolution.Stage.DEAD, Evolution.decideStage(79, Evolution.Stage.DEAD));
    }

    @Test
    public void testStageLabel() {
        assertEquals("Egg", Evolution.stageLabel(Evolution.Stage.EGG));
        assertEquals("Cracked Egg", Evolution.stageLabel(Evolution.Stage.CRACKED_EGG));
        assertEquals("Hatching", Evolution.stageLabel(Evolution.Stage.HATCHING));
        assertEquals("Duckling", Evolution.stageLabel(Evolution.Stage.DUCKLING));
        assertEquals("Matching", Evolution.stageLabel(Evolution.Stage.MATCHING));
        assertEquals("Married", Evolution.stageLabel(Evolution.Stage.MARRIED));
        assertEquals("Nesting", Evolution.stageLabel(Evolution.Stage.BIRTH));
        assertEquals("Sickly", Evolution.stageLabel(Evolution.Stage.SICKLY));
        assertEquals("Injured", Evolution.stageLabel(Evolution.Stage.INJURED));
        assertEquals("Deceased", Evolution.stageLabel(Evolution.Stage.DEAD));
    }
}
