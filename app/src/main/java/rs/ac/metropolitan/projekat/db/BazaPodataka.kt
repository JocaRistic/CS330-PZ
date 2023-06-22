package rs.ac.metropolitan.projekat.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import rs.ac.metropolitan.projekat.common.models.Movie
import rs.ac.metropolitan.projekat.common.models.User

@Database(
    entities = [User::class, Movie::class],
    version = 4,
    exportSchema = false
)
abstract class BazaPodataka: RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun movieDao(): MovieDao

    companion object {
        @Volatile
        private var INSTANCE: BazaPodataka? = null

        fun getDatabase(context: Context): BazaPodataka{
            val tempInstance = INSTANCE
            if (tempInstance != null){
               return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BazaPodataka::class.java,
                    "bazapodataka"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}