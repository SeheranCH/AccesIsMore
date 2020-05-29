package ch.noseryoung.accessismore.persistence;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import ch.noseryoung.accessismore.domainModell.User;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "AccessIsMore";
    private static AppDatabase appDb;

    public static AppDatabase getAppDb(Context context) {
        if (appDb == null) {
            appDb = Room.databaseBuilder(context, AppDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return appDb;
    }

    public abstract UserDAO getUserDAO();
}
