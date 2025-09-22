#!/usr/bin/env bash
set -euo pipefail

echo "🦆 Terminal Duck - ワンステップインストール"
echo "========================================"
echo "このスクリプトは以下を自動実行します:"
echo "1. リポジトリのクローン（必要に応じて）"
echo "2. 環境構築（Java確認、ビルド）"
echo "3. グローバルインストール"
echo "4. PATH設定"
echo ""

# 引数でリポジトリURLまたはディレクトリを指定可能
REPO_URL="https://github.com/kumoshitaakira/Commit-Duck.git"
INSTALL_DIR="$HOME/terminal-duck"

if [ $# -gt 0 ]; then
    if [[ "$1" =~ ^https?:// ]] || [[ "$1" =~ \.git$ ]]; then
        REPO_URL="$1"
        echo "指定されたリポジトリURL: $REPO_URL"
    else
        INSTALL_DIR="$1"
        echo "指定されたインストールディレクトリ: $INSTALL_DIR"
    fi
fi

echo "インストール先: $INSTALL_DIR"
echo ""

# リポジトリの準備
if [ -d "$INSTALL_DIR" ]; then
    echo "既存のディレクトリが見つかりました: $INSTALL_DIR"
    echo "アップデートモードで続行します..."
    cd "$INSTALL_DIR"
    
    # Gitリポジトリかチェック
    if git rev-parse --is-inside-work-tree >/dev/null 2>&1; then
        echo "既存のリポジトリを更新中..."
        git fetch origin
        git pull origin main 2>/dev/null || git pull origin master 2>/dev/null || {
            echo "⚠️  警告: 自動更新に失敗しました。手動で最新版を取得してください。"
        }
    else
        echo "⚠️  警告: $INSTALL_DIR はGitリポジトリではありません。"
        echo "手動でセットアップしてください。"
        exit 1
    fi
else
    echo "リポジトリをクローン中..."
    git clone "$REPO_URL" "$INSTALL_DIR"
    cd "$INSTALL_DIR"
fi

echo ""
echo "Terminal Duckをセットアップ中..."

# setup.shが存在するかチェック
if [ ! -f "setup.sh" ]; then
    echo "❌ エラー: setup.shが見つかりません。"
    echo "正しいCommit-Duckリポジトリではない可能性があります。"
    exit 1
fi

# セットアップ実行（デフォルトでグローバルインストール）
chmod +x setup.sh update.sh
echo "セットアップを実行中..."
if ./setup.sh; then
    SETUP_SUCCESS=true
else
    SETUP_SUCCESS=false
    echo "❌ セットアップに失敗しました。"
fi

if [ "$SETUP_SUCCESS" = true ]; then
    echo ""
    echo "🎉 ワンステップインストール完了！"
    echo ""
    echo "📋 インストール情報:"
    echo "  リポジトリ: $INSTALL_DIR"
    echo "  実行ファイル: $HOME/.local/bin/duck"
    echo "  データディレクトリ: $HOME/.local/share/commit-duck/"
    
    # インストール後の検証
    if command -v duck >/dev/null 2>&1; then
        echo "  ステータス: ✅ duck コマンドが利用可能"
    else
        echo "  ステータス: ⚠️  duck コマンドが見つかりません（PATHの更新が必要な可能性）"
        echo ""
        echo "新しいターミナルを開くか、以下を実行してください："
        echo "  export PATH=\"\$HOME/.local/bin:\$PATH\""
    fi
    
    # JARファイルの確認
    if [ -f "$HOME/.local/share/commit-duck/Commit-Duck.jar" ]; then
        jar_size=$(stat -f%z "$HOME/.local/share/commit-duck/Commit-Duck.jar" 2>/dev/null || stat -c%s "$HOME/.local/share/commit-duck/Commit-Duck.jar" 2>/dev/null || echo "unknown")
        echo "  JARファイルサイズ: $jar_size bytes"
    fi
    
    echo ""
    echo "🚀 今すぐ使用開始できます！"
    echo ""
    echo "📖 クイックスタート:"
    echo "1. 任意のGitリポジトリに移動"
    echo "   cd /path/to/your/git/repository"
    echo ""
    echo "2. Terminal Duckをそのリポジトリにインストール"
    echo "   duck install"
    echo ""
    echo "3. コミットしてアヒルを育てよう！"
    echo "   git add . && git commit -m \"コミット\""
    echo "   duck status  # アヒルの成長を確認"
    echo ""
    echo "📚 コマンド一覧:"
    echo "  duck install  - 現在のリポジトリにhookをインストール"
    echo "  duck status   - アヒルの状態を確認"
    echo "  duck refresh  - コミット数を再計算"
    echo "  duck help     - ヘルプを表示"
    echo ""
    echo "🔄 アップデート方法:"
    echo "  cd $INSTALL_DIR && ./update.sh"
    echo ""
    echo "または"
    echo "  curl -fsSL https://raw.githubusercontent.com/kumoshitaakira/Commit-Duck/main/install.sh | bash"
else
    echo ""
    echo "❌ インストールに失敗しました。"
    echo ""
    echo "トラブルシューティング:"
    echo "1. Java 17以上がインストールされているか確認"
    echo "   java -version"
    echo ""
    echo "2. 手動インストールを試行"
    echo "   cd $INSTALL_DIR"
    echo "   ./setup.sh"
    echo ""
    echo "3. ログを確認してエラーの詳細を調べる"
    exit 1
fi
