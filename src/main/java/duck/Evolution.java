package duck;

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

    public static String ascii(Stage s) {
        switch (s) {
            case EGG:
                return ""
                        + "   ____  \n"
                        + "  / __ \\ \n"
                        + " | |  | |  (egg)\n"
                        + " | |  | | \n"
                        + " | |__| | \n"
                        + "  \\____/  \n";
            case DUCKLING:
                return ""
                        + "  _      \n"
                        + " (.)_    \n"
                        + " /|  )   (duckling)\n"
                        + "  |_/    \n";
            case TEEN:
                return ""
                        + "   __      \n"
                        + " __(o )___  (teen)\n"
                        + "/__     _/ \n"
                        + "  /_/_/_/   \n";
            case ADULT:
                return ""
                        + "     __      \n"
                        + " __(o )___   (adult)\n"
                        + "/  ___   / \n"
                        + "\\_/   \\_/  \n";
            case LEGEND:
            default:
                return ""
                        + "      __           \n"
                        + "  ___(o )___  ~*~  (legend)\n"
                        + " /  /___   /\\      \n"
                        + "/__/   /__/  \\__   \n";
        }
    }
}
