package ceamha.casidiablo.agendamedica.actividades;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class DespliegueEstadisticas extends Activity {
	
	@Override
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.despliegue_estadisticas);
		generarImagen();
	}
	/**
	 * Crea la imagen a partir de una URL de Google Chart
	 */
	public void generarImagen(){
		ImageView img = (ImageView)findViewById(R.id.imagen);
		AdministradorImagenes.fetchDrawableOnThread("http://10.10.2.221/chart.png", img);
	}
}