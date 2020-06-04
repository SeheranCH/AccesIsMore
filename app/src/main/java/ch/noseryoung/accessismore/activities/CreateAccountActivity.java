package ch.noseryoung.accessismore.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ch.noseryoung.accessismore.R;
import ch.noseryoung.accessismore.domainModell.User;
import ch.noseryoung.accessismore.persistence.AppDatabase;
import ch.noseryoung.accessismore.persistence.UserDAO;
import ch.noseryoung.accessismore.security.PasswordEncoder;
import ch.noseryoung.accessismore.validation.InputValidation;

public class CreateAccountActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private UserDAO mUserDAO;

    private PasswordEncoder passwordEncoder;

    private static final String TAG = "CreateAccountActivity";

    private InputValidation inputValidation = new InputValidation();

    private EditText mEditTextFirstName;
    private EditText mEditTextLastName;
    private EditText mEditTextEmail;
    private EditText mEditTextPassword1;
    private EditText mEditTextPassword2;

    private ImageView avatarImageView;

    private String errorIsNotEmpty;
    private String errorHasMaxLength50;
    private String errorHasMaxLength100;
    private String errorOnlyLetters;
    private String errorEmail;
    private String errorMatchPasswords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        errorIsNotEmpty = getString(R.string.error_is_not_empty);
        errorHasMaxLength50 = getString(R.string.error_hasMaxLength50);
        errorHasMaxLength100= getString(R.string.error_hasMaxLength100);
        errorOnlyLetters = getString(R.string.error_onlyLetters);
        errorEmail = getString(R.string.error_email);
        errorMatchPasswords = getString(R.string.error_match_passwords);

        Button saveNewAccount = findViewById(R.id.createNewAccountSecondButton);
        saveNewAccount.setOnClickListener(mSaveNewAccount);

        Button goToSignIn = findViewById(R.id.signInButton2);
        goToSignIn.setOnClickListener(mGoToSignInActivity);

        Button getAPicture = findViewById(R.id.roundedButton);
        getAPicture.setOnClickListener(mGetAPicture);

        avatarImageView = findViewById(R.id.imgAvatarView);

        mUserDAO = AppDatabase.getAppDb(getApplicationContext()).getUserDAO();

    }


    private View.OnClickListener mSaveNewAccount = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mEditTextFirstName = findViewById(R.id.editFirstnameFieldText);
            mEditTextLastName = findViewById(R.id.editLastnameFieldText);
            mEditTextEmail = findViewById(R.id.editEmailFieldText);
            mEditTextPassword1 = findViewById(R.id.editPasswordFieldText);
            mEditTextPassword2 = findViewById(R.id.editConfirmPasswordFieldText);

            String firstName = mEditTextFirstName.getText().toString().trim();
            String lastName = mEditTextLastName.getText().toString().trim();
            String email = mEditTextEmail.getText().toString().trim();
            String password1 = mEditTextPassword1.getText().toString();
            String password2 = mEditTextPassword2.getText().toString();

            Resources res = getResources();
            String[] allTypes = res.getStringArray(R.array.all_types);
            String[] allValues = new String[] {firstName, lastName, email, password1, password2};
            List<Boolean> maySubmitting = new ArrayList<>();

            // Validation
            for (int i = 0; i < allTypes.length; i++) {
                if (inputValidation.isNotEmpty(allValues[i])) {
                    // All names
                    if (allTypes[i].endsWith(getString(R.string.suffix_name))) {
                        if (inputValidation.onlyLetters(allValues[i])) {
                            if (inputValidation.hasMaxLength50(allValues[i])) {
                                maySubmitting.add(true);
                            } else {
                                maySubmitting.add(false);
                                sendErrorMessage(allTypes[i], errorHasMaxLength50);
                            }
                        } else {
                            maySubmitting.add(false);
                            sendErrorMessage(allTypes[i], errorOnlyLetters);
                        }
                    }
                    // Email
                    if (allTypes[i].equals(getString(R.string.emailType))) {
                        if (inputValidation.isValidEmail(allValues[i])) {
                            if (inputValidation.hasMaxLength100(allValues[i])) {
                                maySubmitting.add(true);
                            } else {
                                maySubmitting.add(false);
                                sendErrorMessage(allTypes[i], errorHasMaxLength100);
                            }
                        } else {
                            maySubmitting.add(false);
                            sendErrorMessage(allTypes[i], errorEmail);
                        }
                    }
                    if (allTypes[i].equals(getString(R.string.passwordType))) {
                        if (inputValidation.matchPasswords(allValues[i], allValues[i + 1])) {
                            maySubmitting.add(true);
                        } else {
                            maySubmitting.add(false);
                            sendErrorMessage(allTypes[i], errorMatchPasswords);
                        }
                    }
                } else {
                    maySubmitting.add(false);
                    sendErrorMessage(allTypes[i], errorIsNotEmpty);
                }
            }

            Log.d(TAG, "\n" + firstName + "\n" + lastName + "\n" + email + "\n" + password1 + "\n" + password2);

            if (!maySubmitting.contains(false)) {

                //Delete all existing users
                List<User> users = mUserDAO.getAllUsers();
                mUserDAO.deleteUsers(users);

                // Save new account
                User user = new User(firstName, lastName, email, password1);
                try {
                    user.setPassword(passwordEncoder.encrypt(user.getPassword()));
                    Log.d(TAG, user.getPassword());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mUserDAO.insertUser(user);

                //Message for saving successfully
                Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.toast_signed_up_success), Toast.LENGTH_LONG);
                toast.show();

                //Go back to MainActivity
                openMainActivity();
            }
        }
    };

    private View.OnClickListener mGetAPicture = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dispatchTakePictureIntent();
        }
    };

    private View.OnClickListener mGoToSignInActivity = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            openMainActivity();
        }
    };

    public void sendErrorMessage(String field, String errorType) {
        if (errorType.equals(errorIsNotEmpty)) {
            Toast.makeText(this, getString(R.string.toast_error_is_not_empty_1) + field + getString(R.string.toast_error_is_not_empty_2), Toast.LENGTH_LONG).show();
        } else if (errorType.equals(errorOnlyLetters)) {
            Toast.makeText(this, getString(R.string.toast_error_only_letters) + field, Toast.LENGTH_LONG).show();
        } else if (errorType.equals(errorEmail)) {
            Toast.makeText(this, getString(R.string.toast_error_email), Toast.LENGTH_LONG).show();
        } else if (errorType.equals(errorHasMaxLength50)) {
            Toast.makeText(this, field + getString(R.string.toast_error_has_max_length_50), Toast.LENGTH_LONG).show();
        } else if (errorType.equals(errorHasMaxLength100)) {
            Toast.makeText(this, field + getString(R.string.toast_error_has_max_length_100), Toast.LENGTH_LONG).show();
        } else if (errorType.equals(errorMatchPasswords)) {
            Toast.makeText(this, getString(R.string.toast_error_match_passwords), Toast.LENGTH_LONG).show();
        }
    }

    private void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }


}
