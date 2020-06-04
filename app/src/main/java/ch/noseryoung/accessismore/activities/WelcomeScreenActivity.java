package ch.noseryoung.accessismore.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ch.noseryoung.accessismore.R;

public class WelcomeScreenActivity extends AppCompatActivity {

    private static final String TAG = "WelcomeScreenActivity";

    private ImageView avatar;

    private TextView sayHello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (this.getIntent().hasExtra("firstName") && this.getIntent().hasExtra("lastName")) {
            String firstName = this.getIntent().getStringExtra("firstName");
            String lastName = this.getIntent().getStringExtra("lastName");
            sayHello = findViewById(R.id.welcomeTextView);
            sayHello.setText(getString(R.string.say_hello_1) + firstName + " " + lastName + getString(R.string.say_hello_2));
        }

        if (this.getIntent().hasExtra("pathPicture")) {
            avatar = findViewById(R.id.imageView);
            String picturePath = this.getIntent().getStringExtra("pathPicture");
            Log.d(TAG, "Path of picture: " + picturePath);
            Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
            avatar.setImageBitmap(bitmap);
            Log.d(TAG, "Path of picture is available");
        }
    }
}