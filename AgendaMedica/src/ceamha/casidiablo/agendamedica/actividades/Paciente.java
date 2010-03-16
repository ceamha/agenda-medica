package ceamha.casidiablo.agendamedica.actividades;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Paciente extends Activity{
	
	private TextView nombres;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
	//	nombres = (TextView) findViewById(R.id.nombres);
	//	nombres.setText("El nombre del Paciente");
		setContentView(R.layout.paciente);
		
	}
}
