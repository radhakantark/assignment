package com.assignment.jpmorgan.domain;

import java.time.LocalDate;

public class Instruction {
	public enum TransactionType {BUY, SALE};
	
	private Entity entity;	
	TransactionType transactionType;
	private double agreedFx;
	private Currency currency;
	private LocalDate instructionDate;
	private LocalDate settlementDate;
	private int units;
	private double pricePerUnit;
	private double tradeAmount;
	
	
	public Instruction() {		
	}	
	

	public Instruction(Entity entity, 
			TransactionType transactionType, 
			double agreedFx, 
			Currency currency,
			LocalDate instructionDate, 
			LocalDate settlementDate, 
			int units, 
			double pricePerUnit) {		
		this.entity = entity;
		this.transactionType = transactionType;
		this.agreedFx = agreedFx;
		this.currency = currency;
		this.instructionDate = instructionDate;
		this.settlementDate = settlementDate;
		this.units = units;
		this.pricePerUnit = pricePerUnit;
		this.tradeAmount = pricePerUnit * units * agreedFx;
	}



	public Entity getEntity() {
		return entity;
	}
	

	public void setEntity(Entity entity) {
		this.entity = entity;
	}	
	

	public TransactionType getTransactionType() {
		return transactionType;
	}
	

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}
	

	public double getAgreedFx() {
		return agreedFx;
	}
	

	public void setAgreedFx(double agreedFx) {
		this.agreedFx = agreedFx;
	}
	

	public Currency getCurrency() {
		return currency;
	}
	

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	

	public LocalDate getInstructionDate() {
		return instructionDate;
	}
	

	public void setInstructionDate(LocalDate instructionDate) {
		this.instructionDate = instructionDate;
	}
	

	public LocalDate getSettlementDate() {
		return settlementDate;
	}
	

	public void setSettlementDate(LocalDate settlementDate) {
		this.settlementDate = settlementDate;
	}
	

	public int getUnits() {
		return units;
	}
	

	public void setUnits(int units) {
		this.units = units;
	}
	

	public double getPricePerUnit() {
		return pricePerUnit;
	}
	

	public void setPricePerUnit(double pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

	
	public double getTradeAmount() {
		return tradeAmount;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(agreedFx);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + ((entity == null) ? 0 : entity.hashCode());
		result = prime * result + ((instructionDate == null) ? 0 : instructionDate.hashCode());
		temp = Double.doubleToLongBits(pricePerUnit);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((settlementDate == null) ? 0 : settlementDate.hashCode());
		result = prime * result + ((transactionType == null) ? 0 : transactionType.hashCode());
		result = prime * result + units;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Instruction other = (Instruction) obj;
		if (Double.doubleToLongBits(agreedFx) != Double.doubleToLongBits(other.agreedFx))
			return false;
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency))
			return false;
		if (entity == null) {
			if (other.entity != null)
				return false;
		} else if (!entity.equals(other.entity))
			return false;
		if (instructionDate == null) {
			if (other.instructionDate != null)
				return false;
		} else if (!instructionDate.equals(other.instructionDate))
			return false;
		if (Double.doubleToLongBits(pricePerUnit) != Double.doubleToLongBits(other.pricePerUnit))
			return false;
		if (settlementDate == null) {
			if (other.settlementDate != null)
				return false;
		} else if (!settlementDate.equals(other.settlementDate))
			return false;
		if (transactionType != other.transactionType)
			return false;
		if (units != other.units)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Instruction [entity=" + entity + ", transactionType=" + transactionType + ", agreedFx=" + agreedFx
				+ ", currency=" + currency + ", instructionDate=" + instructionDate + ", settlementDate="
				+ settlementDate + ", units=" + units + ", pricePerUnit=" + pricePerUnit + "]";
	}	
	
}
