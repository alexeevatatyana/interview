package utils

import com.google.gson.Gson
import data.Bug
import data.Case
import data.TriangleDataClass

public class Provider {
    companion object {
        @JvmStatic
        fun getCases(): List<Case> {
            val paramsFile = DataProvider().loadFileAsString(
                DataProvider().getCurrentWorkingDirectory()
                    .resolve("src/test/resources/params.json"))
            val testSettings = Gson().fromJson(paramsFile, TriangleDataClass::class.java)
            return testSettings.data.cases
        }

        @JvmStatic
        fun getBugs(): List<Bug> {
            val pathToFile = DataProvider().getCurrentWorkingDirectory()
                .resolve("src/test/resources/params.json")
            val paramsAsString = DataProvider().loadFileAsString(pathToFile)
            val testSettings = Gson().fromJson(paramsAsString, TriangleDataClass::class.java)
            return testSettings.data.bugs
        }
    }
}