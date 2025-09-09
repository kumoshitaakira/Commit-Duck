package duck;

public class DuckCli {

    private static void printStatus(DuckState st) {
        System.out.println("Commits: " + st.getCommits());
        System.out.println("Stage  : " + Evolution.stageLabel(st.getStage()));
        System.out.println(Evolution.ascii(st.getStage()));
    }

    private static void usage() {
        System.out.println("duck install | status | refresh | help");
    }

    public static void main(String[] args) {
        String cmd = (args.length > 0) ? args[0].toLowerCase() : "help";
        switch (cmd) {
            case "status": {
                DuckState st = DuckState.loadOrNew();
                printStatus(st);
                return;
            }
            case "refresh": {
                DuckState st = DuckState.loadOrNew();
                st.refreshFromGit();
                printStatus(st);
                return;
            }
            case "install": {
                // フック設置はシェル/バッチが担当。ここでは初期状態の生成のみ。
                DuckState st = DuckState.loadOrNew();
                st.save();
                System.out.println("Initialized duck state. Run 'duck install' to place git hooks.");
                return;
            }
            case "help":
            default:
                usage();
        }
    }
}
