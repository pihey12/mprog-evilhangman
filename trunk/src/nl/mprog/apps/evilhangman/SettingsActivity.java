package nl.mprog.apps.evilhangman;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;

public class SettingsActivity extends PreferenceActivity {
	
    public static final String PREF_GUESSES = "pref_guessesamount";
    public static final String PREF_EVIL = "pref_evil";
    public static final String PREF_LENGTH = "pref_length";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}