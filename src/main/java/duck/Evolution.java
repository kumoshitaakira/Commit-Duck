package duck;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

public class Evolution {
    public enum Stage {
        EGG(3),
        CRACKED_EGG(5),
        HATCHING(8),
        DUCKLING(13),
        MATCHING(21),
        MARRIED(34),
        BIRTH(55),
        SICKLY(null),
        INJURED(null),
        DEAD(null);

        private final Integer stageLimit;

        Stage(Integer limit) {
            this.stageLimit = limit; // EGG=3, CRACKED_EGG=4, ..., DEAD=12
        }

        public Integer getStageLimit() {
            return stageLimit;
        }
    }

    private static final Random random = new Random();

    public static Stage decideStage(int commitCount, Stage stage) {
        int idx = -1;
        for (Stage s : Stage.values()) {
            if (idx == s.ordinal()) { // next stageの進む時のみ
                return s;
            }
            if (stage != s) { // ステージが違う時は以下の処理を行わない
                continue;
            }
            if (stage.getStageLimit() != null) { // ステージの上限が設定されている場合
                if (commitCount <= stage.getStageLimit()) {
                    return stage;
                } else { // 次ステージに行ける時
                    idx = stage.ordinal() + 1;
                    continue; // 次のループで次のステージを探す
                }
            }
            // デフォルトは今のステージを返す
            return stage;
        }
        // 55以上はランダムにSICKLY, INJURED, DEADを返す
        int r = random.nextInt(3);
        return switch (r) {
            case 0 -> Stage.SICKLY;
            case 1 -> Stage.INJURED;
            default -> Stage.DEAD;
        };
        // if (commitCount < 3)
        // return Stage.EGG;
        // if (commitCount < 5)
        // return Stage.CRACKED_EGG;
        // if (commitCount < 8)
        // return Stage.HATCHING;
        // if (commitCount < 13)
        // return Stage.DUCKLING;
        // if (commitCount < 21)
        // return Stage.MATCHING;
        // if (commitCount < 34)
        // return Stage.MARRIED;
        // if (commitCount < 55)
        // return Stage.BIRTH;
        // if (commitCount < 80)
        // return random.nextBoolean()? Stage.SICKLY : Stage.INJURED;
        // return Stage.DEAD;
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
                return "Matching";
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
            // まずJARファイル内のリソースから読み込みを試行
            var inputStream = Evolution.class.getResourceAsStream("/" + filename);
            if (inputStream != null) {
                return new String(inputStream.readAllBytes());
            }

            // JARファイル内にない場合は、従来の相対パスで試行（開発時用）
            Path filePath = Paths.get("src/assets/" + filename);
            if (Files.exists(filePath)) {
                return Files.readString(filePath);
            }

            throw new IOException("File not found: " + filename);
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
                return readAsciiFromFile("duck_stage9.txt");
            case DEAD:
                return readAsciiFromFile("duck_stage10.txt");
            default:
                return readAsciiFromFile("duck_stage1.txt");
        }
    }
}
