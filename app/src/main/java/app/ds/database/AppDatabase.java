package app.ds.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import app.ds.model.Person;

@Database(entities = {
        Person.class /*, AnotherEntityType.class, AThirdEntityType.class */
}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PersonDao getPersonDao();
}
