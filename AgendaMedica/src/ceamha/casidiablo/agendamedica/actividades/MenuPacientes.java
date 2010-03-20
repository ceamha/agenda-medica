package ceamha.casidiablo.agendamedica.actividades;

import ceamha.casidiablo.agendamedica.almacenamiento.AgendaDbAdaptador;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MenuPacientes extends ListActivity {

	private static final int M_NUEVO = Menu.FIRST;
	private static final int M_BUSCAR = Menu.FIRST + 1;
	private static final int M_INACTIVOS = Menu.FIRST + 2;
	private AgendaDbAdaptador baseDatos;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseDatos = new AgendaDbAdaptador(this);
		baseDatos.abrirBaseDatos();
		generarListadoPacientes();
		ListView lv = getListView();
		lv.setTextFilterEnabled(false);
		lv.setOnItemClickListener(manejadorClickPaciente);
	}
	
	// Crear un manejador de eventos para la lista
	private OnItemClickListener manejadorClickPaciente = new OnItemClickListener() {
	    @SuppressWarnings("unchecked")
		public void onItemClick(AdapterView parent, View v, int position, long id)
	    {
	        Intent intent = new Intent(MenuPacientes.this, InformacionPaciente.class);
	        //le envio el id del paciente :)
	        intent.putExtra("idPaciente", (int)id);
     	    startActivity(intent);
	    }
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(Menu.NONE, M_NUEVO, 0, R.string.m_nuevo_paciente).setIcon(
				R.drawable.nuevopaciente);
		menu.add(Menu.NONE, M_BUSCAR, 1, R.string.m_buscar_paciente).setIcon(
				R.drawable.consultas);
		menu.add(Menu.NONE, M_INACTIVOS, 1, R.string.m_inactivos_paciente).setIcon(
				R.drawable.pacientes_inactivos);
		return true;
	}

	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		super.onMenuItemSelected(featureId, item);
		Intent intent;
		switch (item.getItemId()) {
		case M_NUEVO:
			intent = new Intent(MenuPacientes.this, NuevoPaciente.class);
			startActivityForResult(intent, CodigosPeticion.INSERTAR_PACIENTE);
			break;
		case M_INACTIVOS:
			intent = new Intent(MenuPacientes.this, ListaPacientesInactivos.class);
			startActivityForResult(intent, CodigosPeticion.LISTA_PACIENTES_INACTIVOS);			
			break;
		case M_BUSCAR:
			intent = new Intent(MenuPacientes.this, BuscarPacientes.class);
			startActivityForResult(intent, CodigosPeticion.BUSCAR_PACIENTE);
			break;
		}
		return true;
	}
	
	/**
	 * Utiliza un objeto cursor obtenido usando el metodo obtenerPacientes
	 */
	private void generarListadoPacientes() {
		try{
			Cursor cursor = baseDatos.obtenerPacientes();
			//avisar a la actividad que se usara un cursor
			startManagingCursor(cursor);
	        
	        // Crear un array para especificar los campos que queremos 
			//mostrar en la lista (solo la fecha, de momento)
	        String[] desde = new String[]{"apellidos"};
	        
	        // Y un array de los campos que queremos enlazar
	        int[] para = new int[]{R.id.nombre_paciente};
	        
	        // Crear un cursoradapter y asignarlo a la pantalla
	        SimpleCursorAdapter pacientes = new SimpleCursorAdapter(this, R.layout.lista_pacientes, cursor, desde, para);
	        setListAdapter(pacientes);
		}catch(Exception e){
			new Notificador().notificar(getApplicationContext(), e.toString(), Toast.LENGTH_LONG);
		}
	}
}
