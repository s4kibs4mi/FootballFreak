package ninja.sakib.footballfreak.controllers

import android.os.Environment
import ninja.sakib.footballfreak.models.FixtureModel
import ninja.sakib.pultusorm.callbacks.Callback
import ninja.sakib.pultusorm.core.PultusORM
import kotlin.properties.Delegates

/**
 * := Coded with love by Sakib Sami on 11/8/16.
 * := s4kibs4mi@gmail.com
 * := www.sakib.ninja
 * := Coffee : Dream : Code
 */

class FixtureController {
    private var database: PultusORM by Delegates.notNull<PultusORM>()

    init {
        database = PultusORM("fixtures.db", Environment.getExternalStorageDirectory().absolutePath)
    }

    fun save(fixtureModel: FixtureModel) {
        database.save(fixtureModel)
    }

    fun save(fixtureModel: FixtureModel, callBack: Callback) {
        database.save(fixtureModel, callBack)
    }

    fun get(): MutableList<Any> {
        return database.find(FixtureModel())
    }

    fun clear() {
        database.delete(FixtureModel())
    }
}
