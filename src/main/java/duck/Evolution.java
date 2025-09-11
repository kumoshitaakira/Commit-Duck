package duck;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Evolution {
    public enum Stage {
        EGG, DUCKLING, TEEN, ADULT, LEGEND
    }

    public static Stage decideStage(int commitCount) {
        if (commitCount < 5)
            return Stage.EGG;
        if (commitCount < 10)
            return Stage.DUCKLING;
        if (commitCount < 25)
            return Stage.TEEN;
        if (commitCount < 50)
            return Stage.ADULT;
        return Stage.LEGEND;
    }

    public static String stageLabel(Stage s) {
        switch (s) {
            case EGG:
                return "Egg";
            case DUCKLING:
                return "Duckling";
            case TEEN:
                return "Teen";
            case ADULT:
                return "Adult";
            default:
                return "Legend";
        }
    }

    private static String readAsciiFromFile(String filename) {
        try {
            Path filePath = Paths.get("src/assets/" + filename);
            return Files.readString(filePath);
        } catch (IOException e) {
            System.err.println("Warning: Could not read " + filename + ", using fallback ASCII art");
            return "ASCII art not available";
        }
    }

    public static String ascii(Stage s) {
        switch (s) {
            case EGG:
                return readAsciiFromFile("duck_stage1.txt");
            case DUCKLING:
                return readAsciiFromFile("duck_stage2.txt");
            case TEEN:
                return readAsciiFromFile("duck_stage3.txt");
            case ADULT:
                return readAsciiFromFile("duck_stage4.txt");
            case LEGEND:
            default:
                return readAsciiFromFile("duck_stage5.txt");
        }
    }
}
