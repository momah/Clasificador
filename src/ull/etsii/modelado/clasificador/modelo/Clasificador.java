package ull.etsii.modelado.clasificador.modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import ull.etsii.modelado.clasificador.herramientas.QSort;

/**
 * Clase que implementa el método knn para la clasificación de muestras. Utiliza
 * un fichero csv el cual representa su base de conocimiento.
 * @author José Oliver Martínez Novo
 */
public class Clasificador 
{
	private String[] m_Cabeceras;
	private Muestra[] m_Muestras;
	private int m_Vecinos;
	private TipoProcesado m_TipoProcesado;
	private TipoPesadoAtributos m_TipoPesadoAtributos;
	private MetodoClasificacion m_MetodoClasificacion;
	private TipoDistancia m_TipoDistancia;
	private Muestra[] m_ConjuntoEntrenamiento;
	private HashMap<String, Integer> m_DataSetInfo;
	private HashMap<String, Double> m_Resultados;
			
	/**
	 * Contructor que recibe un array de argumentos y tomará el último como valor de clasificación.
	 * @param cabeceras (String[]) Array de parámetros que representan los datos de cabecera de las muestras.
	 * @param muestras (String[]) Array de parámetros que representan las muestras.
	 * @throws NumberFormatException En caso de que no se haya podido convertir algún
	 * valor de la muestra en formato decimal, o que la columna de clasificación no es la misma en todas las muestras.
	 * @throws FileNotFoundException En caso de que fileName fichero sea vacio o null.
	 * @throws IOException En caso de que se produzca un error al abrir o leer el archivo.
	 */
	public Clasificador(String fileName, TipoProcesado tipo) throws NumberFormatException, FileNotFoundException, IOException
	{
		if((fileName != null) && (fileName != ""))
		{
			this.m_TipoProcesado = tipo;
			this.m_TipoPesadoAtributos = TipoPesadoAtributos.ESTATICO;
			this.m_MetodoClasificacion = MetodoClasificacion.DEFECTO;
			String[] muestras = cargarFichero(fileName);
			if(comprobarMuestras(muestras))
			{
				//asignarDatosEstadisticos();
				setPesosAtributos();
				setPesosMuestras();
			}
			else
			{
				throw new NumberFormatException("Las columnas de la muestra no son coherentes.");
			}
		}
		else
		{
			throw new FileNotFoundException("El fichero de datos no puede ser null");
		}
	}
	
	/**
	 * @author Oliver Martínez Novo
	 * Enumeración para definir el tipo de procesado de datos
	 * 0 = NORMALIZACIÓN, 1 = ESTANDARIZACIÓN
	 */
	public enum TipoProcesado
	{
		NORMALIZACIÓN, ESTANDARIZACIÓN
	}
	
	/**
	 * @author Oliver Martínez Novo
	 * Enumración para definir el peso de los atributos.
	 * 0 = ESTATICO, 1 = DINAMICO
	 */
	public enum TipoPesadoAtributos
	{
		ESTATICO, DINAMICO
	}
	
	/**
	 * 
	 * @author Oliver Martínez Novo
	 * Enumeración para definir el metodo de clasificación
	 * 0 = DEFECTO, 1 = CERCANIA, 2 = VOTO_FIJO
	 */
	public enum MetodoClasificacion
	{
		DEFECTO, CERCANIA, VOTO_FIJO
	}
	
	/**
	 * 
	 * @author Oliver Martínez Novo
	 * Enumeración para definir el tipo de distancia
	 * 0 = EUCLIDEA, 1 = MANHATTAN, 2 = CHEBYCHEF
	 */
	public enum TipoDistancia
	{
		EUCLIDEA, MANHATTAN, CHEBYCHEF
	}
		
	/**
	 * Establece el array de objetos de la clase Muestra que contiene la clase.
	 * @param Muestra[] Array de objetos de la clase Muestra que se desea establecer.
	 */
	public void setMuestras(Muestra[] muestras) 
	{
		this.m_Muestras = muestras;
	}
	
	/**
	 * Obtiene el array de objetos de la clase Muestra que contiene la clase. 
	 * @return Muestra[] Array de objetos de la clase Muestra que contiene la clase.
	 */
	public Muestra[] getMuestras() 
	{
		return m_Muestras;
	}

	/**
	 * Establece el número de vecinos.
	 * @param m_Vecinos the m_Vecinos to set
	 */
	public void setVecinos(int m_Vecinos) 
	{
		this.m_Vecinos = m_Vecinos;
	}

	/**
	 * Obtiene el número de vecinos.
	 * @return the m_Vecinos
	 */
	public int getVecinos() 
	{
		return m_Vecinos;
	}

	/**
	 * Establece el método de clasificación: 0 = por defecto, 1 = cercanía, 2 = voto fijo.
	 * @param metodoClasificacion the m_MetodoClasificacion to set
	 */
	public void setMetodoClasificacion(MetodoClasificacion metodoClasificacion) 
	{
		this.m_MetodoClasificacion = metodoClasificacion;
	}

	/**
	 * Obtiene el método de clasificación: 0 = por defecto, 1 = cercanía, 2 = voto fijo.
	 * @return the m_MetodoClasificacion
	 */
	public MetodoClasificacion getMetodoClasificacion() 
	{
		return m_MetodoClasificacion;
	}

	/**
	 * Establece el tipo de procesado de datos: 0 = Normalización, 1 = Estandarización.
	 * @param m_TipoProcesado the m_TipoProcesado to set
	 */
	public void setTipoProcesado(TipoProcesado tipoProcesado) 
	{
		this.m_TipoProcesado = tipoProcesado;
	}

	/**
	 * Obtiene el tipo de procesado de datos: 0 = Normalización, 1 = Estandarización.
	 * @return the m_TipoProcesado
	 */
	public TipoProcesado getTipoProcesado() 
	{
		return m_TipoProcesado;
	}

	/**
	 * @param m_TipoPesadoAtributos the m_TipoPesadoAtributos to set
	 */
	public void setTipoPesadoAtributos(TipoPesadoAtributos m_TipoPesadoAtributos) 
	{
		this.m_TipoPesadoAtributos = m_TipoPesadoAtributos;
	}

	/**
	 * @return the m_TipoPesadoAtributos
	 */
	public TipoPesadoAtributos getTipoPesadoAtributos() 
	{
		return m_TipoPesadoAtributos;
	}

	/**
	 * @param m_Distancia the m_Distancia to set
	 */
	public void setTipoDistancia(TipoDistancia tipoDistancia) 
	{
		this.m_TipoDistancia = tipoDistancia;
	}

	/**
	 * @return the m_Distancia
	 */
	public TipoDistancia getTipoDistancia() 
	{
		return m_TipoDistancia;
	}

	/**
	 * Establece el conjunto de entranamiento del clasificador.
	 * @param conjuntoEntrenamiento Muestra[] Array de objetos de la clase Muestra que
	 * se va a establecer como conjunto de entrenamiento.
	 */
	public void setConjuntoEntrenamiento(Muestra[] conjuntoEntrenamiento) 
	{
		//this.m_ConjuntoEntrenamiento = new Muestra[conjuntoEntrenamiento.length];
		this.m_ConjuntoEntrenamiento = conjuntoEntrenamiento;
	}

	/**
	 * Obtiene el conjunto de entranamiento del clasificador.
	 * @return Muestra[] Array de objetos de la clase Muestra que
	 * representa el conjunto de entrenamiento cargado.
	 */
	public Muestra[] getConjuntoEntrenamiento() 
	{
		return m_ConjuntoEntrenamiento;
	}

	public HashMap<String, Integer> getDataSetInfo()
	{
		return this.m_DataSetInfo;
	}
	
	/**
	 * Establece los valores del HashMap que representan los resultados obtenidos en 
	 * el proceso de lectura del fichero de muestras y clasificación.
	 * @param HashMap<String, Integer> Resultados del proceso de clasificación
	 */
	public void setResultados(HashMap<String, Double> resultados) 
	{
		this.m_Resultados = resultados;
	}
	
	/**
	 * Obtiene los valores del HashMap que representan los resultados obtenidos en 
	 * el proceso de lectura del fichero de muestras y clasificación.
	 * @return HashMap<String, Double> Resultados del proceso de clasificación
	 */
	public HashMap<String, Double> getResultados() 
	{
		return m_Resultados;
	}
	
	/**
	 * Establece los valores de las cabeceras que representan los nombres
	 * de los atributos que son necesarios para clasificar las muestras. 
	 * @param String[] cabeceras Array de cadenas de texto que representan las cabeceras
	 */
	public void setCabeceras(String[] cabeceras) 
	{
		this.m_Cabeceras = cabeceras;
	}

	/**
	 * Obtiene los valores de las cabeceras que representan los nombres
	 * de los atributos que son necesarios para clasificar las muestras.
	 * @return String[] Array de cadenas de texto que representan las cabeceras. 
	 */
	public String[] getCabeceras() 
	{
		return m_Cabeceras;
	}
		
	/**
	 * Normaliza la columna especificada según los nuevos valores pasados como parámetros.
	 * @param col int Valor de base cero que representa el indice de la columna a normalizar.
	 * @param minValue double Valor mínimo que tomará los atributos de la columna especificada.
	 * @param maxValue double Valor máximo que tomarán los atributos de la columna especificada.
	 */
	/*
	public void normalizarColumna(int col, double minValue, double maxValue)
	{
		for(Muestra m : this.getMuestras())
		{
			Atributo a = m.getAtributos()[col];
			a.setValue(a.getRealValue());
			a.setMaxValue(maxValue);
			a.setMinValue(minValue);
			m.getAtributos()[col] = a.normalizar();
		}
	}
	*/
	
	/**
	 * Estandariza la columna especificada según los nuevos valores pasados como parámetros.
	 * @param col int Valor de base cero que representa el indice de la columna a estandarizar.
	 * @param media Doble valor que representa la media
	 * @param desviacion Double valor que representa la desviación típica.
	 */
	/*
	public void estandarizarColumna(int col, Double media, Double desviacion)
	{
		for(Muestra m : this.getMuestras())
		{
			Atributo a = m.getAtributos()[col];
			a.setValue(a.getRealValue());
			a.setMedia(media);
			a.setDesviacionTipica(desviacion);
			m.getAtributos()[col] = a.estandarizar();
		}
	}
	*/
	/**
	 * Crea un nuevo conjunto de pruebas a partir de los números de orden de las muestras.
	 * @param valores int[] Array de enteros, de base 1, que representan
	 * los números de orden de lista de muestras a seleccionar para el conjunto de pruebas.
	 * @return Boolean True si se ha podido crear el conjunto de entrenamiento satisfactoriamente, 
	 * false en caso contrario.
	 */
	public Boolean crearConjuntoEntrenamiento(int[] valores)
	{
		Integer index = null;
		Boolean flag = false;
		
		if(valores.length > 0)
		{
			this.m_ConjuntoEntrenamiento = new Muestra[valores.length];
			for(int r = 0; r < valores.length; r++)
			{
				index = valores[r];
				if((index <= this.getMuestras().length) && (index >= 1))
				{
					this.getConjuntoEntrenamiento()[r] = (Muestra)this.getMuestras()[index-1].clone();
					this.getMuestras()[index - 1] = null;					
				}
			}
			limpiarNulos(valores.length);
			flag = true;
		}
		return flag;
	}
	
	/**
	 * Función que obtiene la clasificación de una muestra.
	 * @param (Muestra) muestra Objeto de la clase Muestra que se desea clasificar. 
	 * @return (String) Cadena de texto que contiene la clasidicación de la muestra 
	 * en función del archivo de muestras que se haya cargado.
	 */
	public String knn(Muestra muestra)
	{
		this.m_Resultados = new HashMap<String, Double>();
		Muestra[] muestras = null;
		String result = null;
		int index = 0;
		
		
		
		setPesosAtributos();
		setDistanciaEuclidea(muestra);
		
		// Se calculan las distancias segun el tipo elegido
		/*
		switch(this.getTipoDistancia())
		{
			case EUCLIDEA:
				setDistanciaEuclidea(muestra);
				break;
			case MANHATTAN:
				setDistanciaManhattan(muestra);
				break;
			case CHEBYCHEF:
				setDistanciaChebychef(muestra);
				break;
		}
		*/
		// Se ordena el DataSet por distancia.		
		//this.setMuestras(new QSort(this.getMuestras()).ordenar());
		
		
		// Se establecen los pesos de las muestras según el tipo de métrica.
		//setPesosMuestras();
		
		
		// Se cuentan cuantos registros hay de cada tipo		
		// Nuevo
		//Muestra[] aux = new Muestra[this.m_Vecinos];
		ArrayList<Tipo> tipos = new ArrayList<Tipo>();
		Tipo maximo = new Tipo("", 0);
		Boolean flag;
		
		ArrayList<Muestra> cercanasList = new ArrayList<Muestra>();
		Muestra minima = new Muestra();
		minima.setDistancia(Double.MAX_VALUE);
		
		for(int j = 0; j < m_Vecinos; j++)
		{
			for(int r = 0; r < this.getMuestras().length; r++)
			{
				if(this.getMuestras()[r].getDistancia() < minima.getDistancia())
				{
					minima = this.getMuestras()[r];
					//this.getMuestras()[r].setDistancia(Double.MAX_VALUE);
				}
			}
			
			cercanasList.add((Muestra)minima.clone());
			minima.setDistancia(Double.MAX_VALUE);
		}
		
		
		
		Muestra[] cercanas = (Muestra[]) cercanasList.toArray(new Muestra[cercanasList.size()]);
		
		for(int r = 0; r < cercanas.length; r++)
		{
			flag = false;
			Muestra mu = cercanas[r];
			for(int j = 0; j < tipos.size(); j++)
			{
				if(tipos.get(j).getNombre().equals(mu.getClasificacion()))
				{
					flag = true;
					break;
				}
			}
			if(!flag)
			{
				tipos.add(new Tipo(mu.getClasificacion(), 1));
			}
			else
			{
				for(int j = 0; j < tipos.size(); j++)
				{
					if(tipos.get(j).getNombre().equals(mu.getClasificacion()))
					{
						tipos.get(j).setCantidad(tipos.get(j).getCantidad() + 1);
						break;
					}
				}
			}
		}
		
		maximo = new Tipo("", 0);
		
		for(int r = 0; r < tipos.size(); r++)
		{
			if(tipos.get(r).getCantidad() > maximo.getCantidad())
			{
				maximo = tipos.get(r);
			}
		}
		
		return maximo.getNombre();
		/*
		// Fin de nuevo
		for(int r = 0; r < this.m_Vecinos; r++)
		{
			Muestra m = this.getMuestras()[r];
			String key = m.getClasificacion();
			if(this.m_Resultados.containsKey(key))
			{
				this.m_Resultados.put(key, m_Resultados.get(key) + m.getPeso());
			}
			else
			{
				this.m_Resultados.put(key, m.getPeso());
			}
		}
		
		//System.out.println(m_Resultados.toString());
		
		//System.out.println();
		
		
		
		
		// Se comprueban los resultados y se decide la clasificación.
		Iterator<Entry<String, Double>> it = m_Resultados.entrySet().iterator();
		muestras = new Muestra[m_Resultados.size()];
		while(it.hasNext())
		{
			Entry<String, Double> par = it.next();
			Muestra m = new Muestra();
			m.setClasificacion(par.getKey());
			m.setDistancia(par.getValue());
			muestras[index] = m;
			index++;
		}
		muestras = new QSort(muestras).ordenar();
		
		for(int r = 0; r < muestras.length; r++)
		{
			System.out.println(muestras[r].getClasificacion() + 
					" = " + muestras[r].getDistancia());
		}
		
		//if(m_Resultados.size() > 1)
		if(muestras.length > 1)	
		{
			
			// Se calcula el desempate
			if(muestras[muestras.length - 1].getDistancia() == muestras[muestras.length - 2].getDistancia())
			{
				result = "0x";
			}
			else
			{
				result =  muestras[muestras.length - 1].getClasificacion();
			}
		}
		else if(muestras.length == 1)
		//else if(m_Resultados.size() == 1)
		{
			//result = it.next().getKey();
			result = muestras[0].getClasificacion();
		}
		else
		{
			result = "0x";
		}
		
		flag = false;
		if(muestra.getClasificacion().equals(result))
		{
			flag = true;
		}
		/*
		System.out.println("Real...: " + muestra.getClasificacion() + 
				" Predicción...: " + result + " - " + flag);
		*/
		/*
		System.out.println(result + " - " + flag);
		System.out.println();
		return result;
		*/
	}
	
	/**
	 * Clasifica el conjunto de entrenamiento.
	 */
	public void clasificarConjuntoEntrenamiento()
	{
		for(Muestra m : this.getConjuntoEntrenamiento())
		{
			m.setPrediccion(this.knn(m));
		}
		
		/*
		for(int r = 0; r < this.getConjuntoEntrenamiento().length; r++)
		{
			this.getConjuntoEntrenamiento()[r].setPrediccion(this.knn(this.getConjuntoEntrenamiento()[r]));
		}
		*/
	}
	
	/**
	 * Establece la distancia euclidea.
	 * @param muestra
	 */
	private void setDistanciaEuclidea(Muestra muestra)
	{
		//Double[] valores, valoresMuestra = muestra.getValues();
		Double valor = null;
		
		Double[] valoresMuestra = new Double[muestra.getAtributos().length];
		Double[] valores = new Double[valoresMuestra.length];
		
		for(int r = 0; r < muestra.getAtributos().length; r++)
		{
			valoresMuestra[r] = muestra.getAtributos()[r].getValue();
		}
		
		for(int j = 0; j < this.getMuestras().length; j++)
		{
			valor = 0D;
			for(int r = 0; r < this.getMuestras()[j].getAtributos().length; r++)
			{
				valores[r] = this.getMuestras()[j].getAtributos()[r].getValue();
			}
			
			for(int r = 0; r < valores.length; r++)
			{
				valor += Math.pow((valoresMuestra[r] - valores[r]), 2);
				//m.getAtributos()[r].getPeso() * 
			}
			this.getMuestras()[j].setDistancia(Math.sqrt(valor));
		}
		
		/*
		for (Muestra m : this.getMuestras()) 
		{
			for(int r = 0; r < m.getAtributos().length; r++)
			{
				valores[r] = m.getAtributos()[r].getValue();
			}
			
			//valores = m.getValues();
			valor = 0D;
			for(int r = 0; r < valores.length; r++)
			{
				valor += ((Math.pow((valoresMuestra[r] - valores[r]), 2)));
				//m.getAtributos()[r].getPeso() * 
			}
			m.setDistancia(Math.sqrt(valor));	
		}
		*/
	}
	
	/**
	 * Establece la distancia Manhattan.
	 * @param muestra
	 */
	private void setDistanciaManhattan(Muestra muestra)
	{
		Double[] valores, valoresMuestra = muestra.getValues();
		Double valor = null;
		
		for (Muestra m : this.getMuestras()) 
		{
			valores = m.getValues();
			valor = 0D;
			for(int r = 0; r < valores.length; r++)
			{
				valor += (m.getAtributos()[r].getPeso() * (Math.abs(valores[r] - valoresMuestra[r])));
			}
			m.setDistancia(valor);			
		}
	}
	
	/**
	 * Establece la distancia Chebychef
	 * @param muestra
	 */
	private void setDistanciaChebychef(Muestra muestra)
	{
		Double[] valores, valoresMuestra = muestra.getValues();
		Double maxValue = null, valor = null;
		for (Muestra m : this.getMuestras()) 
		{
			valores = m.getValues();
			maxValue = Double.MIN_VALUE;
			valor = 0D;
			for(int r = 0; r < valores.length; r++)
			{
				valor = Math.abs(m.getAtributos()[r].getPeso() * (m.getAtributos()[r].getValue() - valoresMuestra[r]));
				if(valor > maxValue)
				{
					maxValue = valor;
				}
			}
			m.setDistancia(valor);			
		}
	}
	
	/**
	 * Asigna pesos a los atributos en función del tipo de procesado de datos.
	 * Si el procesado es Normalización, asignará peso 1 a todos los atributos.
	 * Si el procesado es Estandarización, asignará peso equivalente al cálculo
	 * de la siguiente expresión: (1D - this.getDesviacionTipica)
	 */
	private void setPesosAtributos()
	{
		//int index;
		//Double[] pesos = new Double[]{9D,18D,4D,3D,8D,8D,7D,5D};
		for(Muestra m : this.getMuestras())
		{
			//index = 0;
			for(Atributo a : m.getAtributos())
			{
				switch (this.getTipoPesadoAtributos()) 
				{
					case ESTATICO:
						//a.setPeso(pesos[index]);
						//index++;
						a.setPeso(1D);
						break;
					case DINAMICO:
						a.setPeso(a.getDesviacionTipica()/a.getSumaDesviacionTipica());
						break;
				}
			}
		}
	}
	
	/**
	 * Asigna los pesos a las muestras del DataSet segun el tipo de clasificación
	 */
	private void setPesosMuestras()
	{
		for(int r = 0; r < this.getVecinos(); r++)
		{
			switch (this.getMetodoClasificacion()) 
			{
				case DEFECTO:
					this.getMuestras()[r].setPeso(1D);
					break;
				case CERCANIA:
					if(this.getMuestras()[r].getDistancia().equals(0D))
					{
						this.getMuestras()[r].setPeso(Double.MAX_VALUE);
					}
					else
					{
						this.getMuestras()[r].setPeso(1D/this.getMuestras()[r].getDistancia());
					}
					break;
				case VOTO_FIJO:
					this.getMuestras()[r].setPeso((double)(this.getVecinos() - r));
					break;
			}
		}
	}
		
	/**
	 * Obtiene un array de cadenas de texto que representan las filas del fichero. 
	 * @param (String) file Cadena de texto que representa la ruta del archivo.
	 * @return (String[]) Array de cadenas de texto.
	 * @throws IOException En el caso de que no se haya tenido acceso al fichero.
	 */
	private String[] cargarFichero(String file) throws IOException
	{
		ArrayList<String> listFilas = new ArrayList<String>();
		File archivo = new File(file);
	    FileReader fr = new FileReader(archivo);
	    BufferedReader br = new BufferedReader(fr);
	    String linea = null;
		while((linea = br.readLine())!= null)
		{
			listFilas.add(linea);
		}
        /*
		String[] result = listFilas.toArray(new String[listFilas.size()]);
		for(int r = 0; r < result.length; r++)
		{
			System.out.println(r + " - " + result[r]);
		}
		return result;
		*/
		return listFilas.toArray(new String[listFilas.size()]);
	}
		
	/**
	 * Comprueba que todos los valores de las muestras sean valores numéricos, 
	 * que tengan la misma cantidad de datos y que la columna identificadora de 
	 * tipo sea la misma en todas las muestras.
	 * @param muestras (String[]) Array de cadenas que representan las muestras.
	 * @return (Boolean) True en caso de que se haya podido generar el clasificador, 
	 * false en caso contrario.
	 */
	private Boolean comprobarMuestras(String[] muestras)
	{
		Boolean flag = true;
		int tam = 0, colClass = -1, index = 1, j = 0;
		String[] stringValues = null;
		this.m_DataSetInfo = new HashMap<String, Integer>();
		
		if((muestras != null) && (muestras.length > 1))
		{
			stringValues = muestras[0].split(",");
			this.m_Muestras = new Muestra[muestras.length-1];
			this.m_Cabeceras = new String[stringValues.length];
			this.m_Cabeceras = stringValues;
			tam = this.m_Cabeceras.length;
			
			// Comprobar si la columna de tipo es la misma en todos 
			// los registros y construir el DataSet.
			while((flag) && (index < muestras.length))
			{
				stringValues = muestras[index].split(",");
				j = 0;
				while((flag) && (j < tam))
				{
					try
					{
						Double.parseDouble(stringValues[j]);
					}
					catch(NumberFormatException e)
					{
						if(this.m_DataSetInfo.containsKey(stringValues[j]))
						{
							int valor = m_DataSetInfo.get(stringValues[j]);
							valor++;
							m_DataSetInfo.put(stringValues[j], valor);
						}
						else
						{
							this.m_DataSetInfo.put(stringValues[j], 1);
						}
						if(colClass == -1)
						{
							colClass = j;
						}
						if(colClass != j)
						{
							flag = false;
						}
					}
					j++;
				}
				if(flag)
				{
					this.m_Muestras[index-1] = new Muestra(muestras[index], colClass);
				}
				index++;
			}
		}
		else
		{
			flag = false;
		}
		
		
		
		index = 0;
		for(Muestra m : this.getMuestras())
		{
			System.out.print(index + " - "); 
			for(Atributo a : m.getAtributos())
			{
				System.out.print(a.getValue() + ", ");
			}
			System.out.println(m.getClasificacion());
			index++;
		}
		
		
		
		return flag;
	}

	/**
	 * Calcula datos estadisticos tales como valores maximos y minimos,
	 * medias y desviaciones tipicas de los atributos. Se normalizan o
	 * estandarizan los valores de los atributos segun el tipo de procesado.
	 */
	private void asignarDatosEstadisticos()
	{
		Double minValue = null, maxValue = null, sumValues, totalValues, desviacion;
		Atributo atr = null;
		
		// Se recorre el DataSet por columnas para asignar minValue, maxValue, 
		// titulo de cabecera y normalizar todas las muestras.
		for(int r = 0; r < this.m_Cabeceras.length - 1; r++)
		{
			minValue = Double.MAX_VALUE;
			maxValue = Double.MIN_VALUE;
			sumValues = 0D;
			for(Muestra m : this.getMuestras())
			{
				sumValues += m.getAtributos()[r].getValue();
				if(m.getAtributos()[r].getValue() > maxValue)
				{
					maxValue = m.getAtributos()[r].getValue();
				}
			}
			for(Muestra m : this.getMuestras())
			{
				if(m.getAtributos()[r].getValue() < minValue)
				{
					minValue = m.getAtributos()[r].getValue();
				}
			}
			
			// Se asignan minValue, maxValue, titulo y media a todas los atributos
			// de todas las muestras por columnas.
			for(Muestra m : this.getMuestras())
			{
				atr = m.getAtributos()[r];
				atr.setTitulo(this.m_Cabeceras[r]);
				atr.setMinValue(minValue);
				atr.setMaxValue(maxValue);
				atr.setMedia(sumValues/this.getMuestras().length);
			}
		}
		
		// Se calcula la desviación típica
		for(int r = 0; r < this.m_Cabeceras.length - 1; r++)
		{
			sumValues = 0D;
			totalValues = 0D;
			desviacion = 0D;
			for(Muestra m : this.getMuestras())
			{
				sumValues += (Math.pow(m.getAtributos()[r].getValue() - m.getAtributos()[r].getMedia(), 2));
			}
			desviacion = Math.sqrt(sumValues);
			totalValues += desviacion;
			for(Muestra m : this.getMuestras())
			{
				atr = m.getAtributos()[r];
				atr.setDesviacionTipica(desviacion);
				atr.setSumaDesviacionTipica(totalValues);
			}
		}
		
		/*
		// Calcular valores maximos y minimos de las desviaciones tipicas para normalizarlas.
		minValue = Double.MAX_VALUE;
		maxValue = Double.MIN_VALUE;
		for(Atributo a : this.getMuestras()[0].getAtributos())
		{
			if(a.getDesviacionTipica() > maxValue)
			{
				maxValue = a.getDesviacionTipica();
			}
		}
		for(Atributo a : this.getMuestras()[0].getAtributos())
		{
			if(a.getDesviacionTipica() < minValue)
			{
				minValue = a.getDesviacionTipica();
			}
		}		
		*/
		
		// Se normalizan o estandarizan los valores de los atributos y se normaliza
		// la desviacion tipica de los mismos.
		for(Muestra m : this.getMuestras())
		{
			for(Atributo a : m.getAtributos())
			{
				if(this.getTipoProcesado().equals(TipoProcesado.NORMALIZACIÓN))
				{
					//a.normalizar();
				}
				else if(this.getTipoProcesado().equals(TipoProcesado.ESTANDARIZACIÓN))
				{
					//a.estandarizar();
				}
				//a.normalizarDesviacionTipica(minValue, maxValue);	
			}
		}
	}
	
	/**
	 * Elimina las muestras que quedaron con valor null después de la ejecución
	 * del proceso de creación del conjunto de entrenamiento.
	 * @param cantidad int Valor que indica la cantidad de muestras a eliminar.
	 */
	private void limpiarNulos(int cantidad)
	{
		int ta = this.getMuestras().length;
		
		Muestra[] aux = new Muestra[ta - cantidad];
		int index = 0;
		
		for(int r = 0; r < this.getMuestras().length; r++)
		{
			if(this.getMuestras()[r] != null)
			{
				aux[index] = (Muestra)this.getMuestras()[r].clone();
			}
			else
			{
				index--;
			}
			index++;
		}
		this.m_Muestras = new Muestra[aux.length];
		this.setMuestras(aux);
	}
	
	public String toString()
	{
		String result = "";
		result += "* Resumen del contenido del DataSet.\n";
		result += "\t" + this.m_DataSetInfo.toString() + "\n\n";
		result += "* Datos estadísticos obtenidos para cada atributo de las muestras.\n";
		for(int r = 0; r < m_Cabeceras.length - 1; r++)
		{
			result += "\t" + this.getMuestras()[0].getAtributos()[r].getTitulo() + 
				"\tminVal: " + this.getMuestras()[0].getAtributos()[r].getMinValue() +
				"\tmaxVal: " + this.getMuestras()[0].getAtributos()[r].getMaxValue() + 
				"\tMedia: " +  this.getMuestras()[0].getAtributos()[r].getMedia() + 
				"\tDesv. típica normalizada: " +  this.getMuestras()[0].getAtributos()[r].getDesviacionTipica() + "\n";
		}
		return result;
	}
	
}
