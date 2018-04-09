package com.assignment.jpmorgan.config;

import java.util.HashSet;
import java.util.Set;

import com.assignment.jpmorgan.domain.Currency;

public class Configuration {
	
	public Set<Currency> getMonToFridTradingCurrency() {
		Set<Currency> currencies = new HashSet<>();
		currencies.add(new Currency("SGP"));
		currencies.add(new Currency("INR"));	
		currencies.add(new Currency("WON"));
		currencies.add(new Currency("JPY"));
		return currencies;
	}
	
	public Set<Currency> getSunToThursTradingCurrency() {
		Set<Currency> currencies = new HashSet<>();
		currencies.add(new Currency("AED"));
		currencies.add(new Currency("SAR"));		
		currencies.add(new Currency("BHD"));
		currencies.add(new Currency("EGP"));
		return currencies;
	}
}
