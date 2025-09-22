package duck;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;
import java.util.Calendar;

public class EvolutionTest {

    @Test
    public void testDecideStage_EggStage() {
        Date currentDate = new Date();
        // 現在時刻なので日付判定に該当せず、コミット数ベースの進化が動作
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
        // 現在時刻なので日付判定に該当せず、コミット数ベースの進化が動作
        // CRACKED_EGGステージ内にとどまる場合（上限5未満）
        assertEquals(Evolution.Stage.CRACKED_EGG, Evolution.decideStage(4, Evolution.Stage.CRACKED_EGG, currentDate));
        assertEquals(Evolution.Stage.CRACKED_EGG, Evolution.decideStage(4, Evolution.Stage.CRACKED_EGG, currentDate));
        // CRACKED_EGGステージから次のステージへの進化（上限5以上）
        assertEquals(Evolution.Stage.HATCHING, Evolution.decideStage(5, Evolution.Stage.CRACKED_EGG, currentDate));
    }

    @Test
    public void testDecideStage_HatchingStage() {
        Date currentDate = new Date();
        // 現在時刻なので日付判定に該当せず、コミット数ベースの進化が動作
        // HATCHINGステージ内にとどまる場合（上限8未満）
        assertEquals(Evolution.Stage.HATCHING, Evolution.decideStage(6, Evolution.Stage.HATCHING, currentDate));
        assertEquals(Evolution.Stage.HATCHING, Evolution.decideStage(7, Evolution.Stage.HATCHING, currentDate));
        // HATCHINGステージから次のステージへの進化（上限8以上）
        assertEquals(Evolution.Stage.DUCKLING, Evolution.decideStage(8, Evolution.Stage.HATCHING, currentDate));
    }

    @Test
    public void testDecideStage_DucklingStage() {
        Date currentDate = new Date();
        // 現在時刻なので日付判定に該当せず、コミット数ベースの進化が動作
        // DUCKLINGステージ内にとどまる場合（上限13未満）
        assertEquals(Evolution.Stage.DUCKLING, Evolution.decideStage(10, Evolution.Stage.DUCKLING, currentDate));
        assertEquals(Evolution.Stage.DUCKLING, Evolution.decideStage(12, Evolution.Stage.DUCKLING, currentDate));
        // DUCKLINGステージから次のステージへの進化（上限13以上）
        assertEquals(Evolution.Stage.MATCHING, Evolution.decideStage(13, Evolution.Stage.DUCKLING, currentDate));
    }

    @Test
    public void testDecideStage_MatchingStage() {
        Date currentDate = new Date();
        // 現在時刻なので日付判定に該当せず、コミット数ベースの進化が動作
        // MATCHINGステージ内にとどまる場合（上限21未満）
        assertEquals(Evolution.Stage.MATCHING, Evolution.decideStage(15, Evolution.Stage.MATCHING, currentDate));
        assertEquals(Evolution.Stage.MATCHING, Evolution.decideStage(20, Evolution.Stage.MATCHING, currentDate));
        // MATCHINGステージから次のステージへの進化（上限21以上）
        assertEquals(Evolution.Stage.MARRIED, Evolution.decideStage(21, Evolution.Stage.MATCHING, currentDate));
    }

    @Test
    public void testDecideStage_MarriedStage() {
        Date currentDate = new Date();
        // 現在時刻なので日付判定に該当せず、コミット数ベースの進化が動作
        // MARRIEDステージ内にとどまる場合（上限34未満）
        assertEquals(Evolution.Stage.MARRIED, Evolution.decideStage(25, Evolution.Stage.MARRIED, currentDate));
        assertEquals(Evolution.Stage.MARRIED, Evolution.decideStage(33, Evolution.Stage.MARRIED, currentDate));
        // MARRIEDステージから次のステージへの進化（上限34以上）
        assertEquals(Evolution.Stage.BIRTH, Evolution.decideStage(34, Evolution.Stage.MARRIED, currentDate));
    }

    @Test
    public void testDecideStage_BirthStage() {
        Date currentDate = new Date();
        // 現在時刻なので日付判定に該当せず、コミット数ベースの進化が動作
        // BIRTHステージ内にとどまる場合（上限55未満）
        assertEquals(Evolution.Stage.BIRTH, Evolution.decideStage(40, Evolution.Stage.BIRTH, currentDate));
        assertEquals(Evolution.Stage.BIRTH, Evolution.decideStage(54, Evolution.Stage.BIRTH, currentDate));
        // BIRTHステージから次のステージへの進化（上限55以上）は、ランダムなので結果は予測不可
        // テストは省略
    }

    @Test
    public void testDecideStage_SpecialStages() {
        Date currentDate = new Date();
        // 現在時刻なので日付判定に該当せず、コミット数ベースの進化が動作
        // 特殊ステージは上限がnullなので現在のステージを維持

        // SICKLYステージはランダムでSICKLYまたはINJUREDを返す
        Evolution.Stage sicklyResult = Evolution.decideStage(60, Evolution.Stage.SICKLY, currentDate);
        assertTrue(sicklyResult == Evolution.Stage.SICKLY || sicklyResult == Evolution.Stage.INJURED,
                "SICKLYステージはSICKLYまたはINJUREDを返すべき");

        // INJUREDステージはランダムでSICKLYまたはINJUREDを返す
        Evolution.Stage injuredResult = Evolution.decideStage(70, Evolution.Stage.INJURED, currentDate);
        assertTrue(injuredResult == Evolution.Stage.SICKLY || injuredResult == Evolution.Stage.INJURED,
                "INJUREDステージはSICKLYまたはINJUREDを返すべき");

        // DEADステージはDEADを維持
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

        // 6日前の日付を作成（確実に6日前）
        cal.add(Calendar.DAY_OF_MONTH, -6);
        Date sixDaysAgo = cal.getTime();

        // 4日前の日付を作成（確実に4日前）
        cal.add(Calendar.DAY_OF_MONTH, 2); // -6 + 2 = -4日前
        Date fourDaysAgo = cal.getTime();

        // デバッグ用：実際の日数差を確認
        long diffSixDays = (System.currentTimeMillis() - sixDaysAgo.getTime()) / (24 * 60 * 60 * 1000);
        long diffFourDays = (System.currentTimeMillis() - fourDaysAgo.getTime()) / (24 * 60 * 60 * 1000);
        System.out.println("6日前との差: " + diffSixDays + "日");
        System.out.println("4日前との差: " + diffFourDays + "日");

        // 6日前の日付で5日経過テスト（6日以上経過していることを確認）
        assertTrue(Evolution.hasFiveDaysPassed(sixDaysAgo), "6日前の日付で5日経過していない");
        assertFalse(Evolution.hasSevenDaysPassed(sixDaysAgo), "6日前の日付で7日経過している");

        // 4日前の日付で5日経過テスト（4日未満なので5日経過していない）
        assertFalse(Evolution.hasFiveDaysPassed(fourDaysAgo), "4日前の日付で5日経過している");
        assertFalse(Evolution.hasSevenDaysPassed(fourDaysAgo), "4日前の日付で7日経過している");

        // 8日前の日付で7日経過テスト
        cal.add(Calendar.DAY_OF_MONTH, -4); // -4 - 4 = -8日前
        Date eightDaysAgo = cal.getTime();
        assertTrue(Evolution.hasFiveDaysPassed(eightDaysAgo), "8日前の日付で5日経過していない");
        assertTrue(Evolution.hasSevenDaysPassed(eightDaysAgo), "8日前の日付で7日経過していない");

        // nullの日付テスト
        assertFalse(Evolution.hasFiveDaysPassed(null));
        assertFalse(Evolution.hasSevenDaysPassed(null));
    }

    @Test
    public void testDecideStageWithDate_FiveDaysPassed() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -6); // 6日前
        Date sixDaysAgo = cal.getTime();

        // 任意のコミット数・ステージで5日経過している場合、SICKLYまたはINJUREDになる
        Evolution.Stage result1 = Evolution.decideStage(10, Evolution.Stage.EGG, sixDaysAgo);
        Evolution.Stage result2 = Evolution.decideStage(60, Evolution.Stage.BIRTH, sixDaysAgo);

        assertTrue(result1 == Evolution.Stage.SICKLY || result1 == Evolution.Stage.INJURED);
        assertTrue(result2 == Evolution.Stage.SICKLY || result2 == Evolution.Stage.INJURED);
    }

    @Test
    public void testDecideStageWithDate_SevenDaysPassed() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -8); // 8日前
        Date eightDaysAgo = cal.getTime();

        // 任意のコミット数・ステージで7日経過している場合、DEADになる
        Evolution.Stage result1 = Evolution.decideStage(10, Evolution.Stage.EGG, eightDaysAgo);
        Evolution.Stage result2 = Evolution.decideStage(60, Evolution.Stage.BIRTH, eightDaysAgo);
        Evolution.Stage result3 = Evolution.decideStage(5, Evolution.Stage.SICKLY, eightDaysAgo);

        assertEquals(Evolution.Stage.DEAD, result1);
        assertEquals(Evolution.Stage.DEAD, result2);
        assertEquals(Evolution.Stage.DEAD, result3);
    }

    @Test
    public void testDecideStageWithDate_NoDateProvided() {
        // 日付がnullの場合は現在のステージを維持（5日/7日経過チェックはスキップ）
        assertEquals(Evolution.Stage.EGG, Evolution.decideStage(2, Evolution.Stage.EGG, null));
        assertEquals(Evolution.Stage.CRACKED_EGG, Evolution.decideStage(3, Evolution.Stage.EGG, null));
    }

    @Test
    public void testDecideStageWithDate_RecentCommit() {
        // 最近のコミット（日付判定に該当しない）場合はコミット数ベースの進化
        Date recentDate = new Date(); // 現在時刻

        // コミット数ベースの進化が正しく動作することを確認
        assertEquals(Evolution.Stage.EGG, Evolution.decideStage(2, Evolution.Stage.EGG, recentDate));
        assertEquals(Evolution.Stage.CRACKED_EGG, Evolution.decideStage(3, Evolution.Stage.EGG, recentDate));
        assertEquals(Evolution.Stage.HATCHING, Evolution.decideStage(5, Evolution.Stage.CRACKED_EGG, recentDate));
        assertEquals(Evolution.Stage.DUCKLING, Evolution.decideStage(8, Evolution.Stage.HATCHING, recentDate));
        assertEquals(Evolution.Stage.MATCHING, Evolution.decideStage(13, Evolution.Stage.DUCKLING, recentDate));
        assertEquals(Evolution.Stage.MARRIED, Evolution.decideStage(21, Evolution.Stage.MATCHING, recentDate));
        assertEquals(Evolution.Stage.BIRTH, Evolution.decideStage(34, Evolution.Stage.MARRIED, recentDate));
    }

    @Test
    public void testDecideStageWithDate_FourDaysPassed() {
        // 4日前のコミット（5日未満）の場合はコミット数ベースの進化
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -4); // 4日前
        Date fourDaysAgo = cal.getTime();

        // 日付判定に該当しないので、コミット数ベースの進化が動作
        assertEquals(Evolution.Stage.EGG, Evolution.decideStage(2, Evolution.Stage.EGG, fourDaysAgo));
        assertEquals(Evolution.Stage.CRACKED_EGG, Evolution.decideStage(3, Evolution.Stage.EGG, fourDaysAgo));
        assertEquals(Evolution.Stage.BIRTH, Evolution.decideStage(50, Evolution.Stage.MARRIED, fourDaysAgo));
    }
}
