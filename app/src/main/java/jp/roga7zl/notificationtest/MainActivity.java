package jp.roga7zl.notificationtest;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

public class MainActivity extends ActionBarActivity {

    private static final int NOTIFICATION_MINIMUM_ID = 0;
    private static final int NOTIFICATION_TEXT_ID = 1;
    private static final int NOTIFICATION_CUSTOMLAYOUT_ID = 2;
    private static final int NOTIFICATION_INTENT_ID = 3;
    private static final int NOTIFICATION_VIBRATE_ID = 4;
    private static final int NOTIFICATION_IMAGE_ID = 5;

    private static final int REQUEST_CODE_TEST = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ただ通知を出すだけ
        initMinimumButton();
        // テキスト表示
        initTextButton();
        // 自作レイアウト
        initCustomLayoutButton();
        // タップしたとき
        initIntentButton();

        // バイブレーション
        initVibrateButton();
        // 大きい画像
        initImageButton();
    }

    private void initMinimumButton() {
        initButton(R.id.button_minimum, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
                builder.setSmallIcon(R.drawable.ic_launcher); // 最低限アイコンが無いと失敗する

                NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
                manager.notify(NOTIFICATION_MINIMUM_ID, builder.build());
            }
        });
    }

    private void initTextButton() {
        initButton(R.id.button_text, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
                builder.setSmallIcon(R.drawable.ic_launcher);

                // 通知バーに表示される
                builder.setContentTitle("Title"); // 1行目
                builder.setContentText("Text"); // 2行目
                builder.setSubText("SubText"); // 3行目
                builder.setContentInfo("Info"); // 右端
                builder.setWhen(1400000000000l); // タイムスタンプ（現在時刻、メール受信時刻、カウントダウンなど）

                builder.setTicker("Ticker"); // 通知されたタイミングで表示される(-4.4)
                // 5.0からは表示されない。テキスト読み上げなどAccessibilityServiceで使うものに役割が変わった。

                NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
                manager.notify(NOTIFICATION_TEXT_ID, builder.build());
            }
        });
    }

    private void initCustomLayoutButton() {
        initButton(R.id.button_customlayout, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
                builder.setSmallIcon(R.drawable.ic_launcher);

                RemoteViews customView = new RemoteViews(getPackageName(), R.layout.customlayout);
                customView.setTextViewText(R.id.textview_text, "_(:3｣ ∠)_");
                builder.setContent(customView);

                NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
                manager.notify(NOTIFICATION_CUSTOMLAYOUT_ID, builder.build());
            }
        });
    }

    private void initIntentButton() {
        initButton(R.id.button_intent, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
                builder.setSmallIcon(R.drawable.ic_launcher); // 最低限アイコンが無いと失敗する

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://yahoo.co.jp"));
                PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), REQUEST_CODE_TEST, intent, PendingIntent.FLAG_ONE_SHOT);
                builder.setContentIntent(contentIntent);

                builder.setAutoCancel(true); // タップで通知が削除される

                NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
                manager.notify(NOTIFICATION_INTENT_ID, builder.build());
            }
        });
    }

    private void initVibrateButton() {
        initButton(R.id.button_vibrate, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
                builder.setSmallIcon(R.drawable.ic_launcher);

                // デフォルト
                // builder.setDefaults(NotificationCompat.DEFAULT_SOUND| NotificationCompat.DEFAULT_VIBRATE | NotificationCompat.DEFAULT_LIGHTS); // もしくはNotificationCompat.DEFAUL_ALL
                // カスタム
                builder.setVibrate(new long[]{100, 200, 100, 200}); // off:100ms -> on:200ms -> ...

                NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
                manager.notify(NOTIFICATION_VIBRATE_ID, builder.build());
            }
        });
    }

    private void initImageButton() {
        initButton(R.id.button_image, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
                builder.setSmallIcon(R.drawable.ic_launcher);

                Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
                builder.setLargeIcon(icon);

                NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
                manager.notify(NOTIFICATION_IMAGE_ID, builder.build());
            }
        });
    }

    private void initButton(int id, View.OnClickListener listener) {
        Button button = (Button) findViewById(id);
        button.setOnClickListener(listener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
