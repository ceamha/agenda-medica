package ceamha.casidiablo.agendamedica.actividades;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class AgendaMedica extends ListActivity {
	
	static final String[] CITAS = new String[] {"Primera Cita","Segunda Cita","Tercer Cita"};
	private static final int M_PACIENTES = Menu.FIRST;
	private static final int M_CITAS = Menu.FIRST + 1;
    private static final int M_HORARIO = Menu.FIRST + 2;
    private static final int M_CONSULTAS = Menu.FIRST + 3;
    private static final int M_ESTADISTICAS = Menu.FIRST + 4;
    private static final int SALIR = Menu.FIRST + 5;
    
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);

	  setListAdapter(new ArrayAdapter<String>(this, R.layout.listas, CITAS));

	  ListView lv = getListView();
	  lv.setTextFilterEnabled(true);

	  lv.setOnItemClickListener(new OnItemClickListener() {
	    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	      
	      Toast.makeText(getApplicationContext(), ((TextView) view).getText()+" Hola",
	          Toast.LENGTH_SHORT).show();
	    //  Intent intent = new Intent(HelloListView.this, OtraActividad.class);
	    //  startActivity(intent);
	      
	    }
	  });
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(Menu.NONE, M_PACIENTES, 0, R.string.m_clientes).setIcon(R.drawable.clientes);
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
}