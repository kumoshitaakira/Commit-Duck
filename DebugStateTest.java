import duck.DuckState;

public class DebugStateTest {
    public static void main(String[] args) {
        try {
            System.out.println("=== 状態デバッグテスト ===");

            DuckState state = DuckState.loadOrNew();
            System.out.println("ロード後の状態:");
            System.out.println("  commits: " + state.getCommits());
            System.out.println("  totalCommits: " + state.getTotalCommits());
            System.out.println("  stageCommits: " + state.getStageCommits());
            System.out.println("  stage: " + state.getStage());

            System.out.println("\nrefreshFromGit呼び出し前:");
            boolean changed = state.refreshFromGit();
            System.out.println("refreshFromGit result: " + changed);

            System.out.println("\nrefreshFromGit呼び出し後:");
            System.out.println("  commits: " + state.getCommits());
            System.out.println("  totalCommits: " + state.getTotalCommits());
            System.out.println("  stageCommits: " + state.getStageCommits());
            System.out.println("  stage: " + state.getStage());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
