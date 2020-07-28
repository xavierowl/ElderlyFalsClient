package com.example.mashicommits.models;

import androidx.annotation.NonNull;

import com.example.mashicommits.enums.TipoCredito;

import java.io.Serializable;
import java.util.List;

/**
 * Esta clase permite guardar datos 
 * referentes a un credito.
 **/
public class Credito implements Serializable {
	private int id;

	private double monto;

	private double saldo;

	private TipoCredito tipo;

	private String fechaVencimiento;

	private List<Cuota> listaCuotas;
	
	/**
	 * Crea un nueva instancia de la clase Credito.
	 */
	public Credito() {
	}

	/**
	 * Devuelve el valor del ID.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Establece el valor del ID.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Devuelve el valor del monto.
	 */
	public double getMonto() {
		return monto;
	}


	/**
	 * Establece el valor del monto.
	 */
	public void setMonto(double monto) {
		this.monto = monto;
	}

	/**
	 * Devuelve el valor del saldo.
	 */
	public double getSaldo() {
		return saldo;
	}
	
	/**
	 * Establece el valor del saldo.
	 */
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	
	/**
	 * Devuelve el valor del tipo de crédito.
	 */
	public TipoCredito getTipo() {
		return tipo;
	}
	
	/**
	 * Establece el valor del tipo de crédito.
	 */
	public void setTipo(TipoCredito tipo) {
		this.tipo = tipo;
	}
	
	/**
	 * Devuelve el valor de la fecha de vencimiento.
	 */
	public String getFechaVencimiento() {
		return fechaVencimiento;
	}

	/**
	 * Establece el valor de la fecha de vencimiento.
	 */
	public void setFechaVencimiento(String fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	/**
	 * Devuelve la lista de cuotas asociadas.
	 */
	public List<Cuota> getListaCuotas() {
		return listaCuotas;
	}

	/**
	 * Establece la lista de cuotas asociadas.
	 */
	public void setListaCuotas(List<Cuota> listaCuotas) {
		this.listaCuotas = listaCuotas;
	}
}