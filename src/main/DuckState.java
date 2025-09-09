package main;

import java.io.*;
import java.util.Properties;

public class DuckState {
    // リポジトリごとの状態ファイル（<repoRoot>/.duck/state.properties）
    private final String repoRoot;
    private final File stateDir;
    private final File stateFile;

    private int commits;
    private Evolution.Stage stage;

    private DuckState(String repoRoot) {
        this.repoRoot = repoRoot;
        this.stateDir = new File(repoRoot, ".duck");
        this.stateFile = new File(stateDir, "state.properties");
    }

    public int getCommits() {
        return commits;
    }

    public Evolution.Stage getStage() {
        return stage;
    }

    private void set(int commits, Evolution.Stage stage) {
        this.commits = commits;
        this.stage = stage;
    }

    /** 現在の git リポジトリに対する状態をロード。なければ初期化して作成。 */
    public static DuckState loadOrNew() {
        try {
            String root = GitUtils.getRepoRoot(); // 常に“今のリポジトリ”を解決
            DuckState st = new DuckState(root);

            if (!st.stateDir.exists())
                st.stateDir.mkdirs();

            if (!st.stateFile.exists()) {
                st.refreshFromGit(); // 初期化時点でコミット数反映
                st.save();
                return st;
            }

            Properties p = new Properties();
            try (InputStream in = new FileInputStream(st.stateFile)) {
                p.load(in);
            }
            int c = Integer.parseInt(p.getProperty("commits", "0"));
            Evolution.Stage s = Evolution.Stage.valueOf(p.getProperty("stage", "EGG"));
            st.set(c, s);
            return st;
        } catch (Exception e) {
            System.err.println("状態読み込みエラー: " + e.getMessage());
            try {
                String root = GitUtils.getRepoRoot();
                DuckState st = new DuckState(root);
                st.set(0, Evolution.decideStage(0));
                return st;
            } catch (Exception ignored) {
                // git 管理外など：呼び出し側で扱う想定
                DuckState st = new DuckState(new File(".").getAbsolutePath());
                st.set(0, Evolution.decideStage(0));
                return st;
            }
        }
    }

    /** 現在のリポジトリのコミット総数から再計算して保存 */
    public void refreshFromGit() {
        try {
            int c = GitUtils.getCommitCount();
            set(c, Evolution.decideStage(c));
            save();
        } catch (Exception e) {
            System.err.println("コミット数取得に失敗: " + e.getMessage());
        }
    }

    public void save() {
        Properties p = new Properties();
        p.setProperty("commits", Integer.toString(commits));
        p.setProperty("stage", stage.name());
        try (OutputStream out = new FileOutputStream(stateFile)) {
            p.store(out, "duck state (per repository)");
        } catch (IOException e) {
            System.err.println("状態保存に失敗: " + e.getMessage());
        }
    }
}
