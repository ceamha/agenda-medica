package ceamha.casidiablo.agendamedica.almacenamiento;

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
	public AgendaDbAdaptador abrirBaseDatos() throws SQLException {
		facilitadorBD = new DatabaseHelper(contexto);
		baseDatos = facilitadorBD.getWritableDatabase();
		return this;
	}

	public void close() {
		facilitadorBD.close();
	}

	@Override
	public long almacenarCita(Cita cita) {
		//datos a almacenar
		ContentValues valoresCita = obtenerValoresCita(cita);
		
		//consultar si ya existe la cita
		Cursor mCursor = baseDatos.query(true, "cita",
				new String[] { "_id" },
				" _id =" + cita.get_id(),
				null, null, null, null, null);
		//si es null, entonces la cita no existe, y por ende se debe crear
		if (mCursor.getCount() == 0)
			return baseDatos.insert("cita", null, valoresCita);
		//actualizarla
		else if (baseDatos.update("cita", valoresCita, " _id =" + cita.get_id(), null) > 0)
				return cita.get_id();
		else
			return -1;
	}

	@Override
	public long almacenarPaciente(Paciente paciente) {
		//datos a almacenar
		ContentValues valoresPaciente = obtenerValoresPaciente(paciente);
		//consultar si ya existe el paciente
		Cursor mCursor = baseDatos.query(true, "paciente",
				new String[] { "_id" },
				" _id =" + paciente.get_id(),
				null, null, null, null, null);
		//si es null, entonces el paciente no existe, y por ende se debe crear
		if (mCursor.getCount() == 0)
			return baseDatos.insert("paciente", null, valoresPaciente);
		//actualizar
		else if (baseDatos.update("paciente", valoresPaciente, " _id = " + paciente.get_id(), null) > 0)
				return paciente.get_id();
		else
			return -1;
		
	}

	@Override
	public boolean inactivarPaciente(Paciente paciente) {
		ContentValues valoresPaciente = obtenerValoresPaciente(paciente);
		return baseDatos.update("paciente", valoresPaciente, " _id =" + paciente.get_id(), null) > 0;
	}

	@Override
	public Cita obtenerCita(int id) {
		Cursor mCursor = baseDatos.query(true, "cita", new String[] { "_id" },
				" _id = " + id, null, null, null, null, null);
		if (mCursor != null)
			mCursor.moveToFirst();
		
		return crearCita(mCursor);
	}

	@Override
	public Cursor obtenerCitas() {
		Cursor cursor = baseDatos.query("cita", new String[] { "_id", "fecha", "horaProgramadaInicio",
				"horaProgramadaFin", "observaciones", "idPaciente", "horaInicio",
				"horaFin", "estado" }, null, null, null, null, null);
		return cursor;
	}

	@Override
	public Cursor obtenerCitas(String desde, String hasta) {
		Cursor cursor = baseDatos.query("cita", new String[] { "_id", "fecha", "horaProgramadaInicio",
				"horaProgramadaFin", "observaciones", "idPaciente", "horaInicio",
				"horaFin", "estado" },
				"fechaProgramadaFin >= " + desde + " AND fechaProgramadaFin <= " + hasta,
				null, null, null, null);
		return cursor;
	}

	@Override
	public Cursor obtenerPacientes() {
		Cursor cursor = baseDatos.query("paciente", new String[] { "_id",
				"nombres", "apellidos", "documento", "telefono",
				"direccion", "correo", "fechaNacimiento", "estado"},
				null, null, null, null, null);
		return cursor;
	}

	@Override
	public Paciente obtenerPaciente(int id) {
		Cursor cursor = baseDatos.query("table", new String[] { "_id",
				"nombres", "apellidos", "documento", "telefono",
				"direccion", "correo", "fechaNacimiento", "estado"},
				"_id = " + id, null, null, null, null);
		if(cursor != null)
			cursor.moveToFirst();
		return crearPaciente(cursor);
	}
	
	/**
	 * Devuelve un objeto tipo ContentValues con los valores
	 * que debe tener la tabla de paciente 
	 * @param paciente
	 * @return
	 */
	private ContentValues obtenerValoresPaciente(Paciente paciente) {
		ContentValues valoresPaciente = new ContentValues();
		valoresPaciente.put("nombres", paciente.getNombres());
		valoresPaciente.put("apellidos", paciente.getApellidos());
		valoresPaciente.put("documento", paciente.getDocumento());
		valoresPaciente.put("telefono", paciente.getDocumento());
		valoresPaciente.put("direccion", paciente.getDireccion());
		valoresPaciente.put("correo", paciente.getCorreo());
		valoresPaciente.put("fechaNacimiento", paciente.getFechaNacimiento());
		valoresPaciente.put("estado", paciente.isActivo());
		return valoresPaciente; 
	}
	
	/**
	 * Devuelve un objeto tipo ContentValues con los valores
	 * que debe tener la tabla de cita
	 * @param cita
	 * @return
	 */
	private ContentValues obtenerValoresCita(Cita cita) {
		ContentValues valoresCita = new ContentValues();
		valoresCita.put("fecha", cita.getFecha());
		valoresCita.put("horaProgramadaInicio", cita.getHoraProgramadaInicio());
		valoresCita.put("horaProgramadaFin", cita.getHoraProgramadaFin());
		valoresCita.put("observaciones", cita.getObservaciones());
		valoresCita.put("idPaciente", cita.getIdPaciente());
		valoresCita.put("horaInicio", cita.getHoraInicio());
		valoresCita.put("horaFin", cita.getHoraFin());
		valoresCita.put("estado", cita.isEstado());
		return valoresCita; 
	}
	
	/**
	 * A partir de un cursor, crea y devuelve un objeto tipo Cita
	 * @param cursor
	 * @return
	 */
	private Cita crearCita(Cursor cursor) {
		Cita cita = new Cita();
		cita.set_id(cursor.getInt(cursor.getColumnIndex("_id")));
		cita.setFecha(cursor.getString(cursor.getColumnIndex("fecha")));
		cita.setHoraProgramadaInicio(cursor.getString(cursor.getColumnIndex("horaProgramadaInicio")));
		cita.setHoraProgramadaFin(cursor.getString(cursor.getColumnIndex("horaProgramadaFin")));
		cita.setObservaciones(cursor.getString(cursor.getColumnIndex("observaciones")));
		cita.setIdPaciente(cursor.getInt(cursor.getColumnIndex("idPaciente")));
		cita.setHoraInicio(cursor.getString(cursor.getColumnIndex("horaInicio")));
		cita.setHoraFin(cursor.getString(cursor.getColumnIndex("horaFin")));
		cita.setEstado(cursor.getInt(cursor.getColumnIndex("horaFin")) == 1);
		return cita;
	}
	
	/**
	 * A partir de un cursor, crea y devuelve un objeto tipo Paciente
	 * @param cursor
	 * @return
	 */
	private Paciente crearPaciente(Cursor cursor) {
		Paciente paciente = new Paciente();
		paciente.set_id(cursor.getInt(cursor.getColumnIndex("_id")));
		paciente.setNombres(cursor.getString(cursor.getColumnIndex("nombres")));
		paciente.setApellidos(cursor.getString(cursor.getColumnIndex("apellidos")));
		paciente.setDocumento(Long.parseLong(cursor.getString(cursor.getColumnIndex("documento"))));
		paciente.setTelefono(cursor.getString(cursor.getColumnIndex("telefono")));
		paciente.setDireccion(cursor.getString(cursor.getColumnIndex("direccion")));
		paciente.setCorreo(cursor.getString(cursor.getColumnIndex("correo")));
		paciente.setFechaNacimiento(cursor.getString(cursor.getColumnIndex("fechaNacimiento")));
		paciente.setActivo(cursor.getInt(cursor.getColumnIndex("estado")) == 1);
		return paciente;
	}
}