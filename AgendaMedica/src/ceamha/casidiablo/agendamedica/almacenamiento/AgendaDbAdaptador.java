package ceamha.casidiablo.agendamedica.almacenamiento;

import ceamha.casidiablo.agendamedica.actividades.Notificador;
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
							+ "motivo text not null,"
							+ "fecha date not null,"
							+ "horaProgramadaInicio time not null,"
							+ "horaProgramadaFin time not null,"
							+ "observaciones text not null,"
							+ "idPaciente int not null,"
							+ "horaInicio time,"
							+ "horaFin time,"
							+ "estado bool);");
			//crear datos de ejemplo
			db.execSQL("INSERT INTO paciente (nombres, apellidos, documento, telefono, " +
					"direccion, correo, fechaNacimiento, estado) " +
					"VALUES ('Cristian Camilo', 'Castiblanco Hernandez', '1032421210'," +
					"'7253464', 'Cra 18 # 27d - 11 sur', 'cristian@elhacker.net', '9-19-1988', 1);");
			db.execSQL("INSERT INTO paciente (nombres, apellidos, documento, telefono," +
					"direccion, correo, fechaNacimiento, estado)" +
					"VALUES ('Cindy Esmeralda', 'Arenas Madronero', '1022836547'," +
					"'7852587', 'Cll 55 # 87d - 85 sur', 'ceamha@gmail.com', '9-2-1988', 1);");
			db.execSQL("INSERT INTO paciente (nombres, apellidos, documento, telefono," +
					"direccion, correo, fechaNacimiento, estado)" +
					"VALUES ('Alan', 'Turing', '52458748'," +
					"'5896455', 'Diag 89 # 56 - 55', 'aturing@slakers.com', '11-23-1965', 1);");
			db.execSQL("INSERT INTO paciente (nombres, apellidos, documento, telefono," +
					"direccion, correo, fechaNacimiento, estado)" +
					"VALUES ('Charles', 'Babbage', '66587445'," +
					"'6884646', 'Kr 89 # 52 - 55d', 'cbabbage@calculator.com', '2-20-1968', 1);");
			db.execSQL("INSERT INTO paciente (nombres, apellidos, documento, telefono," +
					"direccion, correo, fechaNacimiento, estado)" +
					"VALUES ('Isaac', 'Newton', '95682255'," +
					"'5524635', 'Cll 89 # 52 - 63b', 'isaac@gravity.com', '2-20-1978', 1);");
			db.execSQL("INSERT INTO paciente (nombres, apellidos, documento, telefono," +
					"direccion, correo, fechaNacimiento, estado)" +
					"VALUES ('Dennis', 'Ritchie', '35698741'," +
					"'6847595', 'Cra 25 # 3a - 55', 'dritchie@unix.org', '6-10-1960', 1);");
			db.execSQL("INSERT INTO paciente (nombres, apellidos, documento, telefono," +
					"direccion, correo, fechaNacimiento, estado)" +
					"VALUES ('Keneth', 'Thompson', '102245563'," +
					"'6365748', 'St 5th av # 52 - 52', 'keneth@unix.org', '10-10-1961', 1);");
			db.execSQL("INSERT INTO paciente (nombres, apellidos, documento, telefono," +
					"direccion, correo, fechaNacimiento, estado)" +
					"VALUES ('Vinton', 'Cerf', '65689744'," +
					"'6564699', 'Cll 27d #18 - 38sur', 'vinton@arpanet.org', '6-6-1966', 1);");
			//insertar citas de prueba
			db.execSQL("INSERT INTO cita (motivo, fecha, horaProgramadaInicio, horaProgramadaFin, " +
					"observaciones, idPaciente, horaInicio, horaFin, estado)" +
					"VALUES ('Chequeo de rutina', '3-27-2010', '13:00:00', '13:30:00'," +
					"'Todo parece estar bien con el paciente, aunque debe subir de peso porque se " +
					"encuentra demasiado flaco', 1, '13:00:00', '13:20:00', 1);");
			db.execSQL("INSERT INTO cita (motivo, fecha, horaProgramadaInicio, horaProgramadaFin, " +
					"observaciones, idPaciente, horaInicio, horaFin, estado)" +
					"VALUES ('Jaqueca', '3-28-2010', '13:00:00', '13:30:00'," +
					"'', 1, '', '', 1);");
			db.execSQL("INSERT INTO cita (motivo, fecha, horaProgramadaInicio, horaProgramadaFin, " +
					"observaciones, idPaciente, horaInicio, horaFin, estado)" +
					"VALUES ('Migrana', '3-27-2010', '13:30:00', '14:00:00'," +
					"'', 2, '', '', 1);");
			db.execSQL("INSERT INTO cita (motivo, fecha, horaProgramadaInicio, horaProgramadaFin, " +
					"observaciones, idPaciente, horaInicio, horaFin, estado)" +
					"VALUES ('Mareo', '3-28-2010', '8:30:00', '9:00:00'," +
					"'', 3, '', '', 0);");
			db.execSQL("INSERT INTO cita (motivo, fecha, horaProgramadaInicio, horaProgramadaFin, " +
					"observaciones, idPaciente, horaInicio, horaFin, estado)" +
					"VALUES ('Calculos Renales', '3-28-2010', '9:30:00', '10:00:00'," +
					"'', 4, '', '', 1);");
			db.execSQL("INSERT INTO cita (motivo, fecha, horaProgramadaInicio, horaProgramadaFin, " +
					"observaciones, idPaciente, horaInicio, horaFin, estado)" +
					"VALUES ('Le cayo una manzana en la cabeza', '3-28-2010', '10:00:00', '10:30:00'," +
					"'', 5, '', '', 1);");
			db.execSQL("INSERT INTO cita (motivo, fecha, horaProgramadaInicio, horaProgramadaFin, " +
					"observaciones, idPaciente, horaInicio, horaFin, estado)" +
					"VALUES ('Heridas de guerra', '3-29-2010', '11:30:00', '12:00:00'," +
					"'', 8, '', '', 1);");
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
		Cursor cursor = baseDatos.rawQuery("SELECT * FROM cita WHERE _id = " + id, null);
		cursor.moveToFirst();
		return crearCita(cursor);
	}

	@Override
	public Cursor obtenerCitas() {
		Cursor cursor = baseDatos.rawQuery("SELECT cita._id, motivo, fecha, horaProgramadaInicio," +
				"horaProgramadaFin, observaciones, paciente.nombres||' '||paciente.apellidos AS paciente," +
				"horaInicio, horaFin, cita.estado FROM cita, paciente WHERE paciente._id = cita.idPaciente " +
				" AND cita.estado = 1 ORDER BY fecha, horaProgramadaInicio ASC", null); 
		return cursor;
	}

	@Override
	public Cursor obtenerCitas(String desde, String hasta) {
		Cursor cursor = baseDatos.query("cita", new String[] { "_id", "fecha", "horaProgramadaInicio",
				"horaProgramadaFin", "observaciones", "idPaciente", "horaInicio",
				"horaFin", "estado" },
				"horaProgramadaFin >= " + desde + " AND horaProgramadaFin <= " + hasta,
				null, null, null, null);
		return cursor;
	}

	@Override
	public Cursor obtenerPacientes() {
		Cursor cursor = baseDatos.query("paciente", new String[] { "_id",
				"nombres", "apellidos", "documento", "telefono",
				"direccion", "correo", "fechaNacimiento", "estado"},
				"estado=1", null, null, null, null);
		return cursor;
	}

	@Override
	public Paciente obtenerPaciente(int id){
		Cursor cursor = baseDatos.query("paciente", new String[] { "_id",
				"nombres", "apellidos", "documento", "telefono",
				"direccion", "correo", "fechaNacimiento", "estado"},
				"_id = " + id, null, null, null, null);
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
		valoresCita.put("motivo", cita.getMotivo());
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
		cita.setMotivo(cursor.getString(cursor.getColumnIndex("motivo")));
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

	@Override
	public Cursor consultaSQLDirecta(String consulta){
		return baseDatos.rawQuery(consulta, null); 
	}

	@Override
	public Cursor obtenerCitasDisponibles(String fecha) {
		Cursor cursor = null;
		try{
			cursor = baseDatos.rawQuery("SELECT c._id, c.horaProgramadaInicio, c.horaProgramadaFin," +
					"p.nombres||' '||p.apellidos AS nombresPaciente " +
					"FROM cita c, paciente p WHERE c.fecha LIKE '"+fecha+"' AND c.idPaciente = p._id " +
					"ORDER BY c.fecha, c.horaProgramadaInicio ASC", null);
		}catch(SQLException e){new Notificador().notificar(contexto, "Error: "+e.toString(), 1);}
		return cursor;
	}
}