package ninja.sakib.footballfreak.views

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import ninja.sakib.footballfreak.adapters.FixtureAdapter
import ninja.sakib.footballfreak.api.FixtureFetcher
import ninja.sakib.footballfreak.controllers.FixtureController
import ninja.sakib.footballfreak.models.FixtureModel
import ninja.sakib.pultusorm.callbacks.Callback
import ninja.sakib.pultusorm.core.PultusORMQuery
import ninja.sakib.pultusorm.exceptions.PultusORMException
import org.jetbrains.anko.*
import kotlin.properties.Delegates

/**
 * := Coded with love by Sakib Sami on 11/8/16.
 * := s4kibs4mi@gmail.com
 * := www.sakib.ninja
 * := Coffee : Dream : Code
 */

class MainActivity : AppCompatActivity(), Callback {
    private var fixtureAdapter by Delegates.notNull<FixtureAdapter>()
    private var fixtureController = FixtureController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
            fixtureController.clear()

            Log.d("Where", "Saving to database ${fixtures.size}")
            for (it in fixtures) {
                fixtureController.save(it, this@MainActivity)
            }

            showNewData()
        }
    }

    fun showNewData() {
        runOnUiThread {
            toast("Refreshing List")
            fixtureAdapter.clearFixtures()

            val fixtures = fixtureController.get()
            for (it in fixtures) {
                val fixture = it as FixtureModel
                fixtureAdapter.addFixture(fixture)
            }
        }
    }
}
