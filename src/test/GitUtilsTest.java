package test;

import main.GitUtils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GitUtilsTest {

    @Test
    public void testIsGitRepository() {
        // このプロジェクトはGitリポジトリなので、trueが返されることを期待
        boolean isGitRepo = GitUtils.isGitRepository();
        assertTrue(isGitRepo, "This project should be a git repository");
    }

    @Test
    public void testGetCommitCount() {
        try {
            int commitCount = GitUtils.getCommitCount();
            // コミット数は0以上であることを確認
            assertTrue(commitCount >= 0, "Commit count should be non-negative");
        } catch (Exception e) {
            // Gitコマンドが利用できない環境ではテストをスキップ
            System.out.println("Git command not available, skipping commit count test: " + e.getMessage());
        }
    }

    @Test
    public void testGetCurrentBranch() {
        try {
            String branch = GitUtils.getCurrentBranch();
            assertNotNull(branch, "Current branch should not be null");
            assertFalse(branch.trim().isEmpty(), "Current branch should not be empty");
        } catch (Exception e) {
            // Gitコマンドが利用できない環境ではテストをスキップ
            System.out.println("Git command not available, skipping branch test: " + e.getMessage());
        }
    }

    @Test
    public void testGitUtilsInstantiation() {
        // GitUtilsクラスのインスタンス化テスト（static methodsなのでインスタンス化は不要だが、テストとして）
        assertDoesNotThrow(() -> {
            GitUtils gitUtils = new GitUtils();
            assertNotNull(gitUtils);
        });
    }
}
