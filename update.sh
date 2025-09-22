#!/usr/bin/env bash
set -euo pipefail

echo "🦆 Terminal Duck - アップデート"
echo "=========================="

# 現在のディレクトリがCommit-Duckリポジトリかチェック
if [ ! -f "gradlew" ] || [ ! -f "setup.sh" ]; then
    echo "❌ エラー: Commit-Duckリポジトリ内で実行してください。"
    echo "正しい手順:"
    echo "  cd /path/to/Commit-Duck"
    echo "  ./update.sh"
    exit 1
fi

# 最新の変更を取得
echo "最新の変更を取得中..."
if git rev-parse --is-inside-work-tree >/dev/null 2>&1; then
    echo "Gitリポジトリから最新の変更を取得しています..."
    git fetch origin
    
    # 現在のブランチをチェック
    current_branch=$(git rev-parse --abbrev-ref HEAD)
    if [ "$current_branch" != "main" ] && [ "$current_branch" != "master" ]; then
        echo "⚠️  注意: 現在のブランチは '$current_branch' です。"
        echo "メインブランチでアップデートすることをお勧めします。"
        read -p "続行しますか？ (y/N): " -n 1 -r
        echo
        if [[ ! $REPLY =~ ^[Yy]$ ]]; then
            echo "アップデートを中止しました。"
            exit 0
        fi
    else
        echo "メインブランチから最新の変更をマージ中..."
        git pull origin "$current_branch" || {
            echo "⚠️  警告: Gitでのプルに失敗しました。手動で解決してください。"
        }
    fi
fi

# グローバルインストールされているかチェック
if [ ! -f "$HOME/.local/share/commit-duck/Commit-Duck.jar" ] || [ ! -f "$HOME/.local/bin/duck" ]; then
    echo "❌ エラー: グローバルインストールが見つかりません。"
    echo "再インストールしますか？"
    echo ""
    read -p "グローバルインストールを実行しますか？ (y/N): " -n 1 -r
    echo
    if [[ $REPLY =~ ^[Yy]$ ]]; then
        echo "グローバルインストールを実行します..."
        ./setup.sh
        exit 0
    else
        echo "先に './setup.sh' を実行してください。"
        exit 1
    fi
fi

# アップデート前の状態を記録
echo "現在のバージョン情報を記録中..."
OLD_JAR_SIZE="unknown"
OLD_JAR_DATE="unknown"
if [ -f "$HOME/.local/share/commit-duck/Commit-Duck.jar" ]; then
    OLD_JAR_SIZE=$(stat -f%z "$HOME/.local/share/commit-duck/Commit-Duck.jar" 2>/dev/null || stat -c%s "$HOME/.local/share/commit-duck/Commit-Duck.jar" 2>/dev/null || echo "unknown")
    OLD_JAR_DATE=$(stat -f%m "$HOME/.local/share/commit-duck/Commit-Duck.jar" 2>/dev/null || stat -c%Y "$HOME/.local/share/commit-duck/Commit-Duck.jar" 2>/dev/null || echo "unknown")
fi

# 完全なクリーンビルドを実行
echo "Terminal Duckを完全再ビルド中..."
echo "  - 既存ビルドファイルをクリーン"
./gradlew clean -q

echo "  - 依存関係を解決"
./gradlew dependencies --configuration runtimeClasspath -q >/dev/null 2>&1 || true

echo "  - Javaソースコードをコンパイル"
./gradlew compileJava -q

echo "  - リソースファイル（アスキーアートなど）を処理"
./gradlew processResources -q

echo "  - JARファイルを生成"
./gradlew jar -q

# JARファイル内容の確認
echo "  - ビルド結果を検証"
if [ ! -f "build/libs/Commit-Duck.jar" ]; then
    echo "❌ エラー: JARファイルのビルドに失敗しました。"
    exit 1
fi

# リソースファイルが含まれているか確認
echo "  - リソースファイルの存在確認"
resource_count=$(jar tf "build/libs/Commit-Duck.jar" | grep -E "duck_stage[0-9]+\.txt" | wc -l | tr -d ' ')
if [ "$resource_count" -lt 10 ]; then
    echo "⚠️  警告: アスキーアートファイルが不足している可能性があります（検出: $resource_count/10）"
    echo "リソースファイル一覧:"
    jar tf "build/libs/Commit-Duck.jar" | grep -E "duck_stage[0-9]+\.txt" || echo "  なし"
else
    echo "✅ アスキーアートファイル: $resource_count ファイル確認済み"
fi

echo ""
echo "アップデート中..."

# JAR ファイルの更新
echo "  - JARファイルを更新"
cp "build/libs/Commit-Duck.jar" "$HOME/.local/share/commit-duck/"

# グローバル実行スクリプトの更新
echo "  - 実行スクリプトを更新"
INSTALL_DIR="$HOME/.local/bin"
cat > "$INSTALL_DIR/duck" <<'EOF'
#!/usr/bin/env bash
set -euo pipefail

DUCK_HOME="$HOME/.local/share/commit-duck"
JAR_FILE="$DUCK_HOME/Commit-Duck.jar"

if [ ! -f "$JAR_FILE" ]; then
    echo "❌ エラー: Commit-Duck.jarが見つかりません。再インストールしてください。"
    exit 1
fi

# Gitリポジトリ内かチェック
if ! git rev-parse --is-inside-work-tree >/dev/null 2>&1; then
    echo "❌ エラー: Gitリポジトリ内で実行してください。"
    exit 1
fi

run_java() {
    java -jar "$JAR_FILE" "$@"
}

install_hooks() {
    HOOK_DIR=".git/hooks"
    mkdir -p "$HOOK_DIR"
    cat > "$HOOK_DIR/post-commit" <<'EOS'
#!/usr/bin/env bash
set -e
# duck コマンドがあればrefresh実行（静かに失敗許容）
if command -v duck >/dev/null 2>&1; then
    duck refresh >/dev/null 2>&1 || true
fi
EOS
    chmod +x "$HOOK_DIR/post-commit"
    echo "Installed .git/hooks/post-commit"
    echo "Done."
}

case "${1-}" in
    install)
        install_hooks
        ;;
    refresh)
        run_java refresh
        ;;
    status)
        run_java status
        ;;
    help|"")
        echo "duck install | status | refresh | help"
        ;;
    *)
        echo "Unknown command: $1"
        echo "duck install | status | refresh | help"
        exit 1
        ;;
esac
EOF

chmod +x "$INSTALL_DIR/duck"

# アップデート完了後の状態確認
NEW_JAR_SIZE="unknown"
NEW_JAR_DATE="unknown"
if [ -f "$HOME/.local/share/commit-duck/Commit-Duck.jar" ]; then
    NEW_JAR_SIZE=$(stat -f%z "$HOME/.local/share/commit-duck/Commit-Duck.jar" 2>/dev/null || stat -c%s "$HOME/.local/share/commit-duck/Commit-Duck.jar" 2>/dev/null || echo "unknown")
    NEW_JAR_DATE=$(stat -f%m "$HOME/.local/share/commit-duck/Commit-Duck.jar" 2>/dev/null || stat -c%Y "$HOME/.local/share/commit-duck/Commit-Duck.jar" 2>/dev/null || echo "unknown")
fi

echo ""
echo "🎉 アップデート完了！"
echo ""
echo "📋 アップデート詳細:"
echo "  JARファイル: $HOME/.local/share/commit-duck/Commit-Duck.jar"
echo "  実行スクリプト: $HOME/.local/bin/duck"
echo ""
echo "📊 変更内容:"
if [ "$OLD_JAR_SIZE" != "unknown" ] && [ "$NEW_JAR_SIZE" != "unknown" ]; then
    size_diff=$((NEW_JAR_SIZE - OLD_JAR_SIZE))
    if [ "$size_diff" -gt 0 ]; then
        echo "  サイズ変化: $OLD_JAR_SIZE → $NEW_JAR_SIZE bytes (+$size_diff)"
    elif [ "$size_diff" -lt 0 ]; then
        size_diff=$((-size_diff))
        echo "  サイズ変化: $OLD_JAR_SIZE → $NEW_JAR_SIZE bytes (-$size_diff)"
    else
        echo "  サイズ変化: なし ($NEW_JAR_SIZE bytes)"
    fi
else
    echo "  新しいサイズ: $NEW_JAR_SIZE bytes"
fi
echo "  更新日時: $(date -r "$NEW_JAR_DATE" 2>/dev/null || echo "$(date)")"
echo "  リソースファイル: $resource_count アスキーアートファイル"
echo ""
echo "✅ 以下がアップデートされました:"
echo "  - Javaソースコード（完全再コンパイル）"
echo "  - アスキーアートファイル"
echo "  - 設定ファイル"
echo "  - 実行スクリプト"
echo ""
echo "🚀 最新バージョンのTerminal Duckが利用可能です！"
echo ""
echo "使い方:"
echo "  duck install  - 新しいリポジトリにhookをインストール"
echo "  duck status   - アヒルの状態を確認"
echo "  duck refresh  - コミット数を再計算"
