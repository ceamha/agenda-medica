package ceamha.casidiablo.agendamedica.actividades;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

public class NuevaCita extends Activity {
	private TextView mostrarFecha;
	private Button btnSelFecha;
	private int anio;
	private int mes;
	private int dia;

	static final int ID_DIALOGO_FECHA = 0;

	@Override
	public void onCreate(Bundle b) {
		super.onCreate(b);
		setContentView(R.layout.crear_cita);

		// Obtener los elementos de la UI
		mostrarFecha = (TextView) findViewById(R.id.mostrarFecha);
		btnSelFecha = (Button) findViewById(R.id.pickDate);

		// Añadir un listener al botón
		btnSelFecha.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showDialog(ID_DIALOGO_FECHA);
			}
		});

		// Obtener la fecha actual
		final Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, 1);
		anio = c.get(Calendar.YEAR);
		mes = c.get(Calendar.MONTH);
		dia = c.get(Calendar.DAY_OF_MONTH);

		// mostrar la fecha actual
		actualizarFecha();
		
		//asignar funcion al boton de crear cita
		Button crearCita = (Button) findViewById(R.id.programarCita);
		crearCita.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try{
					Intent intent = new Intent(NuevaCita.this, ListaCitasDisponibles.class);
					intent.putExtra("fecha", mostrarFecha.getText().toString());
					startActivityForResult(intent, CodigosPeticion.LISTA_CITAS_DISPONIBLES);
				}catch(Exception e){new Notificador().notificar(v.getContext(), "Error: "+e.toString(), 1);}
			}
		});
	}

	// pone la fecha en el TextView
	private void actualizarFecha() {
		// Obtener la fecha actual
		final Calendar c = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c2.set(anio, mes, dia);
		//comparar calendarios para asegurarse que no se pidan citas en el pasado
		int pepe = c2.compareTo(c);
		if(pepe == -1){
			new Notificador().notificar(this, "No puede asignar citas para hoy o antes", 1);
			c.add(Calendar.DAY_OF_MONTH, 1);
			anio = c.get(Calendar.YEAR);
			mes = c.get(Calendar.MONTH);
			dia = c.get(Calendar.DAY_OF_MONTH);
		}
		mostrarFecha.setText(new StringBuilder()
			// Mes es en base 0, así que se debe añadir 1
					.append(mes + 1).append("-").append(dia).append("-").append(
							anio).append(" "));
		mostrarFecha.setText("3-18-2010");
	}

	// el callback que se recibe cuando el usuario escoje una fecha
	private OnDateSetListener listenerAsignarFecha = new OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			anio = year;
			mes = monthOfYear;
			dia = dayOfMonth;
			actualizarFecha();
		}
	};

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case ID_DIALOGO_FECHA:
			return new DatePickerDialog(this, listenerAsignarFecha, anio, mes, dia);
		}
		return null;
	}
}