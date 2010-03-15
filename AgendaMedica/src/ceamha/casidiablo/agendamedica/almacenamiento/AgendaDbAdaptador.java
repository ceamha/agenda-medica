package ceamha.casidiablo.agendamedica.almacenamiento;

import java.util.Vector;

import ceamha.casidiablo.agendamedica.esqueleto.Cita;
import ceamha.casidiablo.agendamedica.esqueleto.Paciente;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Esta clase define las operaciones CRUD basicas para almacenar los datos de la
 * agenda médica.
 */
public class AgendaDbAdaptador implements Almacenador {

	public static final String KEY_TITLE = "title";
	public static final String KEY_BODY = "body";
	public static final String KEY_ROWID = "_id";

	private static final String TAG = "AgendaDbAdaptador";
	private DatabaseHelper facilitadorBD;
	private SQLiteDatabase baseDatos;

	private static final String NOMBRE_BASE_DATOS = "agenda";
	private static final int VERSION_BASE_DATOS = 1;

	private final Context contexto;

	private static class DatabaseHelper extends SQLiteOpenHelper {

		DatabaseHelper(Context context) {
			super(context, NOMBRE_BASE_DATOS, null, VERSION_BASE_DATOS);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("create table paciente (_id integer primary key autoincrement, "
							+ "nombres text not null,"
							+ "apellidos text not null,"
							+ "documento integer not null,"
							+ "telefono text null,"
							+ "direccion text null,"
							+ "correo text null,"
							+ "fechaNacimiento date,"
							+ "estado bool);");
			db.execSQL("create table cita (_id integer primary key autoincrement, "
							+ "fecha date not null,"
							+ "horaProgramadaInicio time not null,"
							+ "horaProgramadaFin time not null,"
							+ "observaciones text not null,"
							+ "idPaciente int not null,"
							+ "horaInicio time,"
							+ "horaFin time,"
							+ "estado bool);");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int anteriorVersion,
				int nuevaVersion) {
			Log.w(TAG, "Actualizando base de datos desde la version "
					+ anteriorVersion + " a " + nuevaVersion
					+ ", lo cual destruira todos los datos anteriores");
			db.execSQL("DROP TABLE IF EXISTS paciente");
			db.execSQL("DROP TABLE IF EXISTS cita");
			onCreate(db);
		}
	}

	/**
	 * Constructor - obtiene el contexto para permitir abrir/crear
	 * la base de datos
	 * 
	 * @param contexto
	 *            Contexto con el que se trabaja
	 */
	public AgendaDbAdaptador(Context contexto) {
		this.contexto = contexto;
	}

	/**
	 * Abre la base de datos. Si no puede ser creada, trata de crear una nueva.
	 * Si no puede ser creada, arroja una excepcion.
	 * 
	 * @return this (auto referencia, lo cual permite hacer una autoreferencia)
	 * @throws SQLException
	 *             si la base de datos no puede ser creada o abierta
	 */
	public AgendaDbAdaptador open() throws SQLException {
		facilitadorBD = new DatabaseHelper(contexto);
		baseDatos = facilitadorBD.getWritableDatabase();
		return this;
	}

	public void close() {
		facilitadorBD.close();
	}

	/**
	 * Create a new note using the title and body provided. If the note is
	 * successfully created return the new rowId for that note, otherwise return
	 * a -1 to indicate failure.
	 * 
	 * @param title
	 *            the title of the note
	 * @param body
	 *            the body of the note
	 * @return rowId or -1 if failed
	 */
	public long createNote(String title, String body) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_TITLE, title);
		initialValues.put(KEY_BODY, body);

		return baseDatos.insert("paciente", null, initialValues);
	}

	/**
	 * Delete the note with the given rowId
	 * 
	 * @param rowId
	 *            id of note to delete
	 * @return true if deleted, false otherwise
	 */
	public boolean deleteNote(long rowId) {

		return baseDatos.delete("paciente", KEY_ROWID + "=" + rowId, null) > 0;
	}

	/**
	 * Return a Cursor over the list of all notes in the database
	 * 
	 * @return Cursor over all notes
	 */
	public Cursor fetchAllNotes() {

		return baseDatos.query("paciente", new String[] { KEY_ROWID, KEY_TITLE,
				KEY_BODY }, null, null, null, null, null);
	}

	/**
	 * Return a Cursor positioned at the note that matches the given rowId
	 * 
	 * @param rowId
	 *            id of note to retrieve
	 * @return Cursor positioned to matching note, if found
	 * @throws SQLException
	 *             if note could not be found/retrieved
	 */
	public Cursor fetchNote(long rowId) throws SQLException {

		Cursor mCursor =

		baseDatos.query(true, "paciente", new String[] { KEY_ROWID, KEY_TITLE,
				KEY_BODY }, KEY_ROWID + "=" + rowId, null, null, null, null,
				null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;

	}

	/**
	 * Update the note using the details provided. The note to be updated is
	 * specified using the rowId, and it is altered to use the title and body
	 * values passed in
	 * 
	 * @param rowId
	 *            id of note to update
	 * @param title
	 *            value to set note title to
	 * @param body
	 *            value to set note body to
	 * @return true if the note was successfully updated, false otherwise
	 */
	public boolean updateNote(long rowId, String title, String body) {
		ContentValues args = new ContentValues();
		args.put(KEY_TITLE, title);
		args.put(KEY_BODY, body);

		return baseDatos.update("paciente", args, KEY_ROWID + "=" + rowId, null) > 0;
	}

	@Override
	public long almacenarCita(Cita cita) {
		ContentValues valoresCita = new ContentValues();
		valoresCita.put("fecha", cita.getFecha());
		valoresCita.put("horaProgramadaInicio", cita.getHoraProgramadaInicio());
		valoresCita.put("horaProgramadaFin", cita.getHoraProgramadaFin());
		valoresCita.put("observaciones", cita.getObservaciones());
		valoresCita.put("idPaciente", cita.getIdPaciente());
		valoresCita.put("horaInicio", cita.getHoraInicio());
		valoresCita.put("horaFin", cita.getHoraFin());
		return baseDatos.insert("cita", null, valoresCita);
	}

	@Override
	public long almacenarPaciente(Paciente paciente) {
		ContentValues valoresPaciente = new ContentValues();
		valoresPaciente.put("nombres", paciente.getNombres());
		valoresPaciente.put("apellidos", paciente.getApellidos());
		valoresPaciente.put("documento", paciente.getDocumento());
		valoresPaciente.put("telefono", paciente.getDocumento());
		valoresPaciente.put("direccion", paciente.getDireccion());
		valoresPaciente.put("correo", paciente.getCorreo());
		return baseDatos.insert("paciente", null, valoresPaciente);
	}

	@Override
	public void inactivarPaciente(Paciente paciente) {
		/*ContentValues args = new ContentValues();
		args.put(KEY_TITLE, title);
		args.put(KEY_BODY, body);
		return baseDatos.update("paciente", args, KEY_ROWID + "=" + rowId, null) > 0;*/
	}

	@Override
	public int obtenerCita(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Vector<Cita> obtenerCitas() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<Cita> obtenerCitas(String desde, String hasta) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<Paciente> obtenerPacientes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Paciente obtenerPersona(int id) {
		// TODO Auto-generated method stub
		return null;
	}
}