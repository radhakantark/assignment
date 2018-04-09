package com.assignment.jpmorgan.domain;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


import org.junit.Test;

public class InstructionTest {	

	@Test
	public void testGetTradeAmount() {
		Instruction instruction = new Instruction(new Entity("foo-sale-won-2"), 
				Instruction.TransactionType.SALE,
				0.00094,
				new Currency("WON"),
				LocalDate.parse("12 Feb 2016", DateTimeFormatter.ofPattern("dd MMM yyyy")),
				LocalDate.parse("16 Feb 2016", DateTimeFormatter.ofPattern("dd MMM yyyy")),
				50,
				234.85);
		assertEquals(0.00094 * 50 * 234.85, instruction.getTradeAmount(), 0.001);
	}

}
