package ceamha.casidiablo.agendamedica.actividades;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import ceamha.casidiablo.agendamedica.almacenamiento.AgendaDbAdaptador;
import ceamha.casidiablo.agendamedica.esqueleto.Paciente;

public class ActualizarPaciente extends Activity{
	
	private TextView labelNombre;
	private TextView labelApellido;
	private TextView labelDireccion;
	private TextView labelTelefono;
	private TextView labelCorreo;
	private EditText nombre;
	private EditText apellido;
	private EditText direccion;
	private EditText telefono;
	private EditText correo;
	private Button actualizar;
	private AgendaDbAdaptador baseDatos;
	private Paciente paciente;
	
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actualizar_paciente);
        //crear y abrir base de datos
        baseDatos = new AgendaDbAdaptador(this);
        baseDatos.abrirBaseDatos();
        //obtener el ID del paciente que me lo pasa la anterior actividad
        Bundle extras = getIntent().getExtras();
        int idPaciente = 0;
		if(extras !=null)
			idPaciente = extras.getInt("idPaciente");
		//Titulos
		labelNombre = (TextView) findViewById(R.id.label_nombre);
		labelNombre.setText(labelNombre.getText());
		labelApellido = (TextView) findViewById(R.id.label_apellido);
		labelApellido.setText(labelApellido.getText());
		labelDireccion = (TextView) findViewById(R.id.label_direccion);
		labelDireccion.setText(labelDireccion.getText());
		labelTelefono = (TextView) findViewById(R.id.label_telefono);
		labelTelefono.setText(labelTelefono.getText());
		labelCorreo = (TextView) findViewById(R.id.label_correo);
		labelCorreo.setText(labelCorreo.getText());
        //obtener el paciente
		paciente = baseDatos.obtenerPaciente(idPaciente);
		// Mostrar la informacion del Paciente
		nombre = (EditText) findViewById(R.id.info_nombre);
		nombre.setText(paciente.getNombres());
		apellido = (EditText) findViewById(R.id.info_apellido);
		apellido.setText(paciente.getApellidos());
		direccion = (EditText) findViewById(R.id.info_direccion);
		direccion.setText(paciente.getDireccion());
		telefono = (EditText) findViewById(R.id.info_telefono);
		telefono.setText(paciente.getTelefono());
		correo = (EditText) findViewById(R.id.info_correo);
		correo.setText(paciente.getCorreo());
		//Boton para actualizar
		actualizar = (Button) findViewById(R.id.actualizar);
		actualizar.setText(actualizar.getText());
		
		actualizar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	if(nombre.getText().toString()!=paciente.getNombres())
            		paciente.setNombres(nombre.getText().toString());
            	if(apellido.getText().toString()!=paciente.getApellidos())
            		paciente.setApellidos(apellido.getText().toString());
            	if(direccion.getText().toString()!=paciente.getDireccion())
            		paciente.setDireccion(direccion.getText().toString());
            	if(telefono.getText().toString()!=paciente.getTelefono())
            		paciente.setTelefono(telefono.getText().toString());
            	if(correo.getText().toString()!=paciente.getCorreo())
            		paciente.setCorreo(correo.getText().toString());
                baseDatos.almacenarPaciente(paciente);
                new Notificador().notificar(view.getContext(), "Paciente actualizado correctamente", Toast.LENGTH_SHORT);
                finish();
            }
        });
	}

}
