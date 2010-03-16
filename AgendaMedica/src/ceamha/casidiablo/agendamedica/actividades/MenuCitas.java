package ceamha.casidiablo.agendamedica.actividades;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class MenuCitas extends ListActivity {

	static final String[] CITAS = new String[] {"Cita 1","Cita 2","Cita 3"};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);

	  setListAdapter(new ArrayAdapter<String>(this, R.layout.lista_citas, CITAS));
	}
	

}

