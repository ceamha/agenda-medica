package ceamha.casidiablo.agendamedica.actividades;

import ceamha.casidiablo.agendamedica.almacenamiento.AgendaDbAdaptador;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ProgramarCitaPaciente extends ListActivity {

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
	        Intent intent = new Intent(ProgramarCitaPaciente.this, MotivoCita.class);
	        Bundle b = getIntent().getExtras();
	        intent.putExtra("fecha", b.getString("fecha"));
	        intent.putExtra("horaProgramadaInicio", b.getString("horaProgramadaInicio"));
			intent.putExtra("horaProgramadaFin", b.getString("horaProgramadaFin"));
	        intent.putExtra("idPaciente", (int)id);
     	    startActivityForResult(intent, CodigosPeticion.GUARDAR_CITA);
	    }
	};

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
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		finish();
	}
}