package code.nationfb.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RealHomeActivity extends AppCompatActivity {
    CardView gameCard;
    TextView userName,balance;
    FirebaseUser user;
    FirebaseAuth auth;
    DatabaseReference reference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_home);

        gameCard = findViewById(R.id.gameCard);
        userName = findViewById(R.id.userName);
        balance = findViewById(R.id.balance);
        gameCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RealHomeActivity.this,SplashActivity.class));
            }
        });

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("Users");
        reference.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String Firstname = snapshot.child("Name").getValue().toString();
                String getBonus = snapshot.child("MyMoney").getValue().toString();

                balance.setText(String.valueOf(getBonus));
                userName.setText("Hi,"+Firstname);


            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RealHomeActivity.this, "error "+error.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }
}