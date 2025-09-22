package duck;

public class DuckCli {

    private static void printStatus(DuckState st) {
        System.out.println("Commits: " + st.getCommits());
        System.out.println("Stage  : " + Evolution.stageLabel(st.getStage()));
        // FIXME:txtファイルで呼び出しできるようにする
        System.out.println(Evolution.ascii(st.getStage()));
    }

    private static void usage() {
        System.out.println("duck install | status | refresh | help");
    }

    /**
     * post-commitフックを設置
     */
    private static void installPostCommitHook() {
        try {
            String repoRoot = GitUtils.getRepoRoot();
            java.io.File hooksDir = new java.io.File(repoRoot, ".git/hooks");
            java.io.File postCommitFile = new java.io.File(hooksDir, "post-commit");

            // hooksディレクトリが存在しない場合は作成
            if (!hooksDir.exists()) {
                boolean created = hooksDir.mkdirs();
                if (!created) {
                    System.err.println("Failed to create hooks directory: " + hooksDir.getAbsolutePath());
                    return;
                }
            }

            // 既存のpost-commitフックがある場合は確認
            if (postCommitFile.exists()) {
                try (java.io.BufferedReader reader = new java.io.BufferedReader(
                        new java.io.FileReader(postCommitFile))) {
                    String content = reader.lines().collect(java.util.stream.Collectors.joining("\n"));
                    if (content.contains("duck refresh")) {
                        System.out.println("Duck post-commit hook already installed.");
                        return;
                    }
                }
            }

            // post-commitフックを作成
            String hookContent = "#!/usr/bin/env bash\n" +
                    "set -e\n" +
                    "# Duck commit hook - auto-refresh state after commit\n" +
                    "if command -v duck >/dev/null 2>&1; then\n" +
                    "    duck refresh 2>/dev/null || true\n" +
                    "fi\n";

            try (java.io.FileWriter writer = new java.io.FileWriter(postCommitFile)) {
                writer.write(hookContent);
            }

            // 実行権限を付与
            if (!postCommitFile.setExecutable(true)) {
                System.err.println("Warning: Failed to set executable permission on post-commit hook");
            }

            System.out.println("Post-commit hook installed successfully.");

        } catch (Exception e) {
            System.err.println("Failed to install post-commit hook: " + e.getMessage());
        }
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
                boolean stageChanged = st.refreshFromGit();
                // ステージが変更された場合のみ、追加で現在の状態を表示
                // (進化メッセージは既にrefreshFromGit内で表示済み)
                if (!stageChanged) {
                    // ステージが変更されていない場合は何も表示しない（静かに更新のみ）
                }
                return;
            }
            case "install": {
                // 初期状態の生成とpost-commitフックの設置
                DuckState st = DuckState.loadOrNew();
                st.save();
                installPostCommitHook();
                System.out.println("Initialized duck state and installed git hooks.");
                return;
            }
            case "help":
            default:
                usage();
        }
    }
}
