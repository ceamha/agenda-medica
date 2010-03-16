package ceamha.casidiablo.agendamedica.actividades;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import ceamha.casidiablo.agendamedica.almacenamiento.AgendaDbAdaptador;

public class AgendaMedica extends ListActivity {
	
	private static final int M_PACIENTES = Menu.FIRST;
	private static final int M_CITAS = Menu.FIRST + 1;
    private static final int M_HORARIO = Menu.FIRST + 2;
    private static final int M_CONSULTAS = Menu.FIRST + 3;
    private static final int M_ESTADISTICAS = Menu.FIRST + 4;
    private static final int SALIR = Menu.FIRST + 5;
    private AgendaDbAdaptador baseDatos;
    
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  //crear el objeto de la conexion a la base de datos
	  baseDatos = new AgendaDbAdaptador(this);
	  baseDatos.abrirBaseDatos();
	  citasDelDia();
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(Menu.NONE, M_PACIENTES, 0, R.string.m_pacientes).setIcon(R.drawable.clientes);
        menu.add(Menu.NONE, M_CITAS, 1, R.string.m_citas).setIcon(R.drawable.citas);
        menu.add(Menu.NONE, M_HORARIO, 2, R.string.m_horario).setIcon(R.drawable.horario);
        menu.add(Menu.NONE, M_CONSULTAS, 3, R.string.m_consultas).setIcon(R.drawable.consultas);
        menu.add(Menu.NONE, M_ESTADISTICAS, 4, R.string.m_estadisticas).setIcon(R.drawable.estadisticas);
        menu.add(Menu.NONE, SALIR, 5, R.string.salir).setIcon(R.drawable.salir);
        return true;
    }
	
	
	@Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
		super.onMenuItemSelected(featureId, item);
     	Intent intent;
           switch(item.getItemId()) {
	           case M_PACIENTES:
	       	      intent = new Intent(AgendaMedica.this, MenuPacientes.class);
	       	      startActivity(intent);
	               break;
	           case M_CITAS:
	        	   intent = new Intent(AgendaMedica.this, MenuCitas.class);
		       	   startActivity(intent);
	               break;
	           case M_HORARIO:
	               //f_euros_a_pesetas();
	               break;
	           case M_CONSULTAS:
	               //f_pesetas_a_euros();
	               break;
	           case M_ESTADISTICAS:
	               //f_euros_a_pesetas();
	               break;
	           case SALIR:
	               salir();
	               break;
	           }
           return true;
    }
	
	public void salir()
    {
        setResult(RESULT_OK);
        finish();
    }
	
	public void citasDelDia(){
		try{
			Cursor cursor = baseDatos.obtenerCitas();
			//avisar a la actividad que se usará un cursor
			startManagingCursor(cursor);
	        
	        // Crear un array para especificar los campos que queremos 
			//mostrar en la lista (solo la fecha, de momento)
	        String[] desde = new String[]{"fecha"};
	        
	        // Y un array de los campos que queremos enlazar
	        int[] para = new int[]{R.id.lista};
	        
	        // Now create a simple cursor adapter and set it to display
	        SimpleCursorAdapter citas = new SimpleCursorAdapter(this, R.layout.lista_citas, cursor, desde, para);
	        setListAdapter(citas);
		}catch(Exception e){
			new Notificador().notificar(getApplicationContext(), e.toString(), Toast.LENGTH_LONG);
		}
	}
}