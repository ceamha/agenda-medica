package ceamha.casidiablo.agendamedica.esqueleto;

public class Cita {
	private int _id;
	private String motivo;
	private String fecha;
	private String horaProgramadaInicio;
	private String horaProgramadaFin;
	private String observaciones;
	private int idPaciente;
	private String horaInicio;
	private String horaFin;
	private boolean estado;
	
	public Cita(){
		set_id(0);
		setFecha("00-00-0000");
		setHoraProgramadaInicio("");
		setHoraProgramadaFin("");
		setObservaciones("");
		setIdPaciente(0);
		setHoraInicio("");
		setHoraFin("");
		setEstado(true);
	}

	/**
	 * @return the _id
	 */
	public int get_id() {
		return _id;
	}
	/**
	 * @param id the _id to set
	 */
	public void set_id(int id) {
		_id = id;
	}
	/**
	 * @return the fecha
	 */
	public String getFecha() {
		return fecha;
	}
	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	/**
	 * @return the horaProgramadaInicio
	 */
	public String getHoraProgramadaInicio() {
		return horaProgramadaInicio;
	}
	/**
	 * @param horaProgramadaInicio the horaProgramadaInicio to set
	 */
	public void setHoraProgramadaInicio(String horaProgramadaInicio) {
		this.horaProgramadaInicio = horaProgramadaInicio;
	}
	/**
	 * @return the horaProgramadaFin
	 */
	public String getHoraProgramadaFin() {
		return horaProgramadaFin;
	}
	/**
	 * @param horaProgramadaFin the horaProgramadaFin to set
	 */
	public void setHoraProgramadaFin(String horaProgramadaFin) {
		this.horaProgramadaFin = horaProgramadaFin;
	}
	/**
	 * @return the observaciones
	 */
	public String getObservaciones() {
		return observaciones;
	}
	/**
	 * @param observaciones the observaciones to set
	 */
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	/**
	 * @return the idPaciente
	 */
	public int getIdPaciente() {
		return idPaciente;
	}
	/**
	 * @param idPaciente the idPaciente to set
	 */
	public void setIdPaciente(int idPaciente) {
		this.idPaciente = idPaciente;
	}
	/**
	 * @return the horaInicio
	 */
	public String getHoraInicio() {
		return horaInicio;
	}
	/**
	 * @param horaInicio the horaInicio to set
	 */
	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}
	/**
	 * @return the horaFin
	 */
	public String getHoraFin() {
		return horaFin;
	}
	/**
	 * @param horaFin the horaFin to set
	 */
	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}
	/**
	 * @param estado the estado to set
	 */
	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	/**
	 * @return the estado
	 */
	public boolean isEstado() {
		return estado;
	}
	@Override
	public String toString(){
		return getFecha()+" "+getHoraProgramadaInicio();
	}

	/**
	 * @param motivo the motivo to set
	 */
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	/**
	 * @return the motivo
	 */
	public String getMotivo() {
		return motivo;
	}
}
