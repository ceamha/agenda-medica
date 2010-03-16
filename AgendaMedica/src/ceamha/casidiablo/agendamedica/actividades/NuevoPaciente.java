package ceamha.casidiablo.agendamedica.actividades;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import ceamha.casidiablo.agendamedica.almacenamiento.AgendaDbAdaptador;
import ceamha.casidiablo.agendamedica.esqueleto.Paciente;

public class NuevoPaciente extends Activity{
	private EditText nombres;
	private EditText apellidos;
	private EditText documento;
	private EditText telefono;
	private EditText direccion;
	private EditText correo;
	private EditText fechaNacimiento;
	private Button guardar;
	private Button cancelar;
	private AgendaDbAdaptador baseDatos;
	@Override
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		baseDatos = new AgendaDbAdaptador(this);
		baseDatos.abrirBaseDatos();
		setContentView(R.layout.crear_paciente);
		iniciarUI();
	}
	private void iniciarUI() {
		nombres = (EditText) findViewById(R.id.nombres_paciente);
		apellidos = (EditText) findViewById(R.id.apellidos_paciente);
		documento = (EditText) findViewById(R.id.documento_paciente);
		telefono = (EditText) findViewById(R.id.telefono_paciente);
		direccion = (EditText) findViewById(R.id.direccion_paciente);
		correo = (EditText) findViewById(R.id.correo_paciente);
		fechaNacimiento = (EditText) findViewById(R.id.fecha_nacimiento_paciente);
		
		guardar = (Button) findViewById(R.id.guardar_paciente);
		guardar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				insertarPaciente();
			}
		});
		
		cancelar = (Button) findViewById(R.id.cancelar_guardar_paciente);
		
	}
	
	private void insertarPaciente(){
		Paciente paciente = new Paciente();
		paciente.setNombres(nombres.getText().toString());
		paciente.setApellidos(apellidos.getText().toString());
		paciente.setDocumento(Long.parseLong(documento.getText().toString()));
		paciente.setTelefono(telefono.getText().toString());
		paciente.setDireccion(direccion.getText().toString());
		paciente.setCorreo(correo.getText().toString());
		paciente.setFechaNacimiento(fechaNacimiento.getText().toString());
		paciente.setActivo(true);
		long resultado = baseDatos.almacenarPaciente(paciente);
		if(resultado > 0)
			new Notificador().notificar(this, "Registro almacenado", Toast.LENGTH_LONG);
		else
			new Notificador().notificar(this, "Error al almacenar registro", Toast.LENGTH_LONG);
		finish();
	}
}
