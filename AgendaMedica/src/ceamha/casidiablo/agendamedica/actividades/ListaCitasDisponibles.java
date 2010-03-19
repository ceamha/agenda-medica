package ceamha.casidiablo.agendamedica.actividades;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;
import ceamha.casidiablo.agendamedica.almacenamiento.AgendaDbAdaptador;

public class ListaCitasDisponibles extends ListActivity {
	private AgendaDbAdaptador baseDatos;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// crear el objeto de la conexion a la base de datos
		baseDatos = new AgendaDbAdaptador(this);
		baseDatos.abrirBaseDatos();
		citasDisponibles();
		// ListView lv = getListView();
		// lv.setTextFilterEnabled(false);
		// lv.setOnItemClickListener(manejadorClickCita);
	}

	private void citasDisponibles() {
		Bundle extras = getIntent().getExtras();
		String fecha = "";
		if(extras !=null)
			fecha = extras.getString("fecha");
		Cursor cursor = baseDatos.obtenerCitasDisponibles(fecha);
		String[] desde = new String[] { "horaProgramadaInicio",
				"nombresPaciente", "motivo" };
		int[] para = new int[] { R.id.hora_inicio_cita, R.id.nombre_paciente,
				R.id.motivo_cita };

		try {
			SimpleCursorAdapter adaptador = new SimpleCursorAdapter(this,
					R.layout.lista_citas_disponibles, cursor, desde, para);
			setListAdapter(adaptador);
		} catch (Exception e) {
			new Notificador().notificar(this, "Error " + e.toString(), 1);
		}
	}
}
