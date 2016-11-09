package ninja.sakib.footballfreak.models

import ninja.sakib.pultusorm.annotations.PrimaryKey

/**
 * := Coded with love by Sakib Sami on 11/8/16.
 * := s4kibs4mi@gmail.com
 * := www.sakib.ninja
 * := Coffee : Dream : Code
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
