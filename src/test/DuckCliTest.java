package test;

import main.DuckCli;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DuckCliTest {

    @Test
    public void testMainWithEmptyArgs() {
        // 空の引数でmainメソッドを呼び出してもエラーが発生しないことを確認
        assertDoesNotThrow(() -> {
            DuckCli.main(new String[] {});
        });
    }

    @Test
    public void testMainWithStatusCommand() {
        // statusコマンドでmainメソッドを呼び出してもエラーが発生しないことを確認
        assertDoesNotThrow(() -> {
            DuckCli.main(new String[] { "status" });
        });
    }

    @Test
    public void testMainWithHelpCommand() {
        // helpコマンドでmainメソッドを呼び出してもエラーが発生しないことを確認
        assertDoesNotThrow(() -> {
            DuckCli.main(new String[] { "help" });
        });
    }

    @Test
    public void testMainWithInstallCommand() {
        // installコマンドでmainメソッドを呼び出してもエラーが発生しないことを確認
        assertDoesNotThrow(() -> {
            DuckCli.main(new String[] { "install" });
        });
    }

    @Test
    public void testMainWithRefreshCommand() {
        // refreshコマンドでmainメソッドを呼び出してもエラーが発生しないことを確認
        assertDoesNotThrow(() -> {
            DuckCli.main(new String[] { "refresh" });
        });
    }
}
