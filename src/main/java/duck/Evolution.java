package duck;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.Date;
import java.util.Calendar;

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
        DEAD(null)
        ;
        private final Integer stageLimit;

        Stage(Integer limit) {
            this.stageLimit = limit;
        }

        public Integer getStageLimit() {
            return stageLimit;
        }
    }

    private static final Random random = new Random();  

    public static boolean hasDaysPassed(Date startDate, int days) {
        if (startDate == null) {
            return false;
        }
        
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);
        
        Calendar currentCal = Calendar.getInstance();
        
        long diffInMillis = currentCal.getTimeInMillis() - startCal.getTimeInMillis();
        long diffInDays = diffInMillis / (24 * 60 * 60 * 1000);
        
        return diffInDays >= days;
    }

    public static boolean hasFiveDaysPassed(Date startDate) {
        return hasDaysPassed(startDate, 5);
    }

    public static boolean hasSevenDaysPassed(Date startDate) {
        return hasDaysPassed(startDate, 7);
    }

    public static Stage decideStage(int commitCount, Stage stage, Date lastCommitDate) {
        System.out.println("Last commit date: " + lastCommitDate);
        

        int idx = -1;
        if (stage.getStageLimit() != null) {
            if (idx == stage.ordinal() || commitCount < stage.getStageLimit()) {
                return stage;
            } else {
                idx = stage.ordinal() + 1;
            }
        }
        else if(stage == Stage.SICKLY || stage == Stage.INJURED){
            return random.nextBoolean() ? Stage.SICKLY : Stage.INJURED;
        }
        else {
            return Stage.DEAD;
        }

        // 5日経ったらSICKLY, INJURED（lastCommitDateがnullでない場合のみ）
        if (lastCommitDate != null && hasFiveDaysPassed(lastCommitDate)) {
            int r = random.nextInt(2);
            return switch (r) {
                case 0 -> Stage.SICKLY;
                case 1 -> Stage.INJURED;
                default -> Stage.SICKLY;
            };
        }

        // 7日経ったらDEAD（lastCommitDateがnullでない場合のみ）
        if (lastCommitDate != null && hasSevenDaysPassed(lastCommitDate)) {
            return Stage.DEAD;
        }
        
        return stage; // デフォルトの戻り値
    }

//        if (commitCount < 3)
//            return Stage.EGG;
//        if (commitCount < 5)
//            return Stage.CRACKED_EGG;
//        if (commitCount < 8)
//            return Stage.HATCHING;
//        if (commitCount < 13)
//            return Stage.DUCKLING;
//        if (commitCount < 21)
//            return Stage.MATCHING;
//        if (commitCount < 34)
//            return Stage.MARRIED;
//        if (commitCount < 55)
//            return Stage.BIRTH;
//        if (commitCount < 80)
//            return random.nextBoolean()? Stage.SICKLY : Stage.INJURED;
//        return Stage.DEAD;

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
                return readAsciiFromFile("duck_stage9.txt");
            case DEAD:
                return readAsciiFromFile("duck_stage10.txt"); 
            default:
                return readAsciiFromFile("duck_stage1.txt");
        }
    }
}
