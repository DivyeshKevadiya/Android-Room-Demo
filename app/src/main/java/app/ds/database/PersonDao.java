package app.ds.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import app.ds.model.Person;

@Dao
public interface PersonDao {
    // Adds a person to the database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Person... people);

    // Removes a person from the database
    @Delete
    void delete(Person person);

    // Gets all people in the database
    @Query("SELECT * FROM person")
    List<Person> getAllPeople();

    // Gets all people in the database with a address
    @Query("SELECT * FROM person WHERE address LIKE :address")
    List<Person> getAllPeopleWithAddress(String address);
}
