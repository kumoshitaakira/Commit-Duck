package duck;

import duck.Evolution;
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

    @Test
    public void testAllAssetsFilesExistAndReadable() {
        // すべてのステージのファイルが存在し、読み込み可能であることを確認
        String[] expectedFiles = {
                "duck_stage1.txt", // EGG
                "duck_stage2.txt", // DUCKLING
                "duck_stage3.txt", // TEEN
                "duck_stage4.txt", // ADULT
                "duck_stage5.txt" // LEGEND
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
                Evolution.Stage.DUCKLING,
                Evolution.Stage.TEEN,
                Evolution.Stage.ADULT,
                Evolution.Stage.LEGEND
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
                    Evolution.Stage.DUCKLING,
                    Evolution.Stage.TEEN,
                    Evolution.Stage.ADULT,
                    Evolution.Stage.LEGEND
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
        String ducklingArt = Evolution.ascii(Evolution.Stage.DUCKLING);
        String teenArt = Evolution.ascii(Evolution.Stage.TEEN);
        String adultArt = Evolution.ascii(Evolution.Stage.ADULT);
        String legendArt = Evolution.ascii(Evolution.Stage.LEGEND);

        // すべて異なる内容であることを確認（各ステージが独自のアートを持つ）
        assertNotEquals(eggArt, ducklingArt, "EGG and DUCKLING should have different ASCII art");
        assertNotEquals(ducklingArt, teenArt, "DUCKLING and TEEN should have different ASCII art");
        assertNotEquals(teenArt, adultArt, "TEEN and ADULT should have different ASCII art");
        assertNotEquals(adultArt, legendArt, "ADULT and LEGEND should have different ASCII art");

        // 各ASCII artが適切な長さを持つことを確認（空でない）
        assertTrue(eggArt.length() > 10, "EGG ASCII art should have reasonable length");
        assertTrue(ducklingArt.length() > 10, "DUCKLING ASCII art should have reasonable length");
        assertTrue(teenArt.length() > 10, "TEEN ASCII art should have reasonable length");
        assertTrue(adultArt.length() > 10, "ADULT ASCII art should have reasonable length");
        assertTrue(legendArt.length() > 10, "LEGEND ASCII art should have reasonable length");
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
                Evolution.Stage.DUCKLING,
                Evolution.Stage.TEEN,
                Evolution.Stage.ADULT,
                Evolution.Stage.LEGEND
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
                Evolution.Stage.DUCKLING,
                Evolution.Stage.TEEN,
                Evolution.Stage.ADULT,
                Evolution.Stage.LEGEND
        };

        int[] commitThresholds = { 0, 5, 10, 25, 50 };

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
