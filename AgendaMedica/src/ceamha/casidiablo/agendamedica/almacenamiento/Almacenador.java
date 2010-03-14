package ceamha.casidiablo.agendamedica.almacenamiento;

import java.util.Vector;

import ceamha.casidiablo.esqueleto.Cita;
import ceamha.casidiablo.esqueleto.Paciente;

public interface Almacenador {
	/**
	 * Almacena o actualiza un paciente.
	 * @param paciente
	 * @return retorna el ID del registro almacenado
	 */
	public int almacenarPaciente(Paciente paciente);
	
	/**
	 * Dado un ID retorna un objeto que hace referencia a la persona
	 * @param _id
	 * @return Persona o null si no existe
	 */
	public Paciente obtenerPersona(int _id);
	
	/**
	 * Inactiva un paciente
	 * @param paciente
	 * @return
	 */
	public void inactivarPaciente(Paciente paciente);
	
	/**
	 * Almacena o actualiza una cita
	 * @param cita
	 * @return retorna el ID del registro almacenado
	 */
	public int almacenarCita(Cita cita);
	
	/**
	 * Dado un ID retorna la cita correspondiente
	 * @param cita
	 * @return retorna el ID del registro almacenado
	 */
	public int obtenerCita(int _id);
	
	/**
	 * Devuelve un Vector con todas las citas
	 * @return
	 */
	public Vector<Cita> obtenerCitas();
	
	/**
	 * Devuelve un Vector con todas un rango de citas
	 * @return
	 */
	public Vector<Cita> obtenerCitas(String desde, String hasta);
	
}
