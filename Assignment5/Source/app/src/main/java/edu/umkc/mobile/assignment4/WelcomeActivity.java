package edu.umkc.mobile.assignment4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    public void signIn(View view){
        Intent intent = new Intent(this,SignInActivity.class);
        startActivity(intent);
    }

    public void signUp(View view){
        Intent intent = new Intent(this,SignUpActivity.class);
        startActivity(intent);
    }
}
