package nl.mprog.apps.evilhangman;

import java.util.ArrayList;
import java.util.List;

import nl.mprog.apps.evilhangman.clickhandlers.ButtonClickHandler;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

public class ButtonAdapter extends BaseAdapter { 
    private Context context;

    public ButtonAdapter(Context c) { 
    	context = c;
    }

    public int getCount () {
    	return 26; 
    }

    public Object getItem (int position) {
    	return null; 
    }

    public long getItemId (int position) {
    	return position; 
    }
    
    public View getView (int position, View convertView, ViewGroup parent){     
    	Button btn;         
        if (convertView == null) {     
            btn = new Button (context);
            btn.setLayoutParams (new GridView.LayoutParams (60, 60));
            btn.setPadding (0,0,0,0); 
            btn.setText(Character.toString((char) (position+97)));
        } else {        
            btn = (Button) convertView;
        }       
  
        btn.setOnClickListener(new ButtonClickHandler((MainActivity) context));
        btn.setId (position);
        	
        return btn; 
    }
}