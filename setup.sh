#!/usr/bin/env bash
set -euo pipefail

echo "🦆 Terminal Duck - 初回セットアップ"
echo "=============================="

# 引数の確認
GLOBAL_INSTALL=true  # デフォルトでグローバルインストール
if [ "${1:-}" = "local" ]; then
    GLOBAL_INSTALL=false
    echo "ローカルインストールモードで実行します"
else
    echo "グローバルインストールモードで実行します"
    echo "（ローカルインストールの場合は './setup.sh local' を実行）"
fi

# シェル設定ファイルの自動検出
detect_shell_config() {
    if [ -n "${ZSH_VERSION:-}" ] || [ "$SHELL" = "/bin/zsh" ] || [ "$SHELL" = "/usr/bin/zsh" ]; then
        echo "$HOME/.zshrc"
    elif [ -n "${BASH_VERSION:-}" ] || [ "$SHELL" = "/bin/bash" ] || [ "$SHELL" = "/usr/bin/bash" ]; then
        if [ -f "$HOME/.bashrc" ]; then
            echo "$HOME/.bashrc"
        else
            echo "$HOME/.bash_profile"
        fi
    elif [ "$SHELL" = "/bin/fish" ] || [ "$SHELL" = "/usr/bin/fish" ]; then
        echo "$HOME/.config/fish/config.fish"
    else
        # デフォルトはbash profile
        echo "$HOME/.bash_profile"
    fi
}

# Java バージョンチェック
echo "Java環境をチェック中..."
if ! command -v java >/dev/null 2>&1; then
    echo "❌ エラー: Javaが見つかりません。Java 17以上をインストールしてください。"
    exit 1
fi

JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f1)
if [ "$JAVA_VERSION" -lt 17 ]; then
    echo "❌ エラー: Java 17以上が必要です。現在のバージョン: $JAVA_VERSION"
    exit 1
fi

echo "✅ Java環境OK (バージョン: $JAVA_VERSION)"

# Git環境チェック
echo "Git環境をチェック中..."
if ! command -v git >/dev/null 2>&1; then
    echo "❌ エラー: Gitが見つかりません。Gitをインストールしてください。"
    exit 1
fi

echo "✅ Git環境OK"

# 実行権限設定
echo "実行権限を設定中..."
chmod +x duck duck.bat gradlew

# JAR ファイルをビルド
echo "Terminal Duckをビルド中..."
./gradlew jar -q

# グローバルインストールの場合
if [ "$GLOBAL_INSTALL" = true ]; then
    INSTALL_DIR="$HOME/.local/bin"
    DUCK_HOME="$HOME/.local/share/commit-duck"
    SHELL_CONFIG=$(detect_shell_config)
    
    echo "グローバルインストール中..."
    
    # インストールディレクトリを作成
    mkdir -p "$INSTALL_DIR"
    mkdir -p "$DUCK_HOME"
    
    # JARファイルをコピー
    cp "build/libs/Commit-Duck.jar" "$DUCK_HOME/"
    
    # グローバル実行スクリプトを作成
    cat > "$INSTALL_DIR/duck" <<EOF
#!/usr/bin/env bash
set -euo pipefail

DUCK_HOME="\$HOME/.local/share/commit-duck"
JAR_FILE="\$DUCK_HOME/Commit-Duck.jar"

if [ ! -f "\$JAR_FILE" ]; then
    echo "❌ エラー: Commit-Duck.jarが見つかりません。再インストールしてください。"
    exit 1
fi

# Gitリポジトリ内かチェック
if ! git rev-parse --is-inside-work-tree >/dev/null 2>&1; then
    echo "❌ エラー: Gitリポジトリ内で実行してください。"
    exit 1
fi

run_java() {
    java -jar "\$JAR_FILE" "\$@"
}

install_hooks() {
    HOOK_DIR=".git/hooks"
    mkdir -p "\$HOOK_DIR"
    cat > "\$HOOK_DIR/post-commit" <<'EOS'
#!/usr/bin/env bash
set -e
# duck コマンドがあればrefresh実行（静かに失敗許容）
if command -v duck >/dev/null 2>&1; then
    duck refresh >/dev/null 2>&1 || true
fi
EOS
    chmod +x "\$HOOK_DIR/post-commit"
    echo "Installed .git/hooks/post-commit"
    echo "Done."
}

case "\${1-}" in
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
        echo "Unknown command: \$1"
        echo "duck install | status | refresh | help"
        exit 1
        ;;
esac
EOF
    
    chmod +x "$INSTALL_DIR/duck"
    
    # PATHの自動設定
    echo "PATH設定を自動構成中..."
    
    # 既にPATHが設定されているかチェック
    if ! echo "$PATH" | grep -q "$HOME/.local/bin"; then
        # シェル設定ファイルに追記（重複チェック付き）
        if [ -f "$SHELL_CONFIG" ]; then
            if ! grep -q "export PATH.*\.local/bin" "$SHELL_CONFIG"; then
                echo "" >> "$SHELL_CONFIG"
                echo "# Terminal Duck - グローバルコマンドパス" >> "$SHELL_CONFIG"
                echo "export PATH=\"\$HOME/.local/bin:\$PATH\"" >> "$SHELL_CONFIG"
                echo "✅ PATH設定を $SHELL_CONFIG に追加しました"
            else
                echo "✅ PATH設定は既に存在します"
            fi
        else
            # 設定ファイルが存在しない場合は作成
            mkdir -p "$(dirname "$SHELL_CONFIG")"
            echo "# Terminal Duck - グローバルコマンドパス" > "$SHELL_CONFIG"
            echo "export PATH=\"\$HOME/.local/bin:\$PATH\"" >> "$SHELL_CONFIG"
            echo "✅ PATH設定を $SHELL_CONFIG に新規作成しました"
        fi
        
        # 現在のセッションでもPATHを更新
        export PATH="$HOME/.local/bin:$PATH"
        echo "✅ 現在のセッションのPATHも更新しました"
    else
        echo "✅ PATH設定は既に適用されています"
    fi
    
    echo ""
    echo "🎉 グローバルインストール完了！"
    echo ""
    echo "📋 設定内容:"
    echo "  実行ファイル: $INSTALL_DIR/duck"
    echo "  データ:     $DUCK_HOME/Commit-Duck.jar"
    echo "  設定ファイル: $SHELL_CONFIG"
    echo ""
    echo "🚀 すぐに使用できます！新しいターミナルを開く必要はありません。"
    echo ""
    echo "使い方:"
    echo "  duck install  - 現在のリポジトリにhookをインストール"
    echo "  duck status   - 現在のアヒルの状態を確認"
    echo "  duck refresh  - コミット数を再計算"
    echo "  duck help     - ヘルプを表示"
else
    # ローカルインストール（従来の動作）
    echo "ローカルインストール中..."
    if git rev-parse --is-inside-work-tree >/dev/null 2>&1; then
        ./duck install
    else
        echo "⚠️  警告: Gitリポジトリ外のため、hookはインストールされませんでした。"
    fi
    
    echo ""
    echo "🎉 ローカルセットアップ完了！"
    echo ""
    echo "使い方:"
    echo "  ./duck status   - 現在のアヒルの状態を確認"
    echo "  ./duck refresh  - コミット数を再計算"
    echo "  ./duck help     - ヘルプを表示"
    echo ""
    echo "グローバルインストールを行う場合:"
    echo "  ./setup.sh"
fi
echo ""
echo "これからコミットするたびにアヒルが成長します！"
