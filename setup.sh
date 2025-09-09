#!/usr/bin/env bash
set -euo pipefail

echo "🦆 Terminal Duck - 初回セットアップ"
echo "=============================="

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

if ! git rev-parse --is-inside-work-tree >/dev/null 2>&1; then
    echo "❌ エラー: Gitリポジトリ内で実行してください。"
    exit 1
fi

echo "✅ Git環境OK"

# 実行権限設定
echo "実行権限を設定中..."
chmod +x duck duck.bat gradlew

# インストール実行
echo "Terminal Duckをインストール中..."
./duck install

echo ""
echo "🎉 セットアップ完了！"
echo ""
echo "使い方:"
echo "  ./duck status   - 現在のアヒルの状態を確認"
echo "  ./duck refresh  - コミット数を再計算"
echo "  ./duck help     - ヘルプを表示"
echo ""
echo "これからコミットするたびにアヒルが成長します！"
