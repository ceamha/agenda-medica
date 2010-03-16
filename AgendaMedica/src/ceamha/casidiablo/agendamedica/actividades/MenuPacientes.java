package ceamha.casidiablo.agendamedica.actividades;

import ceamha.casidiablo.agendamedica.almacenamiento.AgendaDbAdaptador;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MenuPacientes extends ListActivity {

	static final String[] PACIENTES = new String[] { "ceamha", "casidiablo",
			"together" };
	private static final int M_NUEVO = Menu.FIRST;
	private static final int M_BUSCAR = Menu.FIRST + 1;
	private AgendaDbAdaptador baseDatos;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseDatos = new AgendaDbAdaptador(this);
		baseDatos.abrirBaseDatos();
		generarListadoPacientes();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(Menu.NONE, M_NUEVO, 0, R.string.m_nuevo_paciente).setIcon(
				R.drawable.nuevopaciente);
		menu.add(Menu.NONE, M_BUSCAR, 1, R.string.m_buscar_paciente).setIcon(
				R.drawable.consultas);
		return true;
	}

	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		super.onMenuItemSelected(featureId, item);
		switch (item.getItemId()) {
		case M_NUEVO:
			Intent intent = new Intent(MenuPacientes.this, NuevoPaciente.class);
			startActivityForResult(intent, CodigosPeticion.ALGO);
			break;
		}
		return true;
	}
	
	private void generarListadoPacientes() {
		try{
			Cursor cursor = baseDatos.obtenerPacientes();
			//avisar a la actividad que se usará un cursor
			startManagingCursor(cursor);
	        
	        // Crear un array para especificar los campos que queremos 
			//mostrar en la lista (solo la fecha, de momento)
	        String[] desde = new String[]{"nombres", "apellidos"};
	        
	        // Y un array de los campos que queremos enlazar
	        int[] para = new int[]{R.id.lista_pacientes};
	        
	        // Now create a simple cursor adapter and set it to display
	        SimpleCursorAdapter pacientes = new SimpleCursorAdapter(this, R.layout.lista_pacientes, cursor, desde, para);
	        setListAdapter(pacientes);
		}catch(Exception e){
			new Notificador().notificar(getApplicationContext(), e.toString(), Toast.LENGTH_LONG);
		}
	}
}
