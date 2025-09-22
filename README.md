<img width="481" height="444" alt="スクリーンショット 2025-09-22 23 49 03" src="https://github.com/user-attachments/assets/0aece11b-0a1a-470b-b777-676c8f09c61d" />


# 🦆 Commit Duck (Java)

**Commit Duck** is a terminal application that lets you raise a duck for each Git repository. 
As you make commits, your duck evolves. You can check its status anytime with the `duck status` command.

---

## ✨ Features

- **Repository-based Growth**  
  Each repository stores its own state in `.duck/state.properties`.  
  You can raise a different duck in each project.

- **Automatic Tracking**  
  A post-commit hook runs in the background on every `git commit`, automatically updating the commit count.

- **Global Execution**  
  Once installed, you can use the `duck` command from any directory.

- **Simple Commands**

  - `duck install` … Install the hook in the current repository
  - `duck status` … Show the current evolution state
  - `duck refresh` … Recalculate and update the total commit count
  - `duck help` … Show the list of commands

- **10 Evolution Stages**
  - 0–2: 🥚 Egg
  - 3–5: 🐣 Cracked Egg
  - 6–9: 🐣 Hatching
  - 10–14: 🦆 Duckling
  - 15–24: 💕 Matching
  - 25–39: 💒 Married
  - 40–59: 🍼 Nesting
  - 60–79: 🤒 Sickly
  - 80–99: � Injured
  - 100+: ☠️ Deceased

---

## 📦 Installation & Setup

### Prerequisites

- **Java 17+** is required
- `git` command must be available

### 🚀 One-Step Install (Recommended)

**The easiest way:**

```bash
curl -fsSL https://raw.githubusercontent.com/kumoshitaakira/Commit-Duck/main/install.sh | bash
```

or

```bash
wget -qO- https://raw.githubusercontent.com/kumoshitaakira/Commit-Duck/main/install.sh | bash
```

This will automatically:

- Clone the repository
- Set up the environment and build
- Install globally
- Set up your PATH

### 📋 Manual Installation

#### 1. Clone the repository

```bash
git clone https://github.com/your-username/Commit-Duck.git
cd Commit-Duck
```

#### 2. Install

**Global install (default, recommended):**

```bash
./setup.sh
```

**Local install:**

```bash
./setup.sh local
```

For global install, your PATH will be set automatically and you can use the `duck` command right away.

#### 3. Initialize in each repository

In any Git repository, run:

```bash
duck install
```

Now, your duck will grow automatically with each commit in that repository.

---

## 🔄 Update

Commit Duck provides a comprehensive update feature, updating everything below:

### 📦 What gets updated

- **Java source code** (full recompile)
- **ASCII art files** (all resource files)
- **Config files**
- **Execution scripts**
- **Dependencies**

### 🚀 How to update

#### If globally installed

```bash
cd /path/to/Commit-Duck
./update.sh
```

#### One-step update

```bash
curl -fsSL https://raw.githubusercontent.com/kumoshitaakira/Commit-Duck/main/install.sh | bash
```

If an existing install is detected, it will run in update mode automatically.

### 🔍 Update details

`update.sh` performs the following:

1. **Update source code**: Pull latest changes from Git
2. **Clean build**: Delete all build files and rebuild
3. **Resolve dependencies**: Get the latest libraries
4. **Resource check**: Ensure ASCII art files exist
5. **Update files**: Update JAR and scripts
6. **Detailed report**: Show file sizes and changes

---

## 🚀 Usage

**Quick setup (recommended):**

```bash
./setup.sh
```

**Manual setup:**

macOS/Linux:

```bash
chmod +x duck gradlew
./gradlew build installDist
./duck install
```

Windows (PowerShell/CMD):

```bat
gradlew.bat build installDist
duck.bat install
```

This will:

1. Check your environment (Java 17+, Git)
2. Automatically build the Java project
3. Set up a **post-commit hook** to update the commit count on every `git commit`

### 3. Confirm setup

```bash
./duck status
```

If you see your current commit count and duck evolution state, setup was successful!

---

## 🚀 How to use

Just develop and commit as usual.

### Check status

```bash
duck status
```

### Manually refresh commit count

```bash
duck refresh
```

### List commands

```bash
duck help
```

---

## 📁 Project Structure

Here is a typical directory/file structure for this tool.

```
Commit-Duck/
├─ README.md
├─ build.gradle         # Gradle build config
├─ settings.gradle      # Gradle project config
├─ duck                 # Command for macOS/Linux (shell script entry)
├─ duck.bat             # Command for Windows (batch entry)
└─ src/
   ├─ main/             # Main source code
   │  ├─ DuckCli.java   # CLI entry point / command dispatcher
   │  ├─ DuckState.java # State (commit count/stage) management
   │  ├─ Evolution.java # Commit count → evolution stage logic
   │  └─ GitUtils.java  # Git utilities (get commit count, etc.)
   └─ test/             # Test code
      ├─ DuckCliTest.java      # CLI tests
      ├─ DuckStateTest.java    # State management tests
      ├─ EvolutionTest.java    # Evolution logic tests
      └─ GitUtilsTest.java     # Git utility tests
```

### Build & Run

```bash
# Build the project
./gradlew build

# Create executable distribution
./gradlew installDist

# Run tests
./gradlew test

# Clean build
./gradlew clean build installDist
```

Complete setup (recommended):
```bash
# 1. Build the project
./gradlew build

# 2. Create executable distribution
./gradlew installDist

# 3. Use duck commands
./duck install
./duck status
```

Or use automatic setup:
```bash
./setup.sh
```

Java class files are generated in `build/classes/java/main`. The `duck` / `duck.bat` scripts call these classes via the `java` command.

---

## 🗂️ Stored Information

- Creates `.duck/state.properties` in the root of each repository
- Stores the following inside:
  - `commits`: Current commit count
  - `stage` : Evolution stage (EGG / DUCKLING / TEEN / ADULT / LEGEND)

Example:

```properties
commits=12
stage=TEEN
```

---

## ⚙️ Internal Workflow

1. Run `git commit`
2. The `post-commit` hook calls `duck refresh`
3. Get total commit count with `git rev-list --count HEAD`
4. Save to `.duck/state.properties`
5. When `duck status` is run, read the file and show ASCII art

---

## 🖼️ Diagram

```
User → git commit → git hook → .duck/state.properties → duck status
```

---

## 📝 Notes

- Each repository has its own independent duck.
- It is recommended to add the `.duck` directory to `.gitignore`.
- Requires a working `git` command.
- **Java 17+** is required (Gradle toolchain is set).

---

Enjoy hacking with your Commit Duck! 🦆

---

# 🦆 Commit Duck (Java)

**Commit Duck** は、Git リポジトリごとにアヒルを育てるターミナルアプリです。  
コミットを重ねるとアヒルが進化し、`duck status` コマンドでいつでも確認できます。

---

## ✨ 特徴

- **リポジトリ単位で成長**  
  各リポジトリの `.duck/state.properties` に独立して状態を保存。  
  複数プロジェクトで、それぞれのアヒルを育てられます。

- **自動計測**  
  通常の `git commit` 時に post-commit hook が裏で走り、コミット数を自動で更新。

- **グローバル実行**  
  一度インストールすれば、どのディレクトリからでも `duck` コマンドが使用可能。

- **簡単な操作**

  - `duck install` … 現在のリポジトリに hook をインストール
  - `duck status` … 現在の進化状態を表示
  - `duck refresh` … 現在のコミット総数を再計算して更新
  - `duck help` … コマンド一覧を表示

- **10 段階の進化**
  - 0–2: 🥚 Egg
  - 3–5: 🐣 Cracked Egg
  - 6–9: 🐣 Hatching
  - 10–14: 🦆 Duckling
  - 15–24: 💕 Matching
  - 25–39: 💒 Married
  - 40–59: 🍼 Nesting
  - 60–79: 🤒 Sickly
  - 80–99: � Injured
  - 100+: ☠️ Deceased

---

## 📦 インストール & セットアップ

### 前提条件

- **Java 17+** が必要です
- `git` コマンドが利用可能な環境

### 🚀 ワンステップインストール（推奨）

**最も簡単な方法:**

```bash
curl -fsSL https://raw.githubusercontent.com/kumoshitaakira/Commit-Duck/main/install.sh | bash
```

または

```bash
wget -qO- https://raw.githubusercontent.com/kumoshitaakira/Commit-Duck/main/install.sh | bash
```

これだけで以下が自動実行されます：

- リポジトリのクローン
- 環境構築とビルド
- グローバルインストール
- PATH 設定

### 📋 手動インストール

#### 1. リポジトリを取得

```bash
git clone https://github.com/your-username/Commit-Duck.git
cd Commit-Duck
```

#### 2. インストール

**グローバルインストール（デフォルト・推奨）:**

```bash
./setup.sh
```

**ローカルインストール:**

```bash
./setup.sh local
```

グローバルインストールの場合、PATH 設定も自動で行われ、すぐに `duck` コマンドが使用可能になります。

#### 3. 各リポジトリでの初期化

任意の Git リポジトリで以下を実行：

```bash
duck install
```

これで、そのリポジトリでコミット時に自動的にアヒルが成長します。

---

## 🔄 アップデート

Commit Duck は包括的なアップデート機能を提供し、以下をすべて更新します：

### 📦 アップデート内容

- **Java ソースコード**（完全再コンパイル）
- **アスキーアートファイル**（全リソースファイル）
- **設定ファイル**
- **実行スクリプト**
- **依存関係**

### 🚀 アップデート方法

#### グローバルインストール済みの場合

```bash
cd /path/to/Commit-Duck
./update.sh
```

#### ワンステップでアップデート

```bash
curl -fsSL https://raw.githubusercontent.com/kumoshitaakira/Commit-Duck/main/install.sh | bash
```

既存のインストールが検出された場合、自動的にアップデートモードで実行されます。

### 🔍 アップデート詳細

`update.sh` は以下の処理を実行します：

1. **ソースコードの更新**: Git から最新の変更を取得
2. **クリーンビルド**: 既存のビルドファイルをすべて削除してから再ビルド
3. **依存関係の解決**: 最新の依存ライブラリを取得
4. **リソース検証**: アスキーアートファイルの存在確認
5. **ファイル更新**: JAR ファイルと実行スクリプトを更新
6. **詳細レポート**: ファイルサイズや更新内容の変化を表示

---

## 🚀 使い方

**簡単セットアップ（推奨）:**

```bash
./setup.sh
```

**手動セットアップ:**

macOS/Linux:

```bash
chmod +x duck gradlew
./gradlew build installDist
./duck install
```

Windows (PowerShell/CMD):

```bat
gradlew.bat build installDist
duck.bat install
```

これにより以下が実行されます：

1. 環境チェック（Java 17+、Git）
2. Java プロジェクトが自動的にビルドされます
3. `git commit` のたびにコミット数を更新する **post-commit hook** が設定されます

### 3. セットアップ完了確認

```bash
./duck status
```

現在のコミット数とアヒルの進化状態が表示されれば成功です！

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

## 📁 プロジェクト構成

本ツールの典型的なディレクトリ・ファイル構成例です。

```
Commit-Duck/
├─ README.md
├─ build.gradle         # Gradleビルド設定
├─ settings.gradle      # Gradleプロジェクト設定
├─ duck                 # macOS/Linux用コマンド（シェルスクリプト）
├─ duck.bat             # Windows用コマンド（バッチファイル）
└─ src/
   ├─ main/             # メインソースコード
   │  ├─ DuckCli.java   # CLIエントリポイント/コマンド分岐
   │  ├─ DuckState.java # 状態（コミット数/進化段階）管理
   │  ├─ Evolution.java # コミット数→進化段階ロジック
   │  └─ GitUtils.java  # Gitユーティリティ（コミット数取得等）
   └─ test/             # テストコード
      ├─ DuckCliTest.java      # CLIテスト
      ├─ DuckStateTest.java    # 状態管理テスト
      ├─ EvolutionTest.java    # 進化ロジックテスト
      └─ GitUtilsTest.java     # Gitユーティリティテスト
```

### ビルド・実行

```bash
# ビルド
./gradlew build

# 実行可能ディストリビューション作成
./gradlew installDist

# テスト実行
./gradlew test

# クリーンビルド
./gradlew clean build installDist
```

完全なセットアップ（推奨）:
```bash
# 1. プロジェクトをビルド
./gradlew build

# 2. 実行可能ディストリビューションを作成
./gradlew installDist

# 3. duckコマンドを使用
./duck install
./duck status
```

または、自動セットアップを使用:
```bash
./setup.sh
```

Javaクラスファイルは `build/classes/java/main` に生成されます。`duck` / `duck.bat` スクリプトがこれらのクラスを `java` コマンド経由で呼び出します。

---

## 🗂️ 保存情報

- 各リポジトリ直下に `.duck/state.properties` を作成
- 以下の情報を保存：
  - `commits`: 現在のコミット数
  - `stage` : 進化段階（EGG / DUCKLING / TEEN / ADULT / LEGEND）

例：

```properties
commits=12
stage=TEEN
```

---

## ⚙️ 内部処理フロー

1. `git commit` 実行
2. post-commit hook が `duck refresh` を呼び出し
3. `git rev-list --count HEAD` でコミット数取得
4. `.duck/state.properties` に保存
5. `duck status` 実行時にファイルを読み込みアスキーアート表示

---

## 🖼️ 図解

```
ユーザー → git commit → git hook → .duck/state.properties → duck status
```

---

## 📝 注意事項

- 各リポジトリごとに独立したアヒルが存在します
- `.duck` ディレクトリは `.gitignore` への追加を推奨します
- `git` コマンドが利用可能な環境が必要です
- **Java 17+** が必要です（Gradle toolchain 設定済み）

---

ターミナルダックと一緒に楽しい開発を！🦆
