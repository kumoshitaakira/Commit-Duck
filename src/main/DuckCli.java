package main;

public class DuckCli {

    public DuckCli() {
        // デフォルトコンストラクタ
    }

    public static void main(String[] args) {
        // メインメソッド
        DuckCli cli = new DuckCli();
        cli.run(args);
    }

    public void run(String[] args) {
        // CLI実行ロジック
        if (args.length == 0) {
            showHelp();
            return;
        }

        String command = args[0];
        switch (command) {
            case "status":
                showStatus();
                break;
            case "help":
                showHelp();
                break;
            default:
                System.out.println("Unknown command: " + command);
                showHelp();
        }
    }

    private void showStatus() {
        System.out.println("Duck status: 🦆");
    }

    private void showHelp() {
        System.out.println("Duck CLI Commands:");
        System.out.println("  status - Show duck status");
        System.out.println("  help   - Show this help");
    }
}
