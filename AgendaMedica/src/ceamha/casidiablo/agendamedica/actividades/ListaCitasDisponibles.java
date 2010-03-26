package ceamha.casidiablo.agendamedica.actividades;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import ceamha.casidiablo.agendamedica.almacenamiento.AgendaDbAdaptador;

public class ListaCitasDisponibles extends ListActivity {
	private AgendaDbAdaptador baseDatos;
	protected TextView textoHora;
	private ArrayList<Map<String, String>> arl;
	private String fecha;
	
	// Crear un manejador de eventos para la lista
	private OnItemClickListener manejadorClickAddCita = new OnItemClickListener() {
		@SuppressWarnings("unchecked")
		public void onItemClick(AdapterView parent, View v, int position, long id) {
			HashMap<String, String> mapa = (HashMap<String, String>) arl.get(position);
			String horaProgramadaInicioFin = mapa.get("horaProgramadaInicio");
			String horaProgramadaInicio = "", horaProgramadaFin = "";
			//ignorar las que ya están asignadas
			if(!horaProgramadaInicioFin.endsWith("false")){
				horaProgramadaInicioFin = horaProgramadaInicioFin.substring(0, horaProgramadaInicioFin.indexOf("true"));
				StringTokenizer token = new StringTokenizer(horaProgramadaInicioFin, " - ");
				if(token.hasMoreTokens()){
					horaProgramadaInicio = token.nextToken();
					horaProgramadaFin = token.nextToken();
				}
				Intent intent = new Intent(ListaCitasDisponibles.this, ProgramarCitaPaciente.class);
				intent.putExtra("fecha", fecha);
				intent.putExtra("horaProgramadaInicio", horaProgramadaInicio);
				intent.putExtra("horaProgramadaFin", horaProgramadaFin);
				startActivityForResult(intent, CodigosPeticion.SELECCIONAR_PACIENTE_CITA);
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// crear el objeto de la conexion a la base de datos
		baseDatos = new AgendaDbAdaptador(this);
		baseDatos.abrirBaseDatos();
		citasDisponibles();
		ListView lv = getListView();
		lv.setTextFilterEnabled(false);
		lv.setOnItemClickListener(manejadorClickAddCita);
	}

	private void citasDisponibles() {
		Bundle extras = getIntent().getExtras();
		fecha = "";
		if (extras != null)
			fecha = extras.getString("fecha");
		Cursor cursor = baseDatos.obtenerCitasDisponibles(fecha);
		String[] desde = new String[] { "horaProgramadaInicio",
				"nombresPaciente", "vacante"};
		int[] para = new int[] { R.id.hora_inicio_cita, R.id.nombre_paciente, R.id.vacante};

		try {
			arl = new ArrayList<Map<String, String>>();
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
			}
			
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
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		citasDisponibles();
	}
}
