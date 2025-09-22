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

Terminal Duck は包括的なアップデート機能を提供し、以下をすべて更新します：

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
./duck install
```

Windows (PowerShell/CMD):

```bat
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

以下は本ツールの代表的なディレクトリ/ファイル構成例です。

```
Commit-Duck/
├─ README.md
├─ build.gradle         # Gradle ビルド設定
├─ settings.gradle      # Gradle プロジェクト設定
├─ duck                 # macOS/Linux 用コマンド (シェルスクリプト実行エントリ)
├─ duck.bat             # Windows 用コマンド (バッチ実行エントリ)
└─ src/
   ├─ main/             # メインソースコード
   │  ├─ DuckCli.java   # CLI エントリーポイント / コマンド分岐
   │  ├─ DuckState.java # 状態(コミット数/ステージ)の読み書き管理
   │  ├─ Evolution.java # コミット数→進化段階(ステージ)ロジック
   │  └─ GitUtils.java  # Git コミット数取得など Git 関連ユーティリティ
   └─ test/             # テストコード
      ├─ DuckCliTest.java      # CLI機能のテスト
      ├─ DuckStateTest.java    # 状態管理のテスト
      ├─ EvolutionTest.java    # 進化ロジックのテスト
      └─ GitUtilsTest.java     # Git操作のテスト
```

### ビルド・実行方法

```bash
# プロジェクトのビルド
gradle build

# テスト実行
gradle test

# クリーンビルド
gradle clean build
```

Java の実行可能ファイルは `build/classes/java/main` に生成されます。`duck` / `duck.bat` から `java` コマンドで上記クラスを呼び出す想定です。

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
  └─ test/
    ├─ DuckCliTest.java      # CLI機能のテスト
    ├─ DuckStateTest.java    # 状態管理のテスト
    ├─ EvolutionTest.java    # 進化ロジックのテスト
    └─ GitUtilsTest.java     # Git操作のテスト
  assets/
    └─ demo.md          # アスキーアートなどのリソース配置場所
build/
  └─ classes/java/main/  # ビルド成果物配置場所 (実行可能クラス)
  └─ classes/java/test/   # テストクラスのビルド成果物配置場所
  └─ libs/                # ビルド成果物配置場所 (JARファイル)
  └─ reports/             # テストレポート配置場所
  └─ tmp/                 # ビルド一時ファイル配置場所
  └─ ...                  # その他 Gradle ビルド関連ファイル
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
- **Java 17+** が必要です（Gradle toolchain 設定済み）。

---

Enjoy hacking with your terminal duck! 🦆
