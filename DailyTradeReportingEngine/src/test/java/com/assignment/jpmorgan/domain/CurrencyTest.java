package com.assignment.jpmorgan.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.assignment.jpmorgan.domain.Currency;

public class CurrencyTest {
	Currency currency;

	@Before
	public void setUp() throws Exception {
		currency = new Currency("WON");
	}

	@Test
	public void testGetName() {
		assertEquals("WON", currency.getName());
	}

	@Test
	public void testSetName() {
		currency.setName("JPY");
		assertEquals("JPY", currency.getName());
	}

}
