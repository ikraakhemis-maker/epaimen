package com.epaimen.app;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;

/**
 * يفتح الموقع عبر Chrome Custom Tabs (محرك Chrome نفسه) بدلاً من WebView،
 * لأن الصفحة تعمل في Chrome ولا تظهر في WebView.
 */
public class MainActivity extends AppCompatActivity {

    private static final String TARGET_URL = "https://ikraakhemis-maker.github.io/epaimen/index.html";
    private boolean launched = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout l = new LinearLayout(this);
        l.setOrientation(LinearLayout.VERTICAL);
        l.setGravity(Gravity.CENTER);
        l.setBackgroundColor(0xFF083344);
        l.setPadding(dp(32), dp(32), dp(32), dp(32));

        ImageView logo = new ImageView(this);
        logo.setImageResource(R.drawable.ic_logo);
        l.addView(logo, new LinearLayout.LayoutParams(dp(96), dp(96)));

        TextView t = new TextView(this);
        t.setText(R.string.app_name);
        t.setTextColor(0xFFFFFFFF);
        t.setTextSize(22);
        t.setGravity(Gravity.CENTER);
        t.setPadding(0, dp(16), 0, dp(24));
        l.addView(t);

        Button b = new Button(this);
        b.setText(R.string.open_platform);
        b.setTextColor(0xFF083344);
        b.setBackgroundTintList(ColorStateList.valueOf(0xFFF5A524));
        b.setOnClickListener(v -> openSite());
        l.addView(b);

        setContentView(l);

        if (savedInstanceState != null) launched = savedInstanceState.getBoolean("launched");
        if (!launched) {
            launched = true;
            openSite();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle out) {
        super.onSaveInstanceState(out);
        out.putBoolean("launched", launched);
    }

    private void openSite() {
        Uri uri = Uri.parse(TARGET_URL);
        try {
            CustomTabsIntent tab = new CustomTabsIntent.Builder()
                    .setShowTitle(true)
                    .setToolbarColor(0xFF083344)
                    .build();
            tab.launchUrl(this, uri);
        } catch (Exception e) {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
            } catch (Exception ignored) {
                // لا يوجد متصفح
            }
        }
    }

    private int dp(int v) {
        return (int) (v * getResources().getDisplayMetrics().density);
    }
}
