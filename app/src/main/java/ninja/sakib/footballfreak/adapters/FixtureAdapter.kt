package ninja.sakib.footballfreak.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import ninja.sakib.footballfreak.R
import ninja.sakib.footballfreak.models.FixtureModel
import org.jetbrains.anko.find
import org.jetbrains.anko.layoutInflater
import kotlin.properties.Delegates

/**
 * Created by s4kib on 11/8/16.
 */

class FixtureAdapter(context: Context, fixtureList: MutableList<FixtureModel>) :
        ArrayAdapter<FixtureModel>(context, R.layout.fixture_view, fixtureList) {

    private var fixtureList = mutableListOf<FixtureModel>()
    private var mContext: Context by Delegates.notNull<Context>()

    init {
        this.fixtureList = fixtureList
        mContext = context
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val currentView = mContext.layoutInflater.inflate(R.layout.fixture_view, null)

        val homeTeamName = currentView.find<TextView>(R.id.homeTeamName)
        val awayTeamName = currentView.find<TextView>(R.id.awayTeamName)
        val homeTeamGoals = currentView.find<TextView>(R.id.homeTeamGoals)
        val awayTeamGoals = currentView.find<TextView>(R.id.awayTeamGoals)
        val matchDate = currentView.find<TextView>(R.id.matchDate)
        val matchStatus = currentView.find<TextView>(R.id.matchStatus)

        homeTeamName.text = fixtureList[position].homeTeamName
        awayTeamName.text = fixtureList[position].awayTeamName
        homeTeamGoals.text = fixtureList[position].goalsHomeTeam
        awayTeamGoals.text = fixtureList[position].goalsAwayTeam
        matchDate.text = fixtureList[position].date
        matchStatus.text = fixtureList[position].status

        return currentView
    }

    fun clearFixtures() {
        clear()
        notifyDataSetChanged()
    }

    fun addFixture(fixtureModel: FixtureModel) {
        add(fixtureModel)
        notifyDataSetChanged()
    }
}
