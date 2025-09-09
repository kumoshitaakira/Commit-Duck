package test;

import main.DuckCli;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class DuckCliTest {

    private DuckCli duckCli;

    @BeforeEach
    public void setUp() {
        duckCli = new DuckCli();
    }

    @Test
    public void testDuckCliInitialization() {
        assertNotNull(duckCli, "DuckCli should be initialized");
    }

    @Test
    public void testDuckCliIsNotNull() {
        assertTrue(duckCli instanceof DuckCli, "Instance should be of type DuckCli");
    }

    @Test
    public void testRunWithEmptyArgs() {
        // 空の引数でrunメソッドを呼び出してもエラーが発生しないことを確認
        assertDoesNotThrow(() -> {
            duckCli.run(new String[] {});
        });
    }

    @Test
    public void testRunWithStatusCommand() {
        // statusコマンドでrunメソッドを呼び出してもエラーが発生しないことを確認
        assertDoesNotThrow(() -> {
            duckCli.run(new String[] { "status" });
        });
    }

    @Test
    public void testRunWithHelpCommand() {
        // helpコマンドでrunメソッドを呼び出してもエラーが発生しないことを確認
        assertDoesNotThrow(() -> {
            duckCli.run(new String[] { "help" });
        });
    }
}
