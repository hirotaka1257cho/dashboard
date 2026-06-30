監視ダッシュボード
登録した監視対象URLに対して定期的にHTTPリクエストを送信し、死活監視を行うWebアプリです。
使用技術

Java 21
Spring Boot
Spring Data JPA
PostgreSQL
Thymeleaf
JavaScript (Ajax, Web Audio API)
Docker / Docker Compose
Nginx
AWS EC2

機能一覧

監視対象の登録・一覧表示
定期的なHTTP死活監視（デフォルト1分ごと）
障害の自動検知・記録・復旧管理
障害一覧の絞り込み検索（対象名・会社名・発生日時）
障害発生時のアラーム音通知
画面の自動更新（30秒〜5分）
ページネーション

セットアップ手順
前提条件

Docker / Docker Composeがインストールされていること

手順
bash# リポジトリをクローン
git clone https://github.com/hirotaka1257cho/dashboard.git
cd dashboard

# .envファイルを作成
DB_HOST=db
DB_PORT=5432
DB_NAME=dashboard
DB_USER=postgres
DB_PASS=任意のパスワード

# コンテナを起動
docker-compose up -d --build

# テーブルを作成
docker-compose exec db psql -U postgres -d dashboard -f /docker-entrypoint-initdb.d/schema.sql
アクセス
http://localhost/incidents
画面説明

/incidents → 障害一覧（検索・アラーム音・自動更新）
/targets → 監視対象一覧
/targets/new → 監視対象登録
