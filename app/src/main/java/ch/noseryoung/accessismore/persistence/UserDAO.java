package ch.noseryoung.accessismore.persistence;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ch.noseryoung.accessismore.domainModell.User;

@Dao
public interface UserDAO {

    @Query("SELECT * FROM users WHERE email = :emailAddress")
    public User getSingleUser(String emailAddress);

    @Query("SELECT * FROM users WHERE email = :emailAddress AND password = :password")
    public User checkSignInData(String emailAddress, String password);

    @Query("SELECT * FROM users")
    List<User> getAllUsers();

    @Insert
    public void insertUser (User user);

    @Update
    public void updateUser (User user);

    @Delete
    public void deleteUser (User user);

    @Delete
    public void deleteUsers (List<User> users);

}
