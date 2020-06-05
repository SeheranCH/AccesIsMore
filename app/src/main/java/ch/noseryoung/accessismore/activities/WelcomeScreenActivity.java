package ch.noseryoung.accessismore.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ch.noseryoung.accessismore.R;

public class WelcomeScreenActivity extends AppCompatActivity {

    private static final String TAG = "WelcomeScreenActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (this.getIntent().hasExtra("firstName") && this.getIntent().hasExtra("lastName")) {
            String firstName = this.getIntent().getStringExtra("firstName");
            String lastName = this.getIntent().getStringExtra("lastName");
            TextView sayHello = findViewById(R.id.welcomeTextView);
            sayHello.setText(getString(R.string.say_hello_1) + firstName + " " + lastName + getString(R.string.say_hello_2));
        }

        if (this.getIntent().hasExtra("pathPicture")) {
            ImageView avatar = findViewById(R.id.imageView);
            String picturePath = this.getIntent().getStringExtra("pathPicture");
            Log.d(TAG, "Path of picture: " + picturePath);
            Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
            avatar.setImageBitmap(bitmap);
        }
    }

        public boolean onCreateOptionsMenu (Menu menu){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_file, menu);
            return true;
        }

        public boolean onOptionsItemSelected (MenuItem item){
            logout();
            return true;
        }

        public void logout () {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }