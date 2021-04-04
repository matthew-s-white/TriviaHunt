package com.example.triviahunt;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.FragmentActivity;

import java.io.File;
import java.io.IOException;

public class CreateAccount extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_account_details);

        Button createAccountButton = (Button) findViewById(R.id.CreateAccount);

        final EditText usernameET = (EditText) findViewById(R.id.editUsername);
        usernameET.setInputType(InputType.TYPE_CLASS_TEXT);

        final EditText nameET = (EditText) findViewById(R.id.editName);
        nameET.setInputType(InputType.TYPE_CLASS_TEXT);

        final EditText passwordET = (EditText) findViewById(R.id.editTextTextPassword2);
        passwordET.setInputType(InputType.TYPE_CLASS_TEXT);


        createAccountButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String username = usernameET.getText().toString();
                String name = nameET.getText().toString();
                String password = passwordET.getText().toString();

                Account newAccount = new Account();
                newAccount.setUsername(username);
                newAccount.setName(name);
                newAccount.setPassword(password);


                try {
                    newAccount.writeToFile("myAccount.txt", CreateAccount.this); // Might need to change (include filepath)
                } catch (IOException e) {
                    e.printStackTrace();
                }

                finish();
            }
        });

    }
}
