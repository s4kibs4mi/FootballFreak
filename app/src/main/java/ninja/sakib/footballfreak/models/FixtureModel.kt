package ninja.sakib.footballfreak.models

import ninja.sakib.pultusorm.annotations.PrimaryKey

/**
 * Created by s4kib on 11/9/16.
 */

class FixtureModel {
    @PrimaryKey
    var id = ""
    var homeTeamName = ""
    var awayTeamName = ""
    var goalsHomeTeam = ""
    var goalsAwayTeam = ""
    var date = ""
    var status = ""
    var matchDay = ""

    override fun toString(): String {
        return "$id\n" +
                "$homeTeamName\n" +
                "$awayTeamName\n" +
                "$date\n" +
                "$status"
    }
}
