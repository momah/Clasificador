/**
 * 
 */
package ull.etsii.modelado.clasificador.herramientas;

import java.util.HashMap;
import java.util.Iterator;

import ull.etsii.modelado.clasificador.modelo.Muestra;


/**
 * @author Oliver Martínez Novo
 * Matriz de confusión para la representacion de datos estadisticos
 */
public class MatrizConfusion 
{
	private Muestra[] m_Muestras;
	private int[][] m_Valores;
	private String[] m_Leyendas;
	private HashMap<String, Integer> m_DataSetInfo;
	private int m_Aciertos = 0;
	private int m_Empates = 0;
	private int m_Fallos = 0;
	
	/**
	 * Constructor de la clase
	 * @param muestras Muestra[]
	 * @param dataSetInfo HashMap<String, Integer>
	 */
	public MatrizConfusion(Muestra[] muestras, HashMap<String, Integer> dataSetInfo)
	{
		this.m_Muestras = muestras;
		this.m_DataSetInfo = dataSetInfo;
		m_Valores = new int[dataSetInfo.size()+1][dataSetInfo.size()+1];
		m_Leyendas = new String[m_DataSetInfo.size()];
		Iterator<String> it = m_DataSetInfo.keySet().iterator();
		
		for(int f = 0; f < m_Valores[0].length; f++)
		{
			for(int c = 0; c < m_Valores.length; c++)
			{
				m_Valores[f][c] = 0;
			}
		}
		for(int r = 0; r < m_Valores.length; r++)
		{
			m_Valores[r][0] = r;
			m_Valores[0][r] = r;
		}
		
		int index = 0;
		while(it.hasNext())
		{
			String valor = it.next();
			m_Leyendas[index] = valor;
			index++;
		}
	}

	/**
	 * Establece el array de muestras de la clase.
	 * @param m_Muestras the m_Muestras to set
	 */
	public void setMuestras(Muestra[] m_Muestras) 
	{
		this.m_Muestras = m_Muestras;
	}

	/**
	 * Obtiene el array de muestras de la clase.
	 * @return the m_Muestras
	 */
	public Muestra[] getMuestras() 
	{
		return m_Muestras;
	}
	
	/**
	 * Obtiene el array de leyendas de la matriz
	 * @return
	 */
	public String[] getLeyendas()
	{
		return m_Leyendas;
	}
	
	
	/**
	 * Establece el numero de aciertos
	 * @param m_Aciertos the m_Aciertos to set
	 */
	public void setAciertos(int m_Aciertos) {
		this.m_Aciertos = m_Aciertos;
	}

	/**
	 * Obtiene el numero de aciertos
	 * @return the m_Aciertos
	 */
	public int getAciertos() {
		return m_Aciertos;
	}

	/**
	 * Establece el numero de empates
	 * @param m_Empates the m_Empates to set
	 */
	public void setEmpates(int m_Empates) {
		this.m_Empates = m_Empates;
	}

	/**
	 * Obtiene el nuemero de empates.
	 * @return the m_Empates
	 */
	public int getEmpates() {
		return m_Empates;
	}

	/**
	 * Establece el mumero de fallos.
	 * @param m_Fallos the m_Fallos to set
	 */
	public void setFallos(int m_Fallos) {
		this.m_Fallos = m_Fallos;
	}

	/**
	 * Obtiene el numero de fallos.
	 * @return the m_Fallos
	 */
	public int getFallos() {
		return m_Fallos;
	}
	
	/**
	 * Obtiene una matriz de enteros que representa la matriz de confusión siendo la 
	 * primera fila la leyenda para la clasificación real y la primera columna la
	 * leyenda para las predicciones.
	 * @return matriz de confusión
	 */
	public int[][] getDatos()
	{
		int tam = m_Muestras.length;
		String predictValue = null, realValue = null;;
		
		for(int r = 0; r < tam; r++)
		{
			predictValue = m_Muestras[r].getPrediccion();
			realValue = m_Muestras[r].getClasificacion();
			if(!predictValue.equals("0x"))
			{
				if(m_Muestras[r].isCoincidente())
				{
					this.m_Aciertos++;
					for(int j = 0; j < m_Leyendas.length; j++)
					{
						if(m_Leyendas[j].equals(predictValue))
						{
							m_Valores[j+1][j+1] = m_Valores[j+1][j+1]+1;
							break;
						}
					}
				}
				else
				{
					this.m_Fallos++;
					for(int j = 0; j < m_Leyendas.length; j++)
					{
						if(m_Leyendas[j].equals(predictValue))
						{
							for(int c = 0; c < m_Leyendas.length; c++)
							{
								if(m_Leyendas[c].equals(realValue))
								{
									m_Valores[j+1][c+1] = m_Valores[j+1][c+1]+1;
									break;
								}
							}							
						}
					}
				}
			}
			else
			{
				this.m_Empates++;
			}
		}
		return this.m_Valores;
		
	}
	
}
