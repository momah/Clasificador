package ull.etsii.modelado.clasificador.modelo;

/**
 * Clase utilizada para generar muestras con todos sus atributos que posteriormente
 * usara la clase Clasificator para realizar clasificaciones.
 * @author José Oliver Martínez Novo
 */
public class Muestra 
{
	private String m_Clasificacion;
	private String m_Prediccion;
	private Boolean m_Coincidente;
	private Atributo[] m_Atributos;
	private Double m_Distancia;
	private Double m_Peso;
	
	/**
	 * Constructor por defecto de la clase Muestra, crea una instancia de la clase sin datos.
	 */
	public Muestra()
	{
		m_Atributos = new Atributo[]{};
		m_Distancia = 0D;
		m_Clasificacion = null;
	}
	
	/**
	 * Contructor de la clase Muestra que crea una instancia de dicha clase a partir de una cadena de texto.
	 * @param fila (String) Cadena de texto formada de valores separados por comas que
	 * representan una muestra.
	 * @param posClase (int) Valor numérico que representa la posición de la columna de clasificación
	 * @throws NumberFormatException En caso de que no se haya podido convertir algún
	 * valor de la muestra en formato decimal.
	 */
	public Muestra(String fila, int posClase) throws NumberFormatException
	{
		parseMuestra(fila, posClase);
	}
	
	/**
	 * Establece el valor de clasificación de la muestra.
	 * @param clasificacion (String) Cadena que representa la clasificación de la muestra.
	 */
	public void setClasificacion(String clasificacion) 
	{
		this.m_Clasificacion = clasificacion;
	}
	
	/**
	 * Obtiene el valor de clasificación del objeto según los valores de la muestra.
	 * @return (String) Cadena que representa la clasificación del objeto.
	 */
	public String getClasificacion() 
	{
		return m_Clasificacion;
	}

	/**
	 * Establece la predicción para la muestra según el algoritmo knn.
	 * @param prediccion String Cadena que contiene el valor que se va a establecer.
	 */
	public void setPrediccion(String prediccion) 
	{
		this.m_Prediccion = prediccion;
		if(this.m_Clasificacion.equals(prediccion))
		{
			m_Coincidente = true;
		}
		else
		{
			m_Coincidente = false;
		}
	}

	/**
	 * Obtiene el resultado de la predicción para la muestra según el algoritmo knn.
	 * @return String Cadena que contiene el valor del resultado de la muestra.
	 */
	public String getPrediccion() 
	{
		return m_Prediccion;
	}

	/**
	 * Obtiene un valor que indica si la predicción coincide con la clasificación real de la muestra.
	 * @return Boolean True en caso de que la predicción coincida con el la clasificación real de la muestra, 
	 * false en caso contrario.
	 */
	public Boolean isCoincidente() 
	{
		return m_Coincidente;
	}

	/**
	 * Establece el array de atributos que contiene la clase.
	 * @param Atributo[] values array de atributos que se desa establecer.
	 */
	public void setAtributos(Atributo[] values) 
	{
		this.m_Atributos = values;
	}

	/**
	 * Obtiene el array de atributos que contiene la clase.
	 * @return (Atributo[]) Array de atributos que contiene la clase.
	 */
	public Atributo[] getAtributos() 
	{
		return m_Atributos;
	}
	
	/**
	 * Obtiene el conjunto de valores que tienen los atributos de la muestra.
	 * @return (Double[]) Array de elementos que representan los valores de los atributos de la muestra.
	 */
	public Double[] getValues()
	{
		Double[] values = new Double[this.m_Atributos.length];
		for(int r = 0; r < this.m_Atributos.length; r++)
		{
			values[r] = m_Atributos[r].getValue();
		}
		return values;
	}
	
	/**
	 * Establece el valor de la distancia de la muestra.
	 * @param (Double) distancia El valor de la distancia que se desa establecer.
	 */
	public void setDistancia(Double distancia) 
	{
		this.m_Distancia = distancia;
	}

	/**
	 * Obtiene un valor que representa la distancia de la muestra.
	 * @return (Double) El valor de la distancia
	 */
	public Double getDistancia() 
	{
		return m_Distancia;
	}
	
	/**
	 * Establece el peso de la muestra.
	 * @param m_Peso the m_Peso to set
	 */
	public void setPeso(Double m_Peso)
	{
		this.m_Peso = m_Peso;
	}

	/**
	 * Obtiene el peso de la muestra
	 * @return the m_Peso
	 */
	public Double getPeso() 
	{
		return m_Peso;
	}

	/**
	 * Obtiene un valor que indica si la distancia de instancia actual es mayor que la distancia de obj.
	 * @param (Muestra) obj Instancia de la clase Muestra que se desea comparar.
	 * @return (Boolean) True si la distancia de la instancia actual es mayor que 
	 * la distancia de obj.
	 */
	public Boolean isMayor(Muestra obj)
	{
		return this.getDistancia() > obj.getDistancia();
	}
	
	/**
	 * Obtiene un valor que indica si la distancia de instancia actual es menor que la distancia de obj.
	 * @param (Muestra) obj Instancia de la clase Muestra que se desea comparar.
	 * @return (Boolean) True si la distancia de la instancia actual es menor que 
	 * la distancia de obj.
	 */
	public Boolean isMenor(Muestra obj)
	{
		return this.getDistancia() < obj.getDistancia();
	}
	
	/**
	 * Obtiene un valor que indica si la distancia de instancia actual es menor o igual que obj.
	 * @param (Muestra) obj Instancia de la clase Muestra que se desea comparar.
	 * @return (Boolean) True si la distancia de la instancia actual es menor o igual que 
	 * la distancia de obj.
	 */
	public Boolean isMenorOrIgual(Muestra obj)
	{
		return this.getDistancia() <= obj.getDistancia();
	}
	
	public Object clone()
	{
		Muestra result = new Muestra();
		result.setAtributos(m_Atributos);
		result.setClasificacion(m_Clasificacion);
		result.setDistancia(m_Distancia);
		result.setPeso(m_Peso);
		result.setPrediccion(m_Prediccion);
		return result;
	}
	
	/**
	 * @return (Boolean) Retorna true si, y sólo si todos los valores de todos sus
	 * atributos son iguales.
	 */
	public boolean equals(Object muestra)
	{
		boolean flag = false;
		Muestra mAux;
		int tam = 0, index = -1;
		if(muestra instanceof Muestra)
		{
			mAux = (Muestra)muestra;
			if(this.getAtributos().length == mAux.getAtributos().length)
			{
				tam = this.getAtributos().length;
				while((!flag) && (index < tam))
				{
					index++;
					if(this.getAtributos()[index].getValue() != mAux.getAtributos()[index].getValue())
					{
						flag = true;
					}
				}
				flag = !flag;
			}
		}
		return flag;
	}
	
	public String toString()
	{
		return "Tipo...: " + this.getClasificacion() + "             \tPredicción...: " + this.getPrediccion();
	}
	
	/**
	 * Convierte una cadena de valores separados por comas en los valores y la clasificación de la muestra.
	 * @param values (String) Cadena separada por comas que representa los valores de la
	 * muestra y su clasificación
	 * @param pos (int) Indice de base 0 que indica la posición de la columna que
	 * contiene el valor del tipo de la muestra.
	 * @throws NumberFormatException En caso de que no se haya podido convertir algún
	 * valor de la muestra en formato decimal.
	 */
	private void parseMuestra(String values, int pos) throws NumberFormatException
	{
		String[] aux = values.split(",");
		this.setClasificacion(aux[pos]);
		this.setAtributos(new Atributo[aux.length-1]);
		for(int r = 0; r < aux.length - 1; r++)
		{
			this.m_Atributos[r] = new Atributo(Double.parseDouble(aux[r]));
		}
	}
}
