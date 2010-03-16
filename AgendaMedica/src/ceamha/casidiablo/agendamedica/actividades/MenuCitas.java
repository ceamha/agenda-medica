package ceamha.casidiablo.agendamedica.actividades;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MenuCitas extends ListActivity {

	static final String[] CITAS = new String[] {"Cita 1","Cita 2","Cita 3"};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);

	  setListAdapter(new ArrayAdapter<String>(this, R.layout.listas, CITAS));

	  ListView lv = getListView();
	  lv.setTextFilterEnabled(true);

	  lv.setOnItemClickListener(new OnItemClickListener() {
	    public void onItemClick(AdapterView<?> parent, View view,
	        int position, long id) {
	      // When clicked, show a toast with the TextView text
	      Toast.makeText(getApplicationContext(), ((TextView) view).getText()+" Hola",
	          Toast.LENGTH_SHORT).show();
	    //  Intent intent = new Intent(HelloListView.this, OtraActividad.class);
	    //  startActivity(intent);
	      
	    }
	  });
	}
	

}

