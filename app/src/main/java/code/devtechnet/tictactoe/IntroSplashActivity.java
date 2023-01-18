package code.devtechnet.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class IntroSplashActivity extends AppCompatActivity {
    FirebaseUser user;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_splash);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        new Handler().postDelayed(() -> {
            if (user !=null){
                startActivity(new Intent(IntroSplashActivity.this, RealHomeActivity.class));
                finish();
            }else {
                Intent intent = new Intent(IntroSplashActivity.this, RealSplashActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
            finish();
        },3000);
    }
}