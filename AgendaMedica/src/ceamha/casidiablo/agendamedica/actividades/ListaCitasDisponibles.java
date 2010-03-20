package ceamha.casidiablo.agendamedica.actividades;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import android.app.ListActivity;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
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
			Vector<Map<String, String>> vector = new Vector<Map<String, String>>();
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
					vector.add(mapa);
				} while (cursor.moveToNext());
			}
			
			/**
			 * Ordenar el ArrayString y poner las vacantes
			 */
			String formatoHoraInicio = "", formatoHoraFin = "";
			HashMap<String, String> mapa = new HashMap<String, String>();
			int cont = 1;
			for(float inicio = 8.5f, fin = 16f, temp = 0; inicio <= fin; inicio += 0.5f){
				//crear formato hora
				formatoHoraInicio = ((int)inicio)+":"+( (inicio - ((int)inicio)) == 0.5 ? "30" : "00")+":00";
				temp = inicio + 0.5f;
				formatoHoraFin = ((int)temp)+":"+( (temp - ((int)temp)) == 0.5 ? "30" : "00")+":00";
				Iterator<Map<String, String>> itr = vector.iterator();
				boolean repetido = false;
				while(itr.hasNext()){
					mapa = (HashMap<String, String>) itr.next();
					if(mapa.get("horaProgramadaInicio").equals(formatoHoraInicio + " - "+ formatoHoraFin +"false")){
						repetido = true;
						break;
					}
				}
				if(!repetido){
					mapa = new HashMap<String, String>();
					mapa.put("horaProgramadaInicio", formatoHoraInicio + " - "+ formatoHoraFin +"true");
					mapa.put("nombresPaciente", "Vacante");
				}
				arl.add(mapa);
				cont ++;
			}
			new Notificador().notificar(this, formatoHoraInicio+ " -- "+formatoHoraFin, 1);
			
			SimpleAdapter ad = new SimpleAdapter(this, arl, R.layout.lista_citas_disponibles, desde, para);
			SimpleAdapter.ViewBinder pp = new SimpleAdapter.ViewBinder(){
				@Override
				public boolean setViewValue(View view, Object data,
						String textRepresentation) {
					if(view instanceof TextView){
						try{TextView tt = (TextView) view;
						tt.setText("");
						//si es el que muestra la fecha
						if(tt.getId() == R.id.hora_inicio_cita && textRepresentation.endsWith("false")){
							tt.setTextColor(Color.RED);
							textRepresentation = textRepresentation.substring(0, textRepresentation.indexOf("false"));
						}
						if(tt.getId() == R.id.hora_inicio_cita && textRepresentation.endsWith("true")){
							tt.setTextColor(Color.rgb(0, 87, 2));
							textRepresentation = textRepresentation.substring(0, textRepresentation.indexOf("true"));
						}
						tt.setText(textRepresentation);}
						catch(Exception e){new Notificador().notificar(view.getContext(), e.toString(), 1);}
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
