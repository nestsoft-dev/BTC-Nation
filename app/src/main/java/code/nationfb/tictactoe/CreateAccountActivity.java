package code.nationfb.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

public class CreateAccountActivity extends AppCompatActivity {
    EditText createAcctName,createEmail,createPassword;
    Button registerBtn;
    TextView loginTv;
    FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference database;
    double money = 10;
    FirebaseUser user;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        createAcctName = findViewById(R.id.createAcctName);
        createEmail = findViewById(R.id.createEmail);
        createPassword = findViewById(R.id.createPassword);
        loginTv = findViewById(R.id.textVie);

        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setTitle("Creating Account");
        dialog.setMessage("Loading...");


        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance().getReference().child("Users");


        loginTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CreateAccountActivity.this,LoginActivity.class));
            }
        });

        registerBtn = findViewById(R.id.button3);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = createEmail.getText().toString();
                String pass = createPassword.getText().toString();
                String name = createAcctName.getText().toString();

                if (email.isEmpty() || pass.isEmpty() || name.isEmpty()) {
                    Toast.makeText(CreateAccountActivity.this, "All Field Needed", Toast.LENGTH_LONG).show();
                }else{
                    dialog.show();
                    createAccount(email,pass,name);
                }
            }
        });
    }

    private void createAccount(String email, String pass, String name) {
        auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(CreateAccountActivity.this, "Account Created", Toast.LENGTH_LONG).show();

                    FirebaseUser user = auth.getCurrentUser();

                    auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                updateUi(user,email,pass,name);
//                                progressDialog.dismiss();
                                startActivity(new Intent(CreateAccountActivity.this,RealHomeActivity.class));
                                finish();
                            }else {
                                Toast.makeText(CreateAccountActivity.this, "Error "+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });


                }else {
//                    progressDialog.dismiss();
                    Toast.makeText(CreateAccountActivity.this, "An Error occurred "+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void updateUi(FirebaseUser user, String email, String pass, String name) {

        HashMap<String,Object> map = new HashMap<>();
        map.put("email",email);
        map.put("uid",user.getUid());
        map.put("Name",name);

        map.put("password",pass);
        map.put("myreferrals","");
        map.put("PhoneNumber","");
        map.put("MyMoney",money);
        map.put("Image","");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
        reference.child(user.getUid()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                 dialog.dismiss();
                    Toast.makeText(CreateAccountActivity.this, "Details Saved...", Toast.LENGTH_LONG).show();

                }else {
                    Toast.makeText(CreateAccountActivity.this, "Error "+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}