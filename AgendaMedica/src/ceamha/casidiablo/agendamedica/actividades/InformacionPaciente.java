package ceamha.casidiablo.agendamedica.actividades;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import ceamha.casidiablo.agendamedica.almacenamiento.AgendaDbAdaptador;
import ceamha.casidiablo.agendamedica.esqueleto.Paciente;

public class InformacionPaciente extends Activity{
	
	private TextView nombre;
	private AgendaDbAdaptador baseDatos;
	private Paciente paciente;
	
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paciente);
        //crear y abrir base de datos
        baseDatos = new AgendaDbAdaptador(this);
        baseDatos.abrirBaseDatos();
        //obtener el ID del paciente que me lo pasa la anterior actividad
        Bundle extras = getIntent().getExtras();
        int idPaciente = 0;
		if(extras !=null)
			idPaciente = extras.getInt("idPaciente");
        //obtener el paciente
		paciente = baseDatos.obtenerPaciente(idPaciente);
		nombre = (TextView) findViewById(R.id.info_nombre);
		nombre.setText(nombre.getText()+" "+paciente.getNombres()+" "+paciente.getApellidos());
	}
}
