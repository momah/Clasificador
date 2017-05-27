/**
 * 
 */
package ull.etsii.modelado.clasificador.herramientas;

import java.util.Random;

/**
 * @author Oliver Martínez Novo.
 *
 */
@SuppressWarnings("serial")
public class Aleatorio extends Random
{
	private int m_MinValue;
	private int m_MaxValue;
	private int[] m_NumerosAleatorios;
	
	/**
	 * Constructor por defecto.
	 */
	public Aleatorio()
	{
		this(0, 0);
	}
	
	/**
	 * Genera una secuencia de numeros aleatorios comprendidos entre dos valores.
	 * @param minValue valor minimo
	 * @param maxValue valor maximo
	 */
	public Aleatorio(int minValue, int maxValue)
	{
		this(minValue, maxValue, null);
	}
	
	/**
	 * Genera una secuencia de numeros aleatorios segun una semilla y comprendidos entre dos valores.
	 * @param minValue valor minimo
	 * @param maxValue valor maximo
	 * @param semilla valor de la semilla
	 */
	public Aleatorio(int minValue, int maxValue, Long semilla)
	{
		super();
		if(semilla != null)
		{
			this.setSeed(semilla);
		}
		this.setMinValue(minValue);
		this.setMaxValue(maxValue);
	}

	/**
	 * Establece el valor minimo
	 * @param m_MinValue the m_MinValue to set
	 */
	public void setMinValue(int m_MinValue)
	{
		this.m_MinValue = m_MinValue;
	}

	/**
	 * Obtiene el valor minimo
	 * @return the m_MinValue
	 */
	public int getMinValue() 
	{
		return m_MinValue;
	}

	/**
	 * Establece el valor maximo
	 * @param m_MaxValue the m_MaxValue to set
	 */
	public void setMaxValue(int m_MaxValue) 
	{
		this.m_MaxValue = m_MaxValue;
	}

	/**
	 * Obtiene el valor maximo
	 * @return the m_MaxValue
	 */
	public int getMaxValue() 
	{
		return m_MaxValue;
	}
	
	/**
	 * Obtiene los numeros aleatorios que generó en la llamada
	 * a la función generarAleatorios(int cantidad)
	 * @return int[]
	 */
	public int[] getNumerosGenerados()
	{
		return this.m_NumerosAleatorios;
	}
	
	/**
	 * Genera una cantidad especifica de numeros aleatorios sin repetición
	 * @param cantidad Cantidad de numeros aleatorios a generar
	 * @return Array de enteros que representa la coleccion de numeros aleatorios generados.
	 */
	public int[] generarAleatorios(int cantidad)
	{	
		this.m_NumerosAleatorios = new int[cantidad];
		int value = 0;
		int index = 0;
		Boolean flag;
		
		while(index < cantidad)
		{
			flag = true;
			value = this.nextInt(this.getMaxValue() + 1);
			for(int j = 0; j < cantidad; j++)
			{
				if(this.m_NumerosAleatorios[j] == value)
				{
					index--;
					flag = false;
					break;
				}
			}
			if(flag)
			{
				m_NumerosAleatorios[index] = value;
			}
			index++;
		}
		return this.m_NumerosAleatorios;
	}
	
}
