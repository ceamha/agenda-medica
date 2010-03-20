package ceamha.casidiablo.agendamedica.actividades;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class BuscarPacientes extends Activity{
	
	private TextView labelBuscar;
	private EditText dato;
	private Button buscar;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buscador_pacientes);
    
        //Titulos
		labelBuscar = (TextView) findViewById(R.id.label_buscar);
		labelBuscar.setText(labelBuscar.getText());
		dato = (EditText) findViewById(R.id.buscar_dato);
		dato.setText(dato.getText());
		buscar = (Button) findViewById(R.id.buscar);
		buscar.setText(buscar.getText());
		
		buscar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	Intent intent = new Intent(BuscarPacientes.this, ListaPacientesBuscados.class);
    			intent.putExtra("dato", dato.getText().toString());
    			startActivityForResult(intent, CodigosPeticion.BUSCAR_PACIENTE);
            }
        });
	}

}
