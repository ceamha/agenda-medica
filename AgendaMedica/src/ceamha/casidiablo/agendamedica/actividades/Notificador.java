package ceamha.casidiablo.agendamedica.actividades;

import android.content.Context;
import android.widget.Toast;

public class Notificador {
	public void notificar(Context contexto, CharSequence texto, int duracion) {
		Toast toast = Toast.makeText(contexto, texto, duracion);
		toast.show();
	}
}
