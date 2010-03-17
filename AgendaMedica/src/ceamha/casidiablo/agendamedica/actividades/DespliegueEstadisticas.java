package ceamha.casidiablo.agendamedica.actividades;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
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
		Drawable imagen = crearImagen(this, "http://chart.apis.google.com/chart?cht=bvs&chs=150x100&chbh=15,10&chd=t2:10,20,30,20,70,80|20,10,5,20,30,10|10,0,20,15,60,40,30&chds=0,120&chco=224499,009900&chm=D,76A4FB,2,0,3|N,FF0000,-1,-1,10");
		ImageView img = new ImageView(this);
		img = (ImageView)findViewById(R.id.imagen);
		img.setImageDrawable(imagen);
	}
	/**
	 * Devuelve un objeto Drawable con la imagen
	 * @param ctx
	 * @param url
	 * @return
	 */
	private Drawable crearImagen(Context ctx, String url) {
		try {
			//obtener el flujo de bytes de la imagen
			InputStream flujoEntrada = (InputStream) this.fetch(url);
			Drawable d = Drawable.createFromStream(flujoEntrada, "src");
			return d;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * Descarga un archivo
	 * @param direccion
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public Object fetch(String direccion) throws MalformedURLException,IOException {
		URL url = new URL(direccion);
		return url.getContent();
	}
}