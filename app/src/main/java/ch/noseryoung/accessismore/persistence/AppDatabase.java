package ch.noseryoung.accessismore.persistence;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import ch.noseryoung.accessismore.domainModell.User;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static final String TAG = "AppDatabase";
    private static final String DB_NAME = "AccessIsMore";
    private static AppDatabase appDb;

    public static AppDatabase getAppDb(Context context) {
        if (appDb == null) {
            Log.d(TAG, "created new database");
            appDb = Room.databaseBuilder(context, AppDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        } else {
            Log.d(TAG, "database already existing");
        }
        return appDb;
    }

    public abstract UserDAO getUserDAO();
}
