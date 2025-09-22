package duck;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;
import java.util.Calendar;

public class EvolutionTest {

    @Test
    public void testDecideStage_EggStage() {
        Date currentDate = new Date();
        // EGGステージ内にとどまる場合（上限3未満）
        assertEquals(Evolution.Stage.EGG, Evolution.decideStage(0, Evolution.Stage.EGG, currentDate));
        assertEquals(Evolution.Stage.EGG, Evolution.decideStage(1, Evolution.Stage.EGG, currentDate));
        assertEquals(Evolution.Stage.EGG, Evolution.decideStage(2, Evolution.Stage.EGG, currentDate));
        // EGGステージから次のステージへの進化（上限3以上）
        assertEquals(Evolution.Stage.CRACKED_EGG, Evolution.decideStage(3, Evolution.Stage.EGG, currentDate));
    }

    @Test
    public void testDecideStage_CrackedEggStage() {
        Date currentDate = new Date();
        // CRACKED_EGGステージ内にとどまる場合（上限5未満）
        assertEquals(Evolution.Stage.CRACKED_EGG, Evolution.decideStage(4, Evolution.Stage.CRACKED_EGG, currentDate));
        assertEquals(Evolution.Stage.CRACKED_EGG, Evolution.decideStage(4, Evolution.Stage.CRACKED_EGG, currentDate));
        // CRACKED_EGGステージから次のステージへの進化（上限5以上）
        assertEquals(Evolution.Stage.HATCHING, Evolution.decideStage(5, Evolution.Stage.CRACKED_EGG, currentDate));
    }

    @Test
    public void testDecideStage_HatchingStage() {
        Date currentDate = new Date();
        // HATCHINGステージ内にとどまる場合（上限8未満）
        assertEquals(Evolution.Stage.HATCHING, Evolution.decideStage(6, Evolution.Stage.HATCHING, currentDate));
        assertEquals(Evolution.Stage.HATCHING, Evolution.decideStage(7, Evolution.Stage.HATCHING, currentDate));
        // HATCHINGステージから次のステージへの進化（上限8以上）
        assertEquals(Evolution.Stage.DUCKLING, Evolution.decideStage(8, Evolution.Stage.HATCHING, currentDate));
    }

    @Test
    public void testDecideStage_DucklingStage() {
        Date currentDate = new Date();
        // DUCKLINGステージ内にとどまる場合（上限13未満）
        assertEquals(Evolution.Stage.DUCKLING, Evolution.decideStage(10, Evolution.Stage.DUCKLING, currentDate));
        assertEquals(Evolution.Stage.DUCKLING, Evolution.decideStage(12, Evolution.Stage.DUCKLING, currentDate));
        // DUCKLINGステージから次のステージへの進化（上限13以上）
        assertEquals(Evolution.Stage.MATCHING, Evolution.decideStage(13, Evolution.Stage.DUCKLING, currentDate));
    }

    @Test
    public void testDecideStage_MatchingStage() {
        Date currentDate = new Date();
        // MATCHINGステージ内にとどまる場合（上限21未満）
        assertEquals(Evolution.Stage.MATCHING, Evolution.decideStage(15, Evolution.Stage.MATCHING, currentDate));
        assertEquals(Evolution.Stage.MATCHING, Evolution.decideStage(20, Evolution.Stage.MATCHING, currentDate));
        // MATCHINGステージから次のステージへの進化（上限21以上）
        assertEquals(Evolution.Stage.MARRIED, Evolution.decideStage(21, Evolution.Stage.MATCHING, currentDate));
    }

    @Test
    public void testDecideStage_MarriedStage() {
        Date currentDate = new Date();
        // MARRIEDステージ内にとどまる場合（上限34未満）
        assertEquals(Evolution.Stage.MARRIED, Evolution.decideStage(25, Evolution.Stage.MARRIED, currentDate));
        assertEquals(Evolution.Stage.MARRIED, Evolution.decideStage(33, Evolution.Stage.MARRIED, currentDate));
        // MARRIEDステージから次のステージへの進化（上限34以上）
        assertEquals(Evolution.Stage.BIRTH, Evolution.decideStage(34, Evolution.Stage.MARRIED, currentDate));
    }

    @Test
    public void testDecideStage_BirthStage() {
        Date currentDate = new Date();
        // BIRTHステージ内にとどまる場合（上限55未満）
        assertEquals(Evolution.Stage.BIRTH, Evolution.decideStage(40, Evolution.Stage.BIRTH, currentDate));
        assertEquals(Evolution.Stage.BIRTH, Evolution.decideStage(54, Evolution.Stage.BIRTH, currentDate));
        // BIRTHステージから次のステージへの進化（上限55以上）は、ランダムなので結果は予測不可
        // テストは省略
    }

    @Test
    public void testDecideStage_SpecialStages() {
        Date currentDate = new Date();
        // 特殊ステージは上限がnullなので現在のステージを維持
        assertEquals(Evolution.Stage.SICKLY, Evolution.decideStage(60, Evolution.Stage.SICKLY, currentDate));
        assertEquals(Evolution.Stage.INJURED, Evolution.decideStage(70, Evolution.Stage.INJURED, currentDate));
        assertEquals(Evolution.Stage.DEAD, Evolution.decideStage(79, Evolution.Stage.DEAD, currentDate));
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

    @Test
    public void testHasDaysPassed() {
        Calendar cal = Calendar.getInstance();
        Date sixDaysAgo = cal.getTime();
        cal.add(Calendar.DAY_OF_MONTH, -6);
        sixDaysAgo = cal.getTime();
        
        Date fourDaysAgo = cal.getTime();
        cal.add(Calendar.DAY_OF_MONTH, 2);
        fourDaysAgo = cal.getTime();
        
        // 6日前の日付で5日経過テスト
        assertTrue(Evolution.hasFiveDaysPassed(sixDaysAgo));
        assertTrue(Evolution.hasSevenDaysPassed(sixDaysAgo));
        
        // 4日前の日付で5日経過テスト
        assertFalse(Evolution.hasFiveDaysPassed(fourDaysAgo));
        assertFalse(Evolution.hasSevenDaysPassed(fourDaysAgo));
        
        // nullの日付テスト
        assertFalse(Evolution.hasFiveDaysPassed(null));
        assertFalse(Evolution.hasSevenDaysPassed(null));
    }

    @Test
    public void testDecideStageWithDate_FiveDaysPassed() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -6); // 6日前
        Date sixDaysAgo = cal.getTime();
        
        // BIRTHステージで5日経過している場合、SICKLYになる可能性がある
        Evolution.Stage result = Evolution.decideStage(60, Evolution.Stage.BIRTH, sixDaysAgo);
        assertTrue(result == Evolution.Stage.SICKLY || result == Evolution.Stage.BIRTH);
    }

    @Test
    public void testDecideStageWithDate_SevenDaysPassed() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -8); // 8日前
        Date eightDaysAgo = cal.getTime();
        
        // BIRTHまたはSICKLYステージで7日経過している場合、INJUREDになる可能性がある
        Evolution.Stage result1 = Evolution.decideStage(60, Evolution.Stage.BIRTH, eightDaysAgo);
        Evolution.Stage result2 = Evolution.decideStage(60, Evolution.Stage.SICKLY, eightDaysAgo);
        
        // 結果はランダムなので、有効なステージであることを確認
        assertTrue(result1 == Evolution.Stage.INJURED || result1 == Evolution.Stage.BIRTH);
        assertTrue(result2 == Evolution.Stage.INJURED || result2 == Evolution.Stage.SICKLY);
    }

    @Test
    public void testDecideStageWithDate_NoDateProvided() {
        // 日付がnullの場合は現在のステージを維持（5日/7日経過チェックはスキップ）
        assertEquals(Evolution.Stage.EGG, Evolution.decideStage(2, Evolution.Stage.EGG, null));
        assertEquals(Evolution.Stage.CRACKED_EGG, Evolution.decideStage(3, Evolution.Stage.EGG, null));
    }
}
