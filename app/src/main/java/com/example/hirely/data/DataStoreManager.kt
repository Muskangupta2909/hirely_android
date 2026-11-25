package com.example.hirely.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.hirely.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.json.JSONArray
import org.json.JSONObject

// Application ke Context ke saath DataStore create kar rahe hain
private val Context.dataStore by preferencesDataStore(
    name = "hirely_preferences"
)

// DataStore se related saara kaam yahan handle hoga
class DataStoreManager(
    private val context: Context
) {

    companion object {

        // Saare registered users ko JSON String ke form mein store karenge
        private val USERS_KEY =
            stringPreferencesKey("registered_users")

        // Current logged-in user ka email
        private val LOGGED_IN_EMAIL_KEY =
            stringPreferencesKey("logged_in_email")

        // User login hai ya nahi
        private val IS_LOGGED_IN_KEY =
            booleanPreferencesKey("is_logged_in")

        private val ONBOARDING_COMPLETED_KEY =
            booleanPreferencesKey("onboarding_completed")
    }


    // ---------------------------------------------------------
    // REGISTER USER
    // ---------------------------------------------------------

    suspend fun saveUser(user: User) {

        context.dataStore.edit { preferences ->

            // Pehle se saved users read kar rahe hain
            val existingUsersJson =
                preferences[USERS_KEY] ?: "[]"

            val jsonArray = JSONArray(existingUsersJson)

            // New user ko JSON object mein convert kar rahe hain
            val userJson = JSONObject().apply {

                put("name", user.name)
                put("email", user.email)
                put("phone", user.phone)
                put("password", user.password)
                // User ka role bhi save hoga
                put("role", user.role)
            }

            // New user ko existing list mein add kar rahe hain
            jsonArray.put(userJson)

            // Updated users list DataStore mein save
            preferences[USERS_KEY] = jsonArray.toString()
        }
    }


    // ---------------------------------------------------------
    // GET ALL REGISTERED USERS
    // ---------------------------------------------------------

    val users: Flow<List<User>> =
        context.dataStore.data.map { preferences ->

            val usersJson =
                preferences[USERS_KEY] ?: "[]"

            val jsonArray = JSONArray(usersJson)

            val usersList = mutableListOf<User>()

            for (i in 0 until jsonArray.length()) {

                val userJson = jsonArray.getJSONObject(i)

                usersList.add(
                    User(
                        name = userJson.getString("name"),
                        email = userJson.getString("email"),
                        phone = userJson.getString("phone"),
                        password = userJson.getString("password"),

                        // Saved role read kar rahe hain
                        // Old users ke liye USER default rahega
                        role = userJson.optString("role", "USER")
                    )
                )
            }

            usersList
        }


    // ---------------------------------------------------------
    // LOGIN SESSION
    // ---------------------------------------------------------

    // Login successful hone ke baad current user save karega
    suspend fun saveLoginData(email: String) {

        context.dataStore.edit { preferences ->

            // Current logged-in user
            preferences[LOGGED_IN_EMAIL_KEY] = email

            // User logged in hai
            preferences[IS_LOGGED_IN_KEY] = true
        }
    }


    // Check karega user currently logged in hai ya nahi
    val isLoggedIn: Flow<Boolean> =
        context.dataStore.data.map { preferences ->

            preferences[IS_LOGGED_IN_KEY] ?: false
        }


    // Current logged-in user ka email
    val loggedInEmail: Flow<String?> =
        context.dataStore.data.map { preferences ->

            preferences[LOGGED_IN_EMAIL_KEY]
        }


    // ---------------------------------------------------------
    // LOGOUT
    // ---------------------------------------------------------

    // Sirf login/session clear karega
    // Registered users ko delete NAHI karega
    suspend fun logout() {

        context.dataStore.edit { preferences ->

            preferences.remove(LOGGED_IN_EMAIL_KEY)

            preferences[IS_LOGGED_IN_KEY] = false
        }
    }

    //onboarding part

    // Check karega onboarding complete hua hai ya nahi
    val isOnboardingCompleted: Flow<Boolean> =
        context.dataStore.data.map { preferences ->

            preferences[ONBOARDING_COMPLETED_KEY] ?: false
        }


    // Skip ya Complete hone par onboarding complete save karega
    suspend fun completeOnboarding() {

        context.dataStore.edit { preferences ->

            preferences[ONBOARDING_COMPLETED_KEY] = true
        }
    }
}