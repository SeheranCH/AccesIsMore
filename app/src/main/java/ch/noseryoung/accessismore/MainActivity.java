package ch.noseryoung.accessismore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ch.noseryoung.accessismore.domainModell.User;
import ch.noseryoung.accessismore.persistence.AppDatabase;
import ch.noseryoung.accessismore.persistence.UserDAO;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";

    private UserDAO mUserDAO;

    private EditText mEditTextEmail;
    private Button mButtonToGetSignedIn;
    private Button mButtonToCreateAccount;

    private void showData(String email) {
        User user = mUserDAO.getSingleUser(email);
        String textToDisplay = "\n" + user.getEmail() + "\n" + user.getFirstName() + "\n" + user.getLastName() + "\n" + user.getPassword();
        Toast.makeText(this, textToDisplay, Toast.LENGTH_LONG).show();
    }

    private void openCreateAccountActivity() {
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
        Log.d(TAG, "open new activity 'CreateAccount'");
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonToGetSignedIn = findViewById(R.id.signInButton);
        mButtonToGetSignedIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditTextEmail = findViewById(R.id.emailFieldText);
                String email = mEditTextEmail.getText().toString();
                showData(email);
            }
        });
        mButtonToCreateAccount = findViewById(R.id.createNewAccountButton);
        mButtonToCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreateAccountActivity();
            }
        });

        mUserDAO = AppDatabase.getAppDb(getApplicationContext()).getUserDAO();
    }
}
