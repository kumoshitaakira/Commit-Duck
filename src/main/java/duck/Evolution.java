package duck;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Evolution {
    public enum Stage {
        EGG, CRACKED_EGG, HATCHING, DUCKLING, MATCHING, MARRIED, BIRTH, SICKLY, INJURED, DEAD
    }

    public static Stage decideStage(int commitCount) {
        if (commitCount < 3)
            return Stage.EGG;
        if (commitCount < 6)
            return Stage.CRACKED_EGG;
        if (commitCount < 10)
            return Stage.HATCHING;
        if (commitCount < 15)
            return Stage.DUCKLING;
        if (commitCount < 25)
            return Stage.MATCHING;
        if (commitCount < 40)
            return Stage.MARRIED;
        if (commitCount < 60)
            return Stage.BIRTH;
        if (commitCount < 80)
            return Stage.SICKLY;
        if (commitCount < 100)
            return Stage.INJURED;
        return Stage.DEAD;
    }

    public static String stageLabel(Stage s) {
        switch (s) {
            case EGG:
                return "Egg";
            case CRACKED_EGG:
                return "Cracked Egg";
            case HATCHING:
                return "Hatching";
            case DUCKLING:
                return "Duckling";
            case MATCHING:
                return "Meeting";
            case MARRIED:
                return "Married";
            case BIRTH:
                return "Nesting";
            case SICKLY:
                return "Sickly";
            case INJURED:
                return "Injured";
            case DEAD:
            default:
                return "Deceased";
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
            case CRACKED_EGG:
                return readAsciiFromFile("duck_stage2.txt");
            case HATCHING:
                return readAsciiFromFile("duck_stage3.txt");
            case DUCKLING:
                return readAsciiFromFile("duck_stage4.txt");
            case MATCHING:
                return readAsciiFromFile("duck_stage5.txt");
            case MARRIED:
                return readAsciiFromFile("duck_stage6.txt");
            case BIRTH:
                return readAsciiFromFile("duck_stage7.txt");
            case SICKLY:
                return readAsciiFromFile("duck_stage8.txt");
            case INJURED:
                return readAsciiFromFile("duck_stage8.txt");
            case DEAD:
                return readAsciiFromFile("duck_stage8.txt"); // 同じファイルを使用
            default:
                return readAsciiFromFile("duck_stage1.txt");
        }
    }
}
