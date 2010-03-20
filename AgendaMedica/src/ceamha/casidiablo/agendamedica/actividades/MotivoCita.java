package ceamha.casidiablo.agendamedica.actividades;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import ceamha.casidiablo.agendamedica.almacenamiento.AgendaDbAdaptador;
import ceamha.casidiablo.agendamedica.esqueleto.Cita;

public class MotivoCita extends Activity{
	private EditText motivo;
	private Button guardar;
	private AgendaDbAdaptador baseDatos;
	@Override
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		baseDatos = new AgendaDbAdaptador(this);
		baseDatos.abrirBaseDatos();
		setContentView(R.layout.motivo_cita);
		iniciarUI();
	}
	private void iniciarUI() {
		motivo = (EditText) findViewById(R.id.asignar_motivo_cita);
		
		guardar = (Button) findViewById(R.id.guardar_cita);
		guardar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(motivo.getText().toString().equals(""))
					new Notificador().notificar(v.getContext(), "Debe especificar un motivo.", 1);
				else
					almacenarCita();
			}
		});
	}
	private void almacenarCita() {
		//obtener los datos que me envio la anterior actividad
		Bundle b = getIntent().getExtras();
		String fecha = b.getString("fecha");
		String horaProgramadaInicio = b.getString("horaProgramadaInicio");
		String horaProgramadaFin = b.getString("horaProgramadaFin");
		int idPaciente = b.getInt("idPaciente");
		
        //crear la cita
		Cita cita = new Cita();
		cita.setFecha(fecha);
		cita.setHoraProgramadaInicio(horaProgramadaInicio);
		cita.setHoraProgramadaFin(horaProgramadaFin);
		cita.setIdPaciente(idPaciente);
		cita.setEstado(true);
		cita.setMotivo(motivo.getText().toString());
		baseDatos.almacenarCita(cita);
		new Notificador().notificar(this, "La cita ha sido almacenada correctamente", 1);
		finish();
	}
}
