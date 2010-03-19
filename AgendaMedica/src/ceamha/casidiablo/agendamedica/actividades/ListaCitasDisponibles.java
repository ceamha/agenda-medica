package ceamha.casidiablo.agendamedica.actividades;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.ListActivity;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import ceamha.casidiablo.agendamedica.almacenamiento.AgendaDbAdaptador;

public class ListaCitasDisponibles extends ListActivity {
	private AgendaDbAdaptador baseDatos;
	protected TextView textoHora;

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
		if (extras != null)
			fecha = extras.getString("fecha");
		Cursor cursor = baseDatos.obtenerCitasDisponibles(fecha);
		String[] desde = new String[] { "horaProgramadaInicio",
				"nombresPaciente", "vacante"};
		int[] para = new int[] { R.id.hora_inicio_cita, R.id.nombre_paciente, R.id.vacante};

		try {
			ArrayList<Map<String, String>> arl = new ArrayList<Map<String, String>>();
			cursor.moveToFirst();
			if (cursor.isFirst()) {
				//recorrer el cursor
				do {
					//recuperar los datos
					String horaProgramadaInicio = cursor.getString(cursor
							.getColumnIndex("horaProgramadaInicio"));
					String horaProgramadaFin = cursor.getString(cursor
							.getColumnIndex("horaProgramadaFin"));
					String nombresPaciente = cursor.getString(cursor
							.getColumnIndex("nombresPaciente"));
					HashMap<String, String> mapa = new HashMap<String, String>();
					mapa.put("horaProgramadaInicio", horaProgramadaInicio + " - "+ horaProgramadaFin+"false");
					mapa.put("nombresPaciente", nombresPaciente);
					arl.add(mapa);
				} while (cursor.moveToNext());
			}
			
			SimpleAdapter ad = new SimpleAdapter(this, arl, R.layout.lista_citas_disponibles, desde, para);
			SimpleAdapter.ViewBinder pp = new SimpleAdapter.ViewBinder(){
				@Override
				public boolean setViewValue(View view, Object data,
						String textRepresentation) {
					if(view instanceof TextView){
						TextView tt = (TextView) view;
						tt.setText("");
						//si es el que muestra la fecha
						if(tt.getId() == R.id.hora_inicio_cita && textRepresentation.endsWith("false")){
							tt.setTextColor(Color.RED);
							textRepresentation = textRepresentation.substring(0, textRepresentation.indexOf("false"));
						}
						tt.setText(textRepresentation);
						return true;
					}
					else return false;
				}
			};
			ad.setViewBinder(pp);
			setListAdapter(ad);
		} catch (Exception e) {
			new Notificador().notificar(this, "Error " + e.toString(), 1);
		}
	}
}
