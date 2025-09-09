package main;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class GitUtils {

    /** 現在 HEAD の総コミット数を取得 */
    public static int getCommitCount() throws Exception {
        ProcessBuilder pb = new ProcessBuilder("git", "rev-list", "--count", "HEAD");
        pb.redirectErrorStream(true);
        Process p = pb.start();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            String line = br.readLine();
            int exit = p.waitFor();
            if (exit != 0 || line == null) {
                throw new IllegalStateException("git からコミット数を取得できません。git 管理下で実行してください。");
            }
            return Integer.parseInt(line.trim());
        }
    }

    /** 実行場所に依存せず、対象 git リポジトリのルートパスを返す */
    public static String getRepoRoot() throws Exception {
        ProcessBuilder pb = new ProcessBuilder("git", "rev-parse", "--show-toplevel");
        pb.redirectErrorStream(true);
        Process p = pb.start();
        String root;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            root = br.readLine();
        }
        int exit = p.waitFor();
        if (exit != 0 || root == null || root.isEmpty()) {
            throw new IllegalStateException("git リポジトリのルートを取得できません。");
        }
        return root.trim();
    }
}
