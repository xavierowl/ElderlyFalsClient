package com.example.mashicommits.models;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Esta clase permite guardar datos 
 * referentes a una Cuota.
 */
public class Cuota implements Serializable {
	private int id;

	private double monto;

	private double saldo;

	private LocalDate fechaVencimiento;

	private EstadoCuota estado;
	
	/**
	 * Crea una nueva instancia de la clase Cuota.
	 */
	public Cuota() {
		estado = EstadoCuota.PENDIENTE;
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
	 * Devuelve el valor del monto.
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
	 * Devuelve el valor de la fecha de vencimiento.
	 */
	public LocalDate getFechaVencimiento() {
		return fechaVencimiento;
	}

	/**
	 * Establece el valor de la fecha de vencimiento.
	 */
	public void setFechaVencimiento(LocalDate fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	/**
	 * Devuelve el valor del estado (PENDIENTE, PAGADA, VENCIDA).
	 */
	public EstadoCuota getEstado() {
		return estado;
	}

	/**
	 * Establece el valor del estado (PENDIENTE, PAGADA, VENCIDA).
	 */
	public void setEstado(EstadoCuota estado) {
		/*
		if (this.fechaVencimiento.isAfter(LocalDate.now()) && saldo < monto) {
			this.estado = EstadoCuota.VENCIDA;
		} else {
			this.estado = estado;
		}
		*/
	}
	
	/**
	 * Abona el monto especificado a la cuota.
	 */
	public void abonar(double monto) {
		saldo += monto;
		if(saldo == monto) {
			estado = EstadoCuota.PAGADA;
		}
	}
}