package ceamha.casidiablo.agendamedica.actividades;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class MenuPacientes extends ListActivity {

	static final String[] PACIENTES = new String[] {"ceamha","casidiablo","together"};
	private static final int M_NUEVO = Menu.FIRST;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);

	  setListAdapter(new ArrayAdapter<String>(this, R.layout.listas, PACIENTES));

	  ListView lv = getListView();
	  lv.setTextFilterEnabled(true);

	  lv.setOnItemClickListener(new OnItemClickListener() {
	    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	      //Datos del Paciente
     	      Intent intent = new Intent(MenuPacientes.this, Paciente.class);
       	      startActivityForResult(intent, 1);
	    }
	  });
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(Menu.NONE, M_NUEVO, 0, R.string.m_nuevo_paciente).setIcon(R.drawable.nuevopaciente);
//        menu.add(Menu.NONE, M_ACTUALIZAR, 1, R.string.m_actualizar_paciente).setIcon(R.drawable.refrescar);
        return true;
    }
	
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		super.onMenuItemSelected(featureId, item);
     	Intent intent;
           switch(item.getItemId()) {
	           case M_NUEVO:
//	       	      intent = new Intent(MenuPacientes.this, NuevoPaciente.class);
//	       	      startActivity(intent);
	               break;
	           }
           return true;
    }

}

