package ceamha.casidiablo.agendamedica.almacenamiento;


import android.database.Cursor;
import ceamha.casidiablo.agendamedica.esqueleto.Cita;
import ceamha.casidiablo.agendamedica.esqueleto.Paciente;

public interface Almacenador {
	/**
	 * Almacena o actualiza un paciente.
	 * @param paciente
	 * @return retorna el ID del registro almacenado
	 */
	public long almacenarPaciente(Paciente paciente);
	
	/**
	 * Dado un ID retorna un objeto que hace referencia a la persona
	 * @param _id
	 * @return Persona o null si no existe
	 */
	public Paciente obtenerPaciente(int _id);
	
	/**
	 * Dado un ID retorna un objeto que hace referencia a la persona
	 * @param _id
	 * @return Persona o null si no existe
	 */
	public Cursor obtenerPacientes();
	
	/**
	 * Inactiva un paciente
	 * @param paciente
	 * @return
	 */
	public boolean inactivarPaciente(Paciente paciente);
	
	/**
	 * Almacena o actualiza una cita
	 * @param cita
	 * @return retorna el ID del registro almacenado
	 */
	public long almacenarCita(Cita cita);
	
	/**
	 * Dado un ID retorna la cita correspondiente
	 * @param cita
	 * @return retorna el ID del registro almacenado
	 */
	public Cita obtenerCita(int _id);
	
	/**
	 * Devuelve un Cursor con todas las citas
	 * @return
	 */
	public Cursor obtenerCitas();
	
	/**
	 * Devuelve un Cursor con todas un rango de citas
	 * @return
	 */
	public Cursor obtenerCitas(String desde, String hasta);

}
