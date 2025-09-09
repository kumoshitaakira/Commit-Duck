package test;

import main.DuckState;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.IOException;

public class DuckStateTest {

    private DuckState duckState;
    private static final String TEST_STATE_DIR = ".duck";
    private static final String TEST_STATE_FILE = ".duck/state.properties";

    @BeforeEach
    public void setUp() {
        duckState = new DuckState();
        // テスト前に既存の状態ファイルを削除
        cleanupStateFile();
    }

    @AfterEach
    public void tearDown() {
        // テスト後に作成された状態ファイルを削除
        cleanupStateFile();
    }

    private void cleanupStateFile() {
        File stateFile = new File(TEST_STATE_FILE);
        if (stateFile.exists()) {
            stateFile.delete();
        }
        File stateDir = new File(TEST_STATE_DIR);
        if (stateDir.exists()) {
            stateDir.delete();
        }
    }

    @Test
    public void testDefaultConstructor() {
        assertEquals(0, duckState.getCommits(), "Default commits should be 0");
        assertEquals("EGG", duckState.getStage(), "Default stage should be EGG");
    }

    @Test
    public void testParameterizedConstructor() {
        DuckState state = new DuckState(10, "TEEN");
        assertEquals(10, state.getCommits(), "Commits should be 10");
        assertEquals("TEEN", state.getStage(), "Stage should be TEEN");
    }

    @Test
    public void testSetAndGetCommits() {
        duckState.setCommits(5);
        assertEquals(5, duckState.getCommits(), "Commits should be 5");
    }

    @Test
    public void testSetAndGetStage() {
        duckState.setStage("DUCKLING");
        assertEquals("DUCKLING", duckState.getStage(), "Stage should be DUCKLING");
    }

    @Test
    public void testSaveAndLoad() throws IOException {
        // 状態を設定して保存
        duckState.setCommits(15);
        duckState.setStage("TEEN");
        duckState.save();

        // ファイルが作成されたことを確認
        File stateFile = new File(TEST_STATE_FILE);
        assertTrue(stateFile.exists(), "State file should exist after save");

        // 保存された状態を読み込み
        DuckState loadedState = DuckState.load();
        assertEquals(15, loadedState.getCommits(), "Loaded commits should be 15");
        assertEquals("TEEN", loadedState.getStage(), "Loaded stage should be TEEN");
    }

    @Test
    public void testLoadNonExistentFile() throws IOException {
        // 存在しないファイルをロードした場合、デフォルト状態が返されることを確認
        DuckState defaultState = DuckState.load();
        assertEquals(0, defaultState.getCommits(), "Default commits should be 0");
        assertEquals("EGG", defaultState.getStage(), "Default stage should be EGG");
    }
}
