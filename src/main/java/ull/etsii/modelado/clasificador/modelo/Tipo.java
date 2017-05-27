package ull.etsii.modelado.clasificador.modelo;

public class Tipo 
{
	private String m_Nombre;
	private int m_Cantidad;
	
	public Tipo(String nombre, int cantidad)
	{
		m_Nombre = nombre;
		m_Cantidad = cantidad;
	}

	/**
	 * @param m_Nombre the m_Nombre to set
	 */
	public void setNombre(String m_Nombre) {
		this.m_Nombre = m_Nombre;
	}

	/**
	 * @return the m_Nombre
	 */
	public String getNombre() {
		return m_Nombre;
	}

	/**
	 * @param m_Cantidad the m_Cantidad to set
	 */
	public void setCantidad(int m_Cantidad) {
		this.m_Cantidad = m_Cantidad;
	}

	/**
	 * @return the m_Cantidad
	 */
	public int getCantidad() {
		return m_Cantidad;
	}
	
}
