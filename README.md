# VelocityCord

Velocity と Discord を連携する Velocity プラグイン

Velocity plugin to integrate Velocity and Discord

# Build

```
mvn clean package
```

# Usage

- 一度起動すると NullPointerException が発生し、 plugins/VelocityCord/config.json が生成されるので Token とチャンネル ID を貼り付ける

- 再度起動し、`Discord Login Successful!`と表示されれば準備完了

- ゲーム内チャットは指定チャンネルへ転送され、チャンネル内に送信されたメッセージは全サーバーにブロードキャストされます

# Note

このプラグインはプライベートサーバー用に作成されました
