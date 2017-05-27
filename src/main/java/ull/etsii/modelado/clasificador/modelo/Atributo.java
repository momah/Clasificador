package ull.etsii.modelado.clasificador.modelo;

/**
 * Clase utilizada para almacenar todos los atributos de una muestra.
 * @author José Oliver Martínez Novo
 *
 */
public class Atributo
{
	private String m_Titulo;
	private Double m_MinValue;
	private Double m_MaxValue;
	private Double m_Peso;
	private Double m_Media;
	private Double m_DesviacionTipica;
	private Double m_SumaDesviacionTipica;
	private Double m_Value;
	private Boolean m_IsNormalizado = false;
	private Boolean m_IsEstandarizado = true;
	
	/**
	 * Constructor que crea una instancia de la clase asignandole un valor.
	 * @param value (Double) Representa el valor del atributo.
	 */
	public Atributo(double value)
	{
		this(null, value);
	}
	
	/**
	 * Constructor que crea una instancia de la clase asignandole titulo y valor.
	 * @param titulo (String) Cadena que representa el nombre del atributo.
	 * @param value (Double) Representa el valor del atributo.
	 */
	public Atributo(String titulo, double value)
	{
		this.setTitulo(titulo);
		this.setPeso(1D);
		this.setValue(value);
	}

	/**
	 * Establece el título del atributo.
	 * @param titulo (String) Cadena que representa el titulo del atributo.
	 */
	public void setTitulo(String titulo) 
	{
		this.m_Titulo = titulo;
	}

	/**
	 * Obtiene el título del atributo.
	 * @return (String) Cadena que representa el titulo del atributo.
	 */
	public String getTitulo() 
	{
		return m_Titulo;
	}

	/**
	 * Establece el valor mínimo que toma el atributo en el conjunto de muestras.
	 * @param (Double) minValue  Representa el valor mínimo a establecer.
	 */
	public void setMinValue(Double minValue) 
	{
		this.m_MinValue = minValue;
	}

	/**
	 * Obtiene el valor mínimo que toma el atributo en el conjunto de muestras.
	 * @return (Double) Valor mínimo del atributo
	 */
	public Double getMinValue() 
	{
		return m_MinValue;
	}

	/**
	 * Establece el valor máximo que toma el atributo en el conjunto de muestras.
	 * @param (Double) maxValue Representa el valor máximo a establecer.
	 */
	public void setMaxValue(Double maxValue)
	{
		this.m_MaxValue = maxValue;
	}

	/**
	 * @param m_Peso the m_Peso to set
	 */
	public void setPeso(Double m_Peso) {
		this.m_Peso = m_Peso;
	}

	/**
	 * @return the m_Peso
	 */
	public Double getPeso() {
		return m_Peso;
	}

	/**
	 * Obtiene el valor máximo que toma el atributo en el conjunto de muestras.
	 * @return (Double) Valor máximo del atributo
	 */
	public Double getMaxValue()
	{
		return m_MaxValue;
	}

	/**
	 * @param m_Media the m_Media to set
	 */
	public void setMedia(Double m_Media) {
		this.m_Media = m_Media;
	}

	/**
	 * @return the m_Media
	 */
	public Double getMedia() {
		return m_Media;
	}

	/**
	 * @param m_DesviacionTipica the m_DesviacionTipica to set
	 */
	public void setDesviacionTipica(Double m_DesviacionTipica) 
	{
		this.m_DesviacionTipica = m_DesviacionTipica;
	}

	/**
	 * @return the m_DesviacionTipica
	 */
	public Double getDesviacionTipica() 
	{
		return m_DesviacionTipica;
	}

	/**
	 * @param m_SumaDesviacionTipica the m_SumaDesviacionTipica to set
	 */
	public void setSumaDesviacionTipica(Double m_SumaDesviacionTipica) {
		this.m_SumaDesviacionTipica = m_SumaDesviacionTipica;
	}

	/**
	 * @return the m_SumaDesviacionTipica
	 */
	public Double getSumaDesviacionTipica() {
		return m_SumaDesviacionTipica;
	}

	/**
	 * Establece el valor del atributo.
	 * @param value (Double) Valor del atributo que se va a establecer.
	 */
	public void setValue(Double value) 
	{
		this.m_Value = value;
	}

	/**
	 * Obtiene el valor del atributo.
	 * @return (Double) Valor del atributo.
	 */
	public Double getValue() 
	{
		return m_Value;
	}
	
	/**
	 * Función que normaliza el valor atributo en un rango entre 0 y 1 en función
	 * de los valores máximo y mínimo que tenga el atributo.
	 * @return (Atributo) Instancia actual normalizada.
	 */
	public Atributo normalizar()
	{
		//if((!this.getMinValue().equals(null)) && (!this.getMaxValue().equals(null)))
		if((this.getMinValue() != null) && (this.getMaxValue() != null))
		{
			this.setValue((this.getValue() - this.getMinValue()) / (this.getMaxValue() - this.getMinValue()));
			m_IsNormalizado = true;
			m_IsEstandarizado = false;
		}
		return this;
	}
	
	/**
	 * Normaliza la desviación tipica del atributo en un rango 0-1.
	 * @param minValue valor minimo
	 * @param maxValue valor maximo
	 * @return this
	 */
	public Atributo normalizarDesviacionTipica(Double minValue, Double maxValue)
	{
		if(this.getDesviacionTipica() != null)
		{
			this.setDesviacionTipica((this.getDesviacionTipica() - minValue) / (maxValue - minValue));					
		}
		return this;
	}
	
	/**
	 * Estandariza el valor del atributo
	 * @return this estandarizazo
	 */
	public Atributo estandarizar()
	{
		if((this.getMedia() != null) && (this.getDesviacionTipica() != null))
		{
			this.setValue((this.getValue() - this.getMedia())/this.getDesviacionTipica());
			m_IsNormalizado = false;
			m_IsEstandarizado = true;
		}
		return this;
	}
	
	/**
	 * Obtiene el valor real del atributo que tenía antes de haber sido normalizado.
	 * @return (Double) Valor del atributo antes de ser normalizado.
	 */
	/*
	public Double getRealValue()
	{
		Double value = null;
		if(this.m_IsNormalizado)
		{
			value = ((this.getValue() * (this.getMaxValue() - this.getMinValue())) + this.getMinValue());
		}
		else if(this.m_IsEstandarizado)
		{
			value = (this.getMedia()+(this.getValue()*this.getDesviacionTipica()));
		}
		return value;
	}
	*/
	public String toString()
	{
		//return this.getTitulo() + ":\t" + this.getRealValue();
		return this.getTitulo() + ":\t" + this.getValue();
	}
	
}
