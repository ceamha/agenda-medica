package ceamha.casidiablo.agendamedica.actividades;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import ceamha.casidiablo.agendamedica.almacenamiento.AgendaDbAdaptador;
import ceamha.casidiablo.agendamedica.esqueleto.Paciente;

public class InformacionPaciente extends Activity{
	
	private TextView nombre;
	private TextView apellido;
	private TextView direccion;
	private TextView telefono;
	private TextView correo;
	private static final int M_EDIT = Menu.FIRST;
	private static final int M_ACTIVE = Menu.FIRST + 1;
	private static final int M_ADD_CITA = Menu.FIRST + 2;
	private AgendaDbAdaptador baseDatos;
	private Paciente paciente;
	private Bundle extras; 
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paciente);
        //crear y abrir base de datos
        baseDatos = new AgendaDbAdaptador(this);
        baseDatos.abrirBaseDatos();
        //obtener el ID del paciente que me lo pasa la anterior actividad
        extras = getIntent().getExtras();    
        int idPaciente = 0;
		if(extras !=null)
			idPaciente = extras.getInt("idPaciente");
        //obtener el paciente
		paciente = baseDatos.obtenerPaciente(idPaciente);
		// Mostrar la informacion del Paciente
		nombre = (TextView) findViewById(R.id.info_nombre);
		nombre.setText(paciente.getNombres()+" "+paciente.getApellidos());
		apellido = (TextView) findViewById(R.id.info_apellido);
		apellido.setText(paciente.getApellidos());
		direccion = (TextView) findViewById(R.id.info_direccion);
		direccion.setText(paciente.getDireccion());
		telefono = (TextView) findViewById(R.id.info_telefono);
		telefono.setText(paciente.getTelefono());
		correo = (TextView) findViewById(R.id.info_correo);
		correo.setText(paciente.getCorreo());
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		
		menu.add(Menu.NONE, M_EDIT, 1, R.string.m_actualizar_paciente).setIcon(
				R.drawable.refrescar);
		if(paciente.isActivo()==true){
			menu.add(Menu.NONE, M_ACTIVE, 2, R.string.m_inactivar_paciente).setIcon(R.drawable.inactivar);
			menu.add(Menu.NONE, M_ADD_CITA, 0, R.string.m_citas).setIcon(
					R.drawable.citas);
		}else
			menu.add(Menu.NONE, M_ACTIVE, 2, R.string.m_activar_paciente).setIcon(R.drawable.activar);
		
		return true;
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		super.onMenuItemSelected(featureId, item);
		Intent intent;
		switch (item.getItemId()) {
		case M_EDIT:
			intent = new Intent(InformacionPaciente.this, ActualizarPaciente.class);
			intent.putExtra("idPaciente", paciente.get_id());
			startActivityForResult(intent, CodigosPeticion.ACTUALIZAR_PACIENTE);
			break;
		case M_ADD_CITA:
			intent = new Intent(InformacionPaciente.this, NuevaCita.class);
			startActivityForResult(intent, CodigosPeticion.INSERTAR_CITA);
			break;
		case M_ACTIVE:
			paciente.setActivo(!paciente.isActivo());
			baseDatos.almacenarPaciente(paciente);
			finish();
			break;
		}
		return true;
	}
}
