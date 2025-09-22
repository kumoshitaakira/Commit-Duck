// package duck;

// public class EvolutionDebugger {
// public static void main(String[] args) {
// // 各ステージの上限値を確認
// System.out.println("ステージの上限値:");
// for (Evolution.Stage stage : Evolution.Stage.values()) {
// System.out.println(stage.name() + ": " + stage.getStageLimit());
// }

// System.out.println("\n動作テスト:");

// // EGGステージのテスト
// System.out.println("EGG(3)ステージ:");
// System.out.println(" commits=2, current=EGG -> " + Evolution.decideStage(2,
// Evolution.Stage.EGG));
// System.out.println(" commits=3, current=EGG -> " + Evolution.decideStage(3,
// Evolution.Stage.EGG));

// // CRACKED_EGGステージのテスト
// System.out.println("CRACKED_EGG(5)ステージ:");
// System.out.println(" commits=4, current=CRACKED_EGG -> " +
// Evolution.decideStage(4, Evolution.Stage.CRACKED_EGG));
// System.out.println(" commits=5, current=CRACKED_EGG -> " +
// Evolution.decideStage(5, Evolution.Stage.CRACKED_EGG));

// // HATCHINGステージのテスト
// System.out.println("HATCHING(8)ステージ:");
// System.out.println(" commits=7, current=HATCHING -> " +
// Evolution.decideStage(7, Evolution.Stage.HATCHING));
// System.out.println(" commits=8, current=HATCHING -> " +
// Evolution.decideStage(8, Evolution.Stage.HATCHING));

// // DUCKLINGステージのテスト
// System.out.println("DUCKLING(13)ステージ:");
// System.out.println(" commits=12, current=DUCKLING -> " +
// Evolution.decideStage(12, Evolution.Stage.DUCKLING));
// System.out.println(" commits=13, current=DUCKLING -> " +
// Evolution.decideStage(13, Evolution.Stage.DUCKLING));
// }
// }
