package styleList.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Stylelist::class], version = 1, exportSchema = false)
abstract class StylelistDatabase: RoomDatabase() {

    abstract fun stylelistDao():StylelistDao

    companion object {
        @Volatile
        private var INSTANCE: StylelistDatabase? = null
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Contact ADD COLUMN seller_id TEXT NOT NULL DEFAULT ''")
            }
        }


        fun getDatabase(context: Context): StylelistDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            StylelistDatabase::class.java,
                            "stylelists.db"
                    ).build() //.addMigrations(MIGRATION_1_2)

                }
            }
            return INSTANCE!!
        }
    }
}