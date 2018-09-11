# 【Android】端末を絞り込んでプッシュ通知を使ってみましょう！（セグメント配信）
*2016/10/19作成*

![画像1](/readme-img/001.png)

## 概要
* [ニフティクラウドmobile backend](https://mbaas.nifcloud.com/)の『プッシュ通知』機能とプッシュ通知を受信する際、プッシュ通知の『端末の絞り込み配信』機能を実装したサンプルプロジェクトです
* 簡単な操作ですぐに [ニフティクラウドmobile backend](https://mbaas.nifcloud.com/)の機能を体験いただけます★☆
* このサンプルはAndroid 4.0以降に対応しています

## ニフティクラウドmobile backendって何？？
スマートフォンアプリのバックエンド機能（プッシュ通知・データストア・会員管理・ファイルストア・SNS連携・位置情報検索・スクリプト）が**開発不要**、しかも基本**無料**(注1)で使えるクラウドサービス！

詳しくは[こちら](https://mbaas.nifcloud.com/price.htm)をご覧ください

![画像2](/readme-img/002.png)

## 動作環境

* Android Studio ver. 2.1.2
* Android 6.0
 * このサンプルアプリは、プッシュ通知を受信する必要があるため実機ビルドが必要です

※上記内容で動作確認をしています

## プッシュ通知の仕組み

* ニフティクラウドmobile backendのプッシュ通知は、各プラットフォームが提供している通知サービスを利用しています
 * Androidの通知サービス __FCM（Firebase Cloud Messaging）__

 ![画像a1](/readme-img/a001.png)

 ※ FCMはGCM(Google Cloud Messaging)の新バージョンです。既にGCMにてプロジェクトの作成・GCMの有効化設定を終えている場合は、継続してご利用いただくことが可能です。新規でGCMをご利用いただくことはできませんので、あらかじめご了承ください。

* 上図のように、アプリ（Android Studio）・サーバー（ニフティクラウドmobile backend）・通知サービス（FCM/GCM）の間でやり取りを行うため、認証が必要になります
 * 認証に必要なプッシュ通知のAPIキーおよびSenderIDは手順にて説明します。

## 手順
### 0.プッシュ通知機能を使うための準備

ニフティクラウド mobile backendと連携させるためのAPIキーを取得する必要があります。 以下のドキュメントを参考に、FCMプロジェクトの作成とAPIキーの取得を行ってください。

__[mobile backendとFCMの連携に必要な設定](https://mbaas.nifcloud.com/doc/current/tutorial/push_setup_android.html)__

### 1. [ニフティクラウドmobile backend](https://mbaas.nifcloud.com/)の準備

* 上記リンクから会員登録（無料）をします
* 登録後、ログインをすると下図のように「アプリの新規作成」画面が出ますので、アプリを作成します

![画像3](/readme-img/mBassNewProject.png)

* アプリ作成されると下図のような画面になります
* この２種類のAPIキー（アプリケーションキーとクライアントキー）は先ほどインポートしたAndroidStudioで作成するAndroidアプリにニフティクラウドmobile backendの紐付けるため、あとで使います

![画像4](/readme-img/mBassAPIkey.png)


* アプリ設定開いてプッシュ通知の設定をします
   * 「プッシュ通知の許可」で「許可する」選択、「保存する」をクリックします
   * 「Androidプッシュ通知」の「APIキー」には、FCMでプロジェクト作成時に発行された「サーバーキー」を記入し、「保存する」をクリックします

![画像6](/readme-img/mBassPushEnv.png)

### 2. [GitHub](https://github.com/NIFCloud-mbaas/KotlinSegmentPushApp)からサンプルプロジェクトのダウンロード

* プロジェクトの[Githubページ](https://github.com/NIFCloud-mbaas/KotlinSegmentPushApp)から「Clone or download」＞「Download ZIP」をクリックします
* プロジェクトを解凍します

### 3. AndroidStudioでアプリを起動

* AndroidStudioを開き、解凍したプロジェクトを選択します

![画像7](/readme-img/SelectProject.png)

* プロジェクトを開きます。`MainActivity.kt`ファイルを開きます。

![画像8](/readme-img/ProjectDesign.png)

### 4. APIキーの設定

* `MainActivity.kt`を編集します
* 先程[ニフティクラウドmobile backend](https://mbaas.nifcloud.com/)のダッシュボード上で確認したAPIキーを貼り付けます

![画像9](/readme-img/AndroidAPIkey.png)

* それぞれ`YOUR_APPLICATION_KEY`と`YOUR_CLIENT_KEY`の部分を書き換えます
 * このとき、ダブルクォーテーション（`"`）を消さないように注意してください！

### 5. AndroidのSender IDキーの設定

* `MainActivity.kt`を編集します

![画像10](/readme-img/FCMAPIkey.png)

* `ANDROID_SENDER_ID`の部分を、FCMでプロジェクト作成時に発行された「Sender ID」に書き換えます
 * このとき、ダブルクォーテーション（`"`）を消さないように注意してください！

### 6. 動作確認

* AndroidStudioからビルドする。
 * 「プロジェクト場所」\app\build\outputs\apk\ ***.apk ファイルが生成される

* インストールしたアプリを起動します
 * プッシュ通知の許可を求めるアラートが出たら、必ず許可してください！

![画像11](/readme-img/Action1.png)

* アプリを起動すると、画面に登録した端末情報が表示されます。
   - `ObjectId`: 登録した端末のクラス内の管理用文字列
   - `appVersion`: アプリのバージョン
   - `channels`: チャネル（A, B, C, D設定可能）
   - `deviceToken`: レジスタレーションID、GCMが発行したもの
   - `sdkVersion`: 利用しているAndroidSDKのバージョン
   - `timeZone`: タイムゾーン
   - `createDate`: 作成時
   - `updateDate`: アプリが起動時

* [ニフティクラウドmobile backend](https://mbaas.nifcloud.com/)のダッシュボードから「データストア (installationクラス(端末情報))」を確認してみましょう！

![画像12](/readme-img/Action2.png)

* 次に絞り込み条件を指定するために、端末情報を登録します。
* Channels情報を登録しましょう！アプリのチャネルを「A」に設定してください。

![画像13](/readme-img/Action3.png)

* カスタム端末情報`Prefectures`も登録します。入力で`Tokyo`を入れてください。
* 「SAVE」ボタンをクリックします。
* データストア(installationクラス(端末情報))からchannels = ["A"]、Prefectures = Tokyo の登録値を確認出来ます。

![画像14](/readme-img/Action4.png)

### 7. プッシュ通知を送りましょう！

* いよいよです！実際にプッシュ通知を送ってみましょう！
* ニフティクラウドmobile backendのダッシュボードで「プッシュ通知」＞「＋新しいプッシュ通知」をクリックします
* プッシュ通知のフォームが開かれます
* 必要な項目を入力してプッシュ通知を作成します
  * `タイトル`, `メッセージ`を追記
  * `今すぐ配信`をそのまま

![画像15](/readme-img/push1.png)

* フォームの下に移動します
  * `Android端末に配信する`と選択します
  * `配信端末`のところ`installationクラスから絞込み`を選択します
  * 絞り込み条件には以下のように設定します
    * `channels`を選択し、`A`を入力し、`のいずれかと同じ`を選択します。
    * `Prefectures`を選択し、`Tokyo`を入力し、`文字列`,`と同じ`を選択します。
* 配信対象が`1端末に向けて送信されます`と表示していることを確認します。
* `プッシュ通知を作成する`と選択し、プッシュ通知を登録します。

![画像16](/readme-img/push2.png)

* Android端末からプッシュ通知を確認しましょう！
* 少し待つとプッシュ通知が届きます！

![画像14](/readme-img/Action5.png)

* ダッシュボードからのプッシュ通知結果も確認する

![画像15](/readme-img/Action6.png)

## 解説
サンプルプロジェクトに実装済みの内容のご紹介

#### SDKのインポートと初期設定
* ニフティクラウドmobile backend の[ドキュメント（クイックスタート）](https://mbaas.nifcloud.com/doc/current/introduction/quickstart_android.html#/Android/)をご用意していますので、ご活用ください

#### ロジック
 * `activity_main.xml`でデザインを作成し、`MainActivity.kt`にロジックを書いています
 * installationクラス(端末情報)が保存される処理は以下のように記述されます
 * アプリを再インストールした時に、端末のデバイストークンが重複した場合の処理を考慮した実装となっています。

```java
//**************** APIキーの設定とSDKの初期化 **********************
NCMB.initialize(applicationContext, "YOUR_APPLICATION_KEY", "YOUR_CLIENT_KEY");

```

* 端末情報を登録するための実装は以下となっています。
* 現端末の`installation`をsaveInBackgroundメソッドを利用して、データ保存を行います。

```java
_channels = findViewById<View>(R.id.spinChannel) as Spinner
_txtPrefectures = findViewById<View>(R.id.txtPrefecture) as EditText
val prefectures = _txtPrefectures.text.toString()
val item = _channels.selectedItem as String
val tmpArray = JSONArray()
tmpArray.put(item)
installation.channels = tmpArray
installation.put("Prefectures", prefectures)
installation.saveInBackground { e ->
    if (e != null) {
        //保存失敗
        Toast.makeText(this@MainActivity, "端末情報の保存に失敗しました。" + e.message, Toast.LENGTH_LONG).show()
    } else {
        //保存成功
        Toast.makeText(this@MainActivity, "端末情報の保存に成功しました。", Toast.LENGTH_LONG).show()
    }
}
```


## 参考
* ニフティクラウドmobile backend の[ドキュメント（プッシュ通知（Android））](https://mbaas.nifcloud.com/doc/current/push/basic_usage_android.html)をご用意していますので、ご活用ください
