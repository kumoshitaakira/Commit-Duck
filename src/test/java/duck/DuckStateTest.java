package duck;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DuckStateTest {

    @Test
    public void testLoadOrNew() {
        // loadOrNewメソッドでDuckStateインスタンスが取得できることを確認
        assertDoesNotThrow(() -> {
            DuckState state = DuckState.loadOrNew();
            assertNotNull(state, "DuckState should not be null");
        });
    }

    @Test
    public void testGetCommits() {
        // getCommitsメソッドが正常に動作することを確認
        DuckState state = DuckState.loadOrNew();
        int commits = state.getCommits();
        assertTrue(commits >= 0, "Commits should be non-negative");
    }

    @Test
    public void testGetStage() {
        // getStageメソッドが正常に動作することを確認
        DuckState state = DuckState.loadOrNew();
        Evolution.Stage stage = state.getStage();
        assertNotNull(stage, "Stage should not be null");
    }

    @Test
    public void testRefreshFromGit() {
        // refreshFromGitメソッドが正常に動作することを確認
        assertDoesNotThrow(() -> {
            DuckState state = DuckState.loadOrNew();
            state.refreshFromGit();
        });
    }

    @Test
    public void testSave() {
        // saveメソッドが正常に動作することを確認
        assertDoesNotThrow(() -> {
            DuckState state = DuckState.loadOrNew();
            state.save();
        });
    }
}
