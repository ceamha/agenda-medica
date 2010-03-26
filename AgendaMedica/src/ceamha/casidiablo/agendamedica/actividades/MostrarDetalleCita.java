package ceamha.casidiablo.agendamedica.actividades;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import ceamha.casidiablo.agendamedica.almacenamiento.AgendaDbAdaptador;
import ceamha.casidiablo.agendamedica.esqueleto.Cita;
import ceamha.casidiablo.agendamedica.esqueleto.Paciente;

public class MostrarDetalleCita extends Activity {

	private TextView nombre;
	private TextView apellido;
	private TextView direccion;
	private TextView telefono;
	private TextView correo;
	private TextView fecha_cita, motivo_de_cita, hora_programada_inicio_cita, hora_programada_fin_cita;
	private Paciente paciente;
	private Cita cita;
	private Bundle extras;
	private AgendaDbAdaptador baseDatos;

	@Override
	public void onCreate(Bundle b) {
		super.onCreate(b);
		setContentView(R.layout.detalle_cita);
		int idCita = 0;
		try {
			baseDatos = new AgendaDbAdaptador(this);
			baseDatos.abrirBaseDatos();

			extras = getIntent().getExtras();
			idCita = extras.getInt("idCita");

			cita = baseDatos.obtenerCita(idCita);
			
			//poner datos a la cita
			fecha_cita = (TextView) findViewById(R.id.fecha_cita);
			fecha_cita.setText(cita.getFecha());
			motivo_de_cita = (TextView) findViewById(R.id.motivo_de_cita);
			motivo_de_cita.setText(cita.getMotivo());
			hora_programada_inicio_cita = (TextView) findViewById(R.id.hora_programada_inicio_cita);
			hora_programada_inicio_cita.setText(cita.getHoraProgramadaInicio());
			hora_programada_fin_cita = (TextView) findViewById(R.id.hora_programada_fin_cita);
			hora_programada_fin_cita.setText(cita.getHoraProgramadaFin());

			// obtener el paciente
			paciente = baseDatos.obtenerPaciente(1);
			// Mostrar la informacion del Paciente
			nombre = (TextView) findViewById(R.id.info_nombre);
			nombre.setText(paciente.getNombres() + " " + paciente.getApellidos());
			apellido = (TextView) findViewById(R.id.info_apellido);
			apellido.setText(paciente.getApellidos());
			direccion = (TextView) findViewById(R.id.info_direccion);
			direccion.setText(paciente.getDireccion());
			telefono = (TextView) findViewById(R.id.info_telefono);
			telefono.setText(paciente.getTelefono());
			correo = (TextView) findViewById(R.id.info_correo);
			correo.setText(paciente.getCorreo());
		} catch (Exception e) {
			new Notificador().notificar(this, idCita+" "+e.toString(), 1);
		}
	}

}
