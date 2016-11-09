package ninja.sakib.footballfreak.api

import android.util.Log
import com.eclipsesource.json.Json
import com.eclipsesource.json.JsonObject
import ninja.sakib.footballfreak.models.FixtureModel
import okhttp3.OkHttpClient
import okhttp3.Request

/**
 * := Coded with love by Sakib Sami on 11/8/16.
 * := s4kibs4mi@gmail.com
 * := www.sakib.ninja
 * := Coffee : Dream : Code
 */

class FixtureFetcher {
    val client = OkHttpClient()
    var API_URL = "http://api.football-data.org/v1/competitions/440/fixtures"

    fun fetch(): MutableList<FixtureModel> {
        val fixtures = mutableListOf<FixtureModel>()

        try {
            val request = Request.Builder().url(API_URL).build()
            val response = client.newCall(request).execute()
            val jsonResponse = response.body().string()
            response.close()

            Log.d("Where", "Response : " + jsonResponse)

            val result = Json.parse(jsonResponse).asObject().get("fixtures").asArray()

            Log.d("Where", "Found Models : " + result.size())


            for (i in 0..result.size()) {
                val fixture = result[i] as JsonObject
                val aFixture = FixtureModel()

                aFixture.homeTeamName = fixture.getString("homeTeamName", "")
                aFixture.awayTeamName = fixture.getString("awayTeamName", "")
                aFixture.id = "${aFixture.homeTeamName}And${aFixture.awayTeamName}"
                aFixture.status = fixture.getString("status", "")
                aFixture.matchDay = fixture.getInt("matchday", 0).toString()
                aFixture.date = fixture.getString("date", "")

                val result = fixture.get("result").asObject()
                aFixture.goalsHomeTeam = result.getInt("goalsHomeTeam", 0).toString()
                aFixture.goalsAwayTeam = result.getInt("goalsAwayTeam", 0).toString()

                Log.d("Where", "Get : $aFixture")
                fixtures.add(aFixture)
            }
        } catch (exception: Exception) {
            Log.d("Where", "Fetch - Error ${exception.message}")
        }
        return fixtures
    }
}
