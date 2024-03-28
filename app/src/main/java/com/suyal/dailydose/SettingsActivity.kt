package com.suyal.dailydose

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat


class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_settings, NewsPreferenceFragment())
            .commit()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class NewsPreferenceFragment : PreferenceFragmentCompat() {

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.settings_main, rootKey)

            bindPreferenceSummaryToValue(findPreference(getString(R.string.settings_number_of_items_key)))
            bindPreferenceSummaryToValue(findPreference(getString(R.string.settings_order_by_key)))
            bindPreferenceSummaryToValue(findPreference(getString(R.string.settings_order_date_key)))
            bindPreferenceSummaryToValue(findPreference(getString(R.string.settings_color_key)))
            bindPreferenceSummaryToValue(findPreference(getString(R.string.settings_text_size_key)))
            val fromDate: Preference? = findPreference(getString(R.string.settings_from_date_key))
            fromDate?.setOnPreferenceClickListener {
                showDatePicker()
                true
            }
            bindPreferenceSummaryToValue(fromDate)
        }

        private fun showDatePicker() {
            val calendar = Calendar.getInstance()
            val year = calendar[Calendar.YEAR]
            val month = calendar[Calendar.MONTH]
            val dayOfMonth = calendar[Calendar.DAY_OF_MONTH]
            context?.let {
                DatePickerDialog(
                    it,
                    { _, year, month, dayOfMonth ->
                        val selectedDate = "$year-${month + 1}-$dayOfMonth"
                        val formattedDate = formatDate(selectedDate)
                        val prefs = preferenceManager.sharedPreferences
                        prefs.edit().putString(getString(R.string.settings_from_date_key), formattedDate).apply()
                        bindPreferenceSummaryToValue(findPreference(getString(R.string.settings_from_date_key)))
                    },
                    year,
                    month,
                    dayOfMonth
                ).show()
            }
        }

        private fun formatDate(dateString: String): String {
            val simpleDateFormat = SimpleDateFormat("yyyy-M-d", Locale.getDefault())
            var dateObject: Date? = null
            try {
                dateObject = simpleDateFormat.parse(dateString)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            val df = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            return df.format(dateObject)
        }

        private fun bindPreferenceSummaryToValue(preference: Preference?) {
            preference?.setOnPreferenceChangeListener { _, newValue ->
                val stringValue = newValue.toString()
                if (preference is androidx.preference.ListPreference) {
                    val listPreference = preference
                    val prefIndex = listPreference.findIndexOfValue(stringValue)
                    if (prefIndex >= 0) {
                        val labels = listPreference.entries
                        preference.summary = labels[prefIndex]
                    }
                } else {
                    preference?.summary = stringValue
                }
                true
            }
            val preferences = preferenceManager.sharedPreferences
            val preferenceString = preferences.getString(preference?.key, "")
            if (preference != null) {
                preference.summary = preferenceString
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
