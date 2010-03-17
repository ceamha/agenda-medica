package ceamha.casidiablo.agendamedica.actividades;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class AdministradorImagenes {
	public static Drawable fetchDrawable(String urlString) {
		try {
			InputStream is = fetch(urlString);
			Drawable drawable = Drawable.createFromStream(is, "src");
			return drawable;
		} catch (MalformedURLException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
	}

	public static void fetchDrawableOnThread(final String urlString,
			final ImageView imageView){
		Drawable drawable = fetchDrawable(urlString);
		imageView.setImageDrawable(drawable);
	}

	private static InputStream fetch(String urlString) throws MalformedURLException,
			IOException {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet request = new HttpGet(urlString);
		HttpResponse response = httpClient.execute(request);
		return response.getEntity().getContent();
	}
}