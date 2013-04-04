package nl.mprog.apps.evilhangman;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;

public class SettingsActivity extends PreferenceActivity {
	
    public static final String PREF_GUESSES = "pref_guessesamount";

    @SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        
//        ListPreference listPreference = (ListPreference) findPreference("pref_guesses");
//        listPreference.setSummary(listPreference.getValue().toString());
//        listPreference.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
//            @Override
//            public boolean onPreferenceChange(Preference preference, Object newValue) {
//                preference.setSummary(newValue.toString());
//                return false;
//            }
//        });
    }
}