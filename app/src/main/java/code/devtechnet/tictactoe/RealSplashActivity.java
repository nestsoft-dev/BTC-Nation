package code.devtechnet.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class RealSplashActivity extends AppCompatActivity {
ImageView strt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_splash);
        strt = findViewById(R.id.strt);
        strt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent( RealSplashActivity.this,CreateAccountActivity.class));
                finish();
            }
        });
    }
}