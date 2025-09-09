package main;

import java.io.*;
import java.util.Properties;

public class DuckState {
    private int commits;
    private String stage;
    private static final String STATE_FILE = ".duck/state.properties";

    public DuckState() {
        this.commits = 0;
        this.stage = "EGG";
    }

    public DuckState(int commits, String stage) {
        this.commits = commits;
        this.stage = stage;
    }

    public int getCommits() {
        return commits;
    }

    public void setCommits(int commits) {
        this.commits = commits;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public void save() throws IOException {
        // 状態をファイルに保存
        Properties props = new Properties();
        props.setProperty("commits", String.valueOf(commits));
        props.setProperty("stage", stage);

        File stateDir = new File(".duck");
        if (!stateDir.exists()) {
            stateDir.mkdirs();
        }

        try (FileOutputStream fos = new FileOutputStream(STATE_FILE)) {
            props.store(fos, "Duck state");
        }
    }

    public static DuckState load() throws IOException {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(STATE_FILE)) {
            props.load(fis);
            int commits = Integer.parseInt(props.getProperty("commits", "0"));
            String stage = props.getProperty("stage", "EGG");
            return new DuckState(commits, stage);
        } catch (FileNotFoundException e) {
            return new DuckState(); // デフォルト状態を返す
        }
    }
}
