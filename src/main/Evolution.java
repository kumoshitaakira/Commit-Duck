package main;

public class Evolution {

    public enum Stage {
        EGG("🥚", 0, 4),
        DUCKLING("🐣", 5, 9),
        TEEN("🦆", 10, 24),
        ADULT("🦆", 25, 49),
        LEGEND("🦆✨", 50, Integer.MAX_VALUE);

        private final String emoji;
        private final int minCommits;
        private final int maxCommits;

        Stage(String emoji, int minCommits, int maxCommits) {
            this.emoji = emoji;
            this.minCommits = minCommits;
            this.maxCommits = maxCommits;
        }

        public String getEmoji() {
            return emoji;
        }

        public int getMinCommits() {
            return minCommits;
        }

        public int getMaxCommits() {
            return maxCommits;
        }
    }

    public static Stage getStageForCommits(int commits) {
        for (Stage stage : Stage.values()) {
            if (commits >= stage.getMinCommits() && commits <= stage.getMaxCommits()) {
                return stage;
            }
        }
        return Stage.EGG; // デフォルト
    }

    public static String getStageNameForCommits(int commits) {
        return getStageForCommits(commits).name();
    }

    public static String getEmojiForCommits(int commits) {
        return getStageForCommits(commits).getEmoji();
    }
}
