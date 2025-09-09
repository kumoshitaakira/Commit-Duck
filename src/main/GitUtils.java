package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GitUtils {

    public static int getCommitCount() throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("git", "rev-list", "--count", "HEAD");
        Process process = pb.start();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String output = reader.readLine();
            if (output != null && !output.trim().isEmpty()) {
                return Integer.parseInt(output.trim());
            }
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new IOException("Git command failed with exit code: " + exitCode);
        }

        return 0;
    }

    public static boolean isGitRepository() {
        try {
            ProcessBuilder pb = new ProcessBuilder("git", "rev-parse", "--git-dir");
            Process process = pb.start();
            int exitCode = process.waitFor();
            return exitCode == 0;
        } catch (IOException | InterruptedException e) {
            return false;
        }
    }

    public static String getCurrentBranch() throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("git", "branch", "--show-current");
        Process process = pb.start();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String output = reader.readLine();
            if (output != null && !output.trim().isEmpty()) {
                return output.trim();
            }
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new IOException("Git command failed with exit code: " + exitCode);
        }

        return "unknown";
    }
}
