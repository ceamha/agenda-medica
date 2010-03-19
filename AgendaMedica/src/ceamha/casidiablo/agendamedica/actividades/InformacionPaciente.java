package ceamha.casidiablo.agendamedica.actividades;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import ceamha.casidiablo.agendamedica.almacenamiento.AgendaDbAdaptador;
import ceamha.casidiablo.agendamedica.esqueleto.Paciente;

public class InformacionPaciente extends Activity{
	
	private TextView nombre;
	private EditText nombre2;
	private AgendaDbAdaptador baseDatos;
	private Paciente paciente = new Paciente();
	
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context mContext = getApplicationContext();    
        nombre = (TextView) findViewById(R.id.info_nombre);
        mostrarInformacion(mContext);
        setContentView(R.layout.paciente);
        
	}

	public void mostrarInformacion(Context c){
		try{			
			
			Toast.makeText(c, "y ahora?:"+this.nombre,Toast.LENGTH_SHORT).show();
			paciente = baseDatos.obtenerPaciente(1);			
			nombre.setText(nombre.getText()+"hola");
	        
		}catch(Exception e){
			
		}
	}
}
