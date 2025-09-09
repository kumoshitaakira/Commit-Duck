# 🦆 Terminal Duck (Java)

**Terminal Duck** は、Git リポジトリごとにアヒルを育てるターミナルアプリです。  
コミットを重ねるとアヒルが進化し、`duck status` コマンドでいつでも確認できます。

---

## ✨ 特徴

- **リポジトリ単位で成長**  
  各リポジトリの `.duck/state.properties` に独立して状態を保存。  
  複数プロジェクトで、それぞれのアヒルを育てられます。

- **自動計測**  
  通常の `git commit` 時に post-commit hook が裏で走り、コミット数を自動で更新。

- **簡単な操作**

  - `duck install` … 初期セットアップ（Java ビルド＋ hook 設置）
  - `duck status` … 現在の進化状態を表示
  - `duck refresh` … 現在のコミット総数を再計算して更新
  - `duck help` … コマンド一覧を表示

- **進化段階（デフォルト 5 段階）**
  - 0–4: 🥚 Egg
  - 5–9: 🐣 Duckling
  - 10–24: 🦆 Teen
  - 25–49: 🦆 Adult
  - 50+: 🦆✨ Legend

---

## 📦 インストール & セットアップ

### 1. リポジトリを取得

```bash
git clone <your-repo-url> duck-term
cd duck-term
```

### 2. 初回セットアップ

macOS/Linux:

```bash
./duck install
```

Windows (PowerShell/CMD):

```bat
duck.bat install
```

これにより `git commit` のたびにコミット数を更新する **post-commit hook** が設定されます。

---

## 🚀 使い方

普段通りに開発・コミットしてください。

### 状態確認

```bash
duck status
```

### コミット数の再取得（手動）

```bash
duck refresh
```

### コマンド一覧

```bash
duck help
```

---

## � プロジェクト構成

以下は本ツールの代表的なディレクトリ/ファイル構成例です。


## 🧪 テスト実行

本リポジトリは Gradle を使用し、要求に合わせ `/test` ディレクトリをテストソースとして設定しています。

テスト起動:

```bash
./gradlew test   # (gradle wrapper 未生成の場合は `gradle test`)
```

`build/reports/tests/test/index.html` をブラウザで開くと結果を確認できます。

---
```
Commit-Duck/
├─ README.md
├─ duck             # macOS/Linux 用コマンド (シェルスクリプト実行エントリ)
├─ duck.bat         # Windows 用コマンド (バッチ実行エントリ)
└─ src/
  └─ main/
    ├─ DuckCli.java     # CLI エントリーポイント / コマンド分岐
    ├─ DuckState.java   # 状態(コミット数/ステージ)の読み書き管理
    ├─ Evolution.java   # コミット数→進化段階(ステージ)ロジック
    └─ GitUtils.java    # Git コミット数取得など Git 関連ユーティリティ
```

ビルド/実行方法や Java の配置はプロジェクト進行に合わせて調整してください。`duck` / `duck.bat` から `java` コマンドで上記クラスを呼び出す想定です。

---

## �🗂️ 保存される情報

- 各リポジトリのルートに `.duck/state.properties` を作成
- その中に以下を保存
  - `commits`: 現在のコミット数
  - `stage` : 進化段階（EGG / DUCKLING / TEEN / ADULT / LEGEND）

例:

```properties
commits=12
stage=TEEN
```

---

## ⚙️ 内部動作

1. `git commit` 実行
2. `post-commit` hook が `duck refresh` を呼び出す
3. `git rev-list --count HEAD` で総コミット数を取得
4. `.duck/state.properties` に保存
5. `duck status` 実行時に読み込み → アスキーアート表示

---

## 🖼️ イメージ図

```
ユーザ → git commit → git hook → .duck/state.properties → duck status
```

---

## 📝 注意事項

- 各リポジトリに独立したアヒルが存在します。
- `.duck` ディレクトリは `.gitignore` することを推奨します。
- `git` コマンドが利用可能な環境で動作します。
- Java 11+ が必要です。

---

Enjoy hacking with your terminal duck! 🦆
