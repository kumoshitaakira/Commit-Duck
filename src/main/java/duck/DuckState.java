package duck;

import java.io.*;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import duck.Evolution.Stage;

import java.util.logging.Logger;

public class DuckState {
    private static final Logger logger = Logger.getLogger(DuckState.class.getName());
    private static final String INITIAL_COMMIT_FILE = ".commit_duck_initial_commit_count";
    private static final String PROP_COMMITS = "commits";
    private static final String PROP_STAGE = "stage";
    private static final String DEFAULT_STAGE = "EGG";

    private final File stateDir;
    private final File stateFile;

    private int commits;
    private Evolution.Stage stage;

    protected DuckState(String repoRoot) {
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

    /**
     * 現在の git リポジトリに対する状態をロード。なければ初期化して作成。
     * コミット数取得ロジックを新方式に置換。
     */
    public static DuckState loadOrNew() {
        try {
            String root = GitUtils.getRepoRoot();
            DuckState st = new DuckState(root);

            if (!st.stateDir.exists()) {
                boolean created = st.stateDir.mkdirs();
                if (!created) {
                    logger.warning("ディレクトリ作成に失敗: " + st.stateDir.getAbsolutePath());
                }
            }

            if (!st.stateFile.exists()) {
                int c = getCommitNumberOfTimes();
                st.set(c, Evolution.decideStage(c, Stage.EGG));
                st.save();
                return st;
            }

            Properties p = new Properties();
            try (InputStream in = new FileInputStream(st.stateFile)) {
                p.load(in);
            }
            int c = Integer.parseInt(p.getProperty(PROP_COMMITS, "0"));
            String stageName = p.getProperty(PROP_STAGE, DEFAULT_STAGE);
            Evolution.Stage s;
            try {
                s = Enum.valueOf(Evolution.Stage.class, stageName);
            } catch (IllegalArgumentException e) {
                s = Enum.valueOf(Evolution.Stage.class, DEFAULT_STAGE);
            }
            st.set(c, s);
            return st;
        } catch (Exception e) {
            logger.severe("状態読み込みエラー: " + e.getMessage());
            try {
                String root = GitUtils.getRepoRoot();
                DuckState st = new DuckState(root);
                int c = getCommitNumberOfTimes();
                st.set(c, Evolution.decideStage(c, Stage.EGG));
                return st;
            } catch (Exception ignored) {
                DuckState st = new DuckState(new File(".").getAbsolutePath());
                st.set(0, Evolution.decideStage(0, Stage.EGG));
                return st;
            }
        }
    }

    /**
     * 現在のリポジトリのコミット総数から再計算して保存（新ロジック）
     */
    public void refreshFromGit() {
        try {
            int c = getCommitNumberOfTimes();
            set(c, Evolution.decideStage(c, this.stage));
            save();
        } catch (Exception e) {
            logger.severe("コミット数取得に失敗: " + e.getMessage());
        }
    }

    public void save() {
        Properties p = new Properties();
        p.setProperty(PROP_COMMITS, Integer.toString(commits));
        p.setProperty(PROP_STAGE, stage.name());
        try (OutputStream out = new FileOutputStream(stateFile)) {
            p.store(out, "duck state (per repository)");
        } catch (IOException e) {
            logger.severe("状態保存に失敗: " + e.getMessage());
        }
    }

    /**
     * 'git log'コマンドでコミット履歴を取得し、総コミット数を返す
     */
    public static int getCommitTotalNumberOfTimes() {
        int commitCount = 0;
        try {
            Process process = Runtime.getRuntime().exec("git log --pretty=oneline");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            Pattern pattern = Pattern.compile("^[a-f0-9]{40} ");
            while ((line = reader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    commitCount++;
                }
            }
            reader.close();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            logger.warning("git log取得失敗: " + e.getMessage());
        }
        return commitCount;
    }

    /**
     * 初回実行時のコミット数を~/.commit_duck_initial_commit_countで管理し、以降の増加分を返す
     */
    public static int getCommitNumberOfTimes() {
        int initialCommitCount = 0;
        try {
            String homeDir = System.getProperty("user.home");
            File file = new File(homeDir, INITIAL_COMMIT_FILE);
            Properties props = new Properties();
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                props.load(fis);
                fis.close();
            }
            String currentDir = new File(".").getCanonicalPath();
            if (!props.containsKey(currentDir)) {
                int currentCount = getCommitTotalNumberOfTimes();
                props.setProperty(currentDir, String.valueOf(currentCount));
                FileOutputStream fos = new FileOutputStream(file);
                props.store(fos, null);
                fos.close();
                initialCommitCount = currentCount;
            } else {
                initialCommitCount = Integer.parseInt(props.getProperty(currentDir));
            }
        } catch (IOException e) {
            logger.warning("初回コミット数記録ファイル処理失敗: " + e.getMessage());
        }
        return getCommitTotalNumberOfTimes() - initialCommitCount;
    }
}
