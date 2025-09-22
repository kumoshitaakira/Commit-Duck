package duck;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static org.junit.jupiter.api.Assertions.*;

public class EvolutionTest {

    @Test
    public void testDecideStage_EggStage() {
        assertEquals(Evolution.Stage.EGG, Evolution.decideStage(0));
        assertEquals(Evolution.Stage.EGG, Evolution.decideStage(1));
        assertEquals(Evolution.Stage.EGG, Evolution.decideStage(2));
    }

    @Test
    public void testDecideStage_CrackedEggStage() {
        assertEquals(Evolution.Stage.CRACKED_EGG, Evolution.decideStage(3));
        assertEquals(Evolution.Stage.CRACKED_EGG, Evolution.decideStage(4));
        assertEquals(Evolution.Stage.CRACKED_EGG, Evolution.decideStage(5));
    }

    @Test
    public void testDecideStage_HatchingStage() {
        assertEquals(Evolution.Stage.HATCHING, Evolution.decideStage(6));
        assertEquals(Evolution.Stage.HATCHING, Evolution.decideStage(8));
        assertEquals(Evolution.Stage.HATCHING, Evolution.decideStage(9));
    }

    @Test
    public void testDecideStage_DucklingStage() {
        assertEquals(Evolution.Stage.DUCKLING, Evolution.decideStage(10));
        assertEquals(Evolution.Stage.DUCKLING, Evolution.decideStage(12));
        assertEquals(Evolution.Stage.DUCKLING, Evolution.decideStage(14));
    }

    @Test
    public void testDecideStage_MatchingStage() {
        assertEquals(Evolution.Stage.MATCHING, Evolution.decideStage(15));
        assertEquals(Evolution.Stage.MATCHING, Evolution.decideStage(20));
        assertEquals(Evolution.Stage.MATCHING, Evolution.decideStage(24));
    }

    @Test
    public void testDecideStage_MarriedStage() {
        assertEquals(Evolution.Stage.MARRIED, Evolution.decideStage(25));
        assertEquals(Evolution.Stage.MARRIED, Evolution.decideStage(30));
        assertEquals(Evolution.Stage.MARRIED, Evolution.decideStage(39));
    }

    @Test
    public void testDecideStage_BirthStage() {
        assertEquals(Evolution.Stage.BIRTH, Evolution.decideStage(40));
        assertEquals(Evolution.Stage.BIRTH, Evolution.decideStage(50));
        assertEquals(Evolution.Stage.BIRTH, Evolution.decideStage(59));
    }

    @Test
    public void testDecideStage_SicklyStage() {
        assertEquals(Evolution.Stage.SICKLY, Evolution.decideStage(60));
        assertEquals(Evolution.Stage.SICKLY, Evolution.decideStage(70));
        assertEquals(Evolution.Stage.SICKLY, Evolution.decideStage(79));
    }

//    @Test
//    public void testDecideStage_InjuredStage() {
//        assertEquals(Evolution.Stage.INJURED, Evolution.decideStage(80));
//        assertEquals(Evolution.Stage.INJURED, Evolution.decideStage(90));
//        assertEquals(Evolution.Stage.INJURED, Evolution.decideStage(99));
//    }

    @Test
    public void testDecideStage_DeadStage() {
        assertEquals(Evolution.Stage.DEAD, Evolution.decideStage(100));
        assertEquals(Evolution.Stage.DEAD, Evolution.decideStage(150));
        assertEquals(Evolution.Stage.DEAD, Evolution.decideStage(1000));
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
    public void testAscii() {
        // ASCIIアートが返されることを確認（具体的な内容は検証しない）
        assertNotNull(Evolution.ascii(Evolution.Stage.EGG));
        assertNotNull(Evolution.ascii(Evolution.Stage.CRACKED_EGG));
        assertNotNull(Evolution.ascii(Evolution.Stage.HATCHING));
        assertNotNull(Evolution.ascii(Evolution.Stage.DUCKLING));
        assertNotNull(Evolution.ascii(Evolution.Stage.MATCHING));
        assertNotNull(Evolution.ascii(Evolution.Stage.MARRIED));
        assertNotNull(Evolution.ascii(Evolution.Stage.BIRTH));
        assertNotNull(Evolution.ascii(Evolution.Stage.SICKLY));
        assertNotNull(Evolution.ascii(Evolution.Stage.INJURED));
        assertNotNull(Evolution.ascii(Evolution.Stage.DEAD));
    }

    @Test
    public void testAllAssetsFilesExistAndReadable() {
        // すべてのステージのファイルが存在し、読み込み可能であることを確認
        String[] expectedFiles = {
                "duck_stage1.txt", // EGG
                "duck_stage2.txt", // CRACKED_EGG
                "duck_stage3.txt", // HATCHING
                "duck_stage4.txt", // DUCKLING
                "duck_stage5.txt", // MATCHING
                "duck_stage6.txt", // MARRIED
                "duck_stage7.txt", // BIRTH
                "duck_stage8.txt" // SICKLY, INJURED, DEAD (共通)
        };

        for (String filename : expectedFiles) {
            try {
                Path filePath = Paths.get("src/assets/" + filename);
                assertTrue(Files.exists(filePath), "File should exist: " + filename);
                String content = Files.readString(filePath);
                assertNotNull(content, "File content should not be null: " + filename);
                assertFalse(content.trim().isEmpty(), "File should not be empty: " + filename);
            } catch (Exception e) {
                fail("Failed to read file: " + filename + " - " + e.getMessage());
            }
        }
    }

    @Test
    public void testAsciiOutputContainsExpectedContent() {
        // 各ステージのASCII出力が期待される内容を含むことを確認
        Evolution.Stage[] stages = {
                Evolution.Stage.EGG,
                Evolution.Stage.CRACKED_EGG,
                Evolution.Stage.HATCHING,
                Evolution.Stage.DUCKLING,
                Evolution.Stage.MATCHING,
                Evolution.Stage.MARRIED,
                Evolution.Stage.BIRTH,
                Evolution.Stage.SICKLY,
                Evolution.Stage.INJURED,
                Evolution.Stage.DEAD
        };

        for (Evolution.Stage stage : stages) {
            String asciiArt = Evolution.ascii(stage);
            assertNotNull(asciiArt, "ASCII art should not be null for stage: " + stage);
            assertFalse(asciiArt.trim().isEmpty(), "ASCII art should not be empty for stage: " + stage);
            // ファイルが正常に読み込まれた場合、警告メッセージが含まれていないことを確認
            assertFalse(asciiArt.contains("ASCII art not available"),
                    "ASCII art should be loaded properly for stage: " + stage);
        }
    }

    @Test
    public void testTerminalOutputFormatting() {
        // ターミナル出力で各ステージのASCIIアートが正しくフォーマットされることを確認
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        try {
            System.setOut(new PrintStream(outputStream));

            // 各ステージのASCIIアートをターミナルに出力してテスト
            Evolution.Stage[] stages = {
                    Evolution.Stage.EGG,
                    Evolution.Stage.CRACKED_EGG,
                    Evolution.Stage.HATCHING,
                    Evolution.Stage.DUCKLING,
                    Evolution.Stage.MATCHING,
                    Evolution.Stage.MARRIED,
                    Evolution.Stage.BIRTH,
                    Evolution.Stage.SICKLY,
                    Evolution.Stage.INJURED,
                    Evolution.Stage.DEAD
            };

            for (Evolution.Stage stage : stages) {
                outputStream.reset();
                String asciiArt = Evolution.ascii(stage);
                System.out.print(asciiArt);

                String terminalOutput = outputStream.toString();
                assertNotNull(terminalOutput, "Terminal output should not be null for stage: " + stage);
                assertFalse(terminalOutput.trim().isEmpty(), "Terminal output should not be empty for stage: " + stage);
                assertEquals(asciiArt, terminalOutput, "Terminal output should match ASCII art for stage: " + stage);
            }
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    public void testAssetFilesMatchStageMapping() {
        // ファイルとステージのマッピングが正しいことを確認
        String eggArt = Evolution.ascii(Evolution.Stage.EGG);
        String crackedEggArt = Evolution.ascii(Evolution.Stage.CRACKED_EGG);
        String hatchingArt = Evolution.ascii(Evolution.Stage.HATCHING);
        String ducklingArt = Evolution.ascii(Evolution.Stage.DUCKLING);
        String matchingArt = Evolution.ascii(Evolution.Stage.MATCHING);
        String marriedArt = Evolution.ascii(Evolution.Stage.MARRIED);
        String birthArt = Evolution.ascii(Evolution.Stage.BIRTH);
        String sicklyArt = Evolution.ascii(Evolution.Stage.SICKLY);
        String injuredArt = Evolution.ascii(Evolution.Stage.INJURED);
        String deadArt = Evolution.ascii(Evolution.Stage.DEAD);

        // 主要なステージが異なる内容であることを確認（各ステージが独自のアートを持つ）
        assertNotEquals(eggArt, crackedEggArt, "EGG and CRACKED_EGG should have different ASCII art");
        assertNotEquals(crackedEggArt, hatchingArt, "CRACKED_EGG and HATCHING should have different ASCII art");
        assertNotEquals(hatchingArt, ducklingArt, "HATCHING and DUCKLING should have different ASCII art");
        assertNotEquals(ducklingArt, matchingArt, "DUCKLING and MATCHING should have different ASCII art");
        assertNotEquals(matchingArt, marriedArt, "MATCHING and MARRIED should have different ASCII art");
        assertNotEquals(marriedArt, birthArt, "MARRIED and BIRTH should have different ASCII art");

        // 各ASCII artが適切な長さを持つことを確認（空でない）
        assertTrue(eggArt.length() > 10, "EGG ASCII art should have reasonable length");
        assertTrue(crackedEggArt.length() > 10, "CRACKED_EGG ASCII art should have reasonable length");
        assertTrue(hatchingArt.length() > 10, "HATCHING ASCII art should have reasonable length");
        assertTrue(ducklingArt.length() > 10, "DUCKLING ASCII art should have reasonable length");
        assertTrue(matchingArt.length() > 10, "MATCHING ASCII art should have reasonable length");
        assertTrue(marriedArt.length() > 10, "MARRIED ASCII art should have reasonable length");
        assertTrue(birthArt.length() > 10, "BIRTH ASCII art should have reasonable length");
        assertTrue(sicklyArt.length() > 10, "SICKLY ASCII art should have reasonable length");
        assertTrue(injuredArt.length() > 10, "INJURED ASCII art should have reasonable length");
        assertTrue(deadArt.length() > 10, "DEAD ASCII art should have reasonable length");
    }

    @Test
    public void testOutputAsciiArtToTerminalAndFile() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("ASCII ART DISPLAY TEST - ALL EVOLUTION STAGES");
        System.out.println("=".repeat(60));

        // outディレクトリを作成
        Path outDir = Paths.get("out");
        try {
            if (!Files.exists(outDir)) {
                Files.createDirectories(outDir);
            }
        } catch (IOException e) {
            fail("Failed to create out directory: " + e.getMessage());
        }

        Evolution.Stage[] stages = {
                Evolution.Stage.EGG,
                Evolution.Stage.CRACKED_EGG,
                Evolution.Stage.HATCHING,
                Evolution.Stage.DUCKLING,
                Evolution.Stage.MATCHING,
                Evolution.Stage.MARRIED,
                Evolution.Stage.BIRTH,
                Evolution.Stage.SICKLY,
                Evolution.Stage.INJURED,
                Evolution.Stage.DEAD
        };

        StringBuilder reportContent = new StringBuilder();
        reportContent.append("ASCII ART TEST REPORT\n");
        reportContent.append("Generated at: ")
                .append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n");
        reportContent.append("=".repeat(80)).append("\n\n");

        for (Evolution.Stage stage : stages) {
            String stageLabel = Evolution.stageLabel(stage);
            String asciiArt = Evolution.ascii(stage);

            // ターミナルに出力
            System.out.println("\n" + "-".repeat(40));
            System.out.println("STAGE: " + stageLabel.toUpperCase() + " (" + stage + ")");
            System.out.println("-".repeat(40));
            System.out.println(asciiArt);

            // レポート内容に追加
            reportContent.append("STAGE: ").append(stageLabel.toUpperCase()).append(" (").append(stage).append(")\n");
            reportContent.append("-".repeat(40)).append("\n");
            reportContent.append(asciiArt).append("\n");
            reportContent.append("-".repeat(40)).append("\n\n");

            // 個別ファイルにも保存
            try {
                Path stageFile = Paths.get("out", "ascii_art_" + stage.toString().toLowerCase() + ".txt");
                try (FileWriter writer = new FileWriter(stageFile.toFile())) {
                    writer.write("ASCII ART - " + stageLabel.toUpperCase() + "\n");
                    writer.write("Stage: " + stage + "\n");
                    writer.write("Generated at: "
                            + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n");
                    writer.write("=".repeat(50) + "\n\n");
                    writer.write(asciiArt);
                }
            } catch (IOException e) {
                fail("Failed to write individual stage file for " + stage + ": " + e.getMessage());
            }

            // テスト検証
            assertNotNull(asciiArt, "ASCII art should not be null for " + stage);
            assertFalse(asciiArt.trim().isEmpty(), "ASCII art should not be empty for " + stage);
        }

        // 統合レポートファイルを作成
        try {
            Path reportFile = Paths.get("out", "ascii_art_test_report.txt");
            try (FileWriter writer = new FileWriter(reportFile.toFile())) {
                writer.write(reportContent.toString());
            }
            System.out.println("\n" + "=".repeat(60));
            System.out.println("ASCII ART TEST COMPLETED SUCCESSFULLY");
            System.out.println("Report saved to: " + reportFile.toAbsolutePath());
            System.out.println("Individual files saved in: " + outDir.toAbsolutePath());
            System.out.println("=".repeat(60));
        } catch (IOException e) {
            fail("Failed to write test report: " + e.getMessage());
        }

        // 出力ファイルの存在を検証
        assertTrue(Files.exists(Paths.get("out", "ascii_art_test_report.txt")),
                "Test report file should be created");
        for (Evolution.Stage stage : stages) {
            Path stageFile = Paths.get("out", "ascii_art_" + stage.toString().toLowerCase() + ".txt");
            assertTrue(Files.exists(stageFile),
                    "Individual stage file should be created for " + stage);
        }
    }

    @Test
    public void testDisplayAsciiArtSummary() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("ASCII ART SUMMARY - EVOLUTION PROGRESSION");
        System.out.println("=".repeat(80));

        Evolution.Stage[] stages = {
                Evolution.Stage.EGG,
                Evolution.Stage.CRACKED_EGG,
                Evolution.Stage.HATCHING,
                Evolution.Stage.DUCKLING,
                Evolution.Stage.MATCHING,
                Evolution.Stage.MARRIED,
                Evolution.Stage.BIRTH,
                Evolution.Stage.SICKLY,
                Evolution.Stage.INJURED,
                Evolution.Stage.DEAD
        };

        int[] commitThresholds = { 0, 3, 6, 10, 15, 25, 40, 60, 80, 100 };

        for (int i = 0; i < stages.length; i++) {
            Evolution.Stage stage = stages[i];
            String stageLabel = Evolution.stageLabel(stage);
            String asciiArt = Evolution.ascii(stage);

            System.out.println("\nSTAGE " + (i + 1) + ": " + stageLabel.toUpperCase());
            System.out.println("Commit threshold: " + commitThresholds[i] + "+");
            System.out.println("Stage enum: " + stage);
            System.out.println("Art preview (first 3 lines):");

            String[] lines = asciiArt.split("\\n");
            for (int j = 0; j < Math.min(3, lines.length); j++) {
                System.out.println("  " + lines[j]);
            }
            if (lines.length > 3) {
                System.out.println("  ... (" + (lines.length - 3) + " more lines)");
            }
            System.out.println("  Total lines: " + lines.length + ", Total characters: " + asciiArt.length());

            // テスト検証
            assertNotNull(asciiArt, "ASCII art should not be null for " + stage);
            assertTrue(lines.length > 0, "ASCII art should have at least one line for " + stage);
        }

        System.out.println("\n" + "=".repeat(80));
        System.out.println("SUMMARY COMPLETE - All stages validated successfully");
        System.out.println("=".repeat(80));
    }
}
