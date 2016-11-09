package ninja.sakib.footballfreak

import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import ninja.sakib.footballfreak.adapters.FixtureAdapter
import ninja.sakib.footballfreak.api.FixtureFetcher
import ninja.sakib.footballfreak.models.FixtureModel
import ninja.sakib.pultusorm.callbacks.Callback
import ninja.sakib.pultusorm.core.PultusORM
import ninja.sakib.pultusorm.core.PultusORMQuery
import ninja.sakib.pultusorm.exceptions.PultusORMException
import org.jetbrains.anko.*
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity(), Callback {
    private var fixtureAdapter by Delegates.notNull<FixtureAdapter>()
    private var database: PultusORM by Delegates.notNull<PultusORM>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = PultusORM("fixtures.db", Environment.getExternalStorageDirectory().absolutePath)

        relativeLayout {
            listView {
                fixtureAdapter = FixtureAdapter(applicationContext, mutableListOf())
                adapter = fixtureAdapter

            }.lparams(width = matchParent, height = matchParent) {
                bottomMargin = 70
            }

            linearLayout {
                button("Refresh") {

                }.lparams(width = 0, weight = 1f) {
                    leftMargin = 10
                    rightMargin = 10
                }.onClick {
                    showNewData()
                }

                button("Fetch") {

                }.lparams(width = 0, weight = 1f) {
                    leftMargin = 10
                    rightMargin = 10
                }.onClick {
                    doOnRefresh()
                }
            }.lparams(width = matchParent) {
                alignParentBottom()
            }
        }

        showNewData()
    }

    override fun onFailure(type: PultusORMQuery.Type, exception: PultusORMException) {
        Log.d("Where", "Added - Failed {${exception.message}}")
    }

    override fun onSuccess(type: PultusORMQuery.Type) {
        Log.d("Where", "Added - Success")
    }

    fun doOnRefresh() {
        runOnUiThread {
            toast("Fetching New Data")
        }

        doAsync {
            val fixtures = FixtureFetcher().fetch()
            database.delete(FixtureModel())

            Log.d("Where", "Saving to database ${fixtures.size}")
            for (it in fixtures) {
                database.save(it, this@MainActivity)
            }

            showNewData()
        }
    }

    fun showNewData() {
        runOnUiThread {
            toast("Refreshing List")
            fixtureAdapter.clearFixtures()

            val fixtures = database.find(FixtureModel())
            for (it in fixtures) {
                val fixture = it as FixtureModel
                fixtureAdapter.addFixture(fixture)
            }
        }
    }
}
