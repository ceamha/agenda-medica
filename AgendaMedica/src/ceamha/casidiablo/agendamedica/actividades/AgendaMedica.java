package ceamha.casidiablo.agendamedica.actividades;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class AgendaMedica extends Activity {
	
	private static final int CLIENTES = Menu.FIRST;
    private static final int CITAS = Menu.FIRST + 1;
    private static final int HORARIO = Menu.FIRST + 2;
    private static final int CONSULTAS = Menu.FIRST + 3;
    private static final int ESTADISTICAS = Menu.FIRST + 4;
    private static final int SALIR = Menu.FIRST + 5;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(Menu.NONE, CLIENTES, 0, R.string.m_clientes).setIcon(R.drawable.clientes);
        menu.add(Menu.NONE, CITAS, 1, R.string.m_citas).setIcon(R.drawable.citas);
        menu.add(Menu.NONE, HORARIO, 2, R.string.m_horario).setIcon(R.drawable.horario);
        menu.add(Menu.NONE, CONSULTAS, 3, R.string.m_consultas).setIcon(R.drawable.consultas);
        menu.add(Menu.NONE, ESTADISTICAS, 4, R.string.m_estadisticas).setIcon(R.drawable.estadisticas);
        menu.add(Menu.NONE, SALIR, 5, R.string.salir).setIcon(R.drawable.salir);
        return true;
    }
    
}