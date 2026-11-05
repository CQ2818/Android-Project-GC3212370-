package com.griffith.mydiary.data

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

/**
 * Simple local persistence using SharedPreferences + JSON.
 * The dataset is intentionally small (a personal diary), so a JSON blob
 * is enough and keeps the project dependency-free for a first milestone.
 */
object DiaryRepository {

    private const val PREFS_NAME = "my_diary_store"
    private const val KEY_ENTRIES = "entries"

    fun load(context: Context): List<DiaryEntry> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val raw = prefs.getString(KEY_ENTRIES, null) ?: return emptyList()
        return try {
            val array = JSONArray(raw)
            buildList {
                for (i in 0 until array.length()) {
                    val obj = array.getJSONObject(i)
                    add(
                        DiaryEntry(
                            id = obj.getLong("id"),
                            title = obj.getString("title"),
                            content = obj.getString("content"),
                            mood = runCatching { Mood.valueOf(obj.getString("mood")) }
                                .getOrDefault(Mood.PEACE),
                            location = obj.optString("location", ""),
                            createdAt = obj.getLong("createdAt"),
                            steps = obj.optInt("steps", 0)
                        )
                    )
                }
            }.sortedByDescending { it.createdAt }
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun save(context: Context, entries: List<DiaryEntry>) {
        val array = JSONArray()
        entries.forEach { entry ->
            array.put(
                JSONObject().apply {
                    put("id", entry.id)
                    put("title", entry.title)
                    put("content", entry.content)
                    put("mood", entry.mood.name)
                    put("location", entry.location)
                    put("createdAt", entry.createdAt)
                    put("steps", entry.steps)
                }
            )
        }
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_ENTRIES, array.toString())
            .apply()
    }
}
