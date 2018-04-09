package com.assignment.jpmorgan.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.assignment.jpmorgan.domain.Currency;
import com.assignment.jpmorgan.domain.Entity;
import com.assignment.jpmorgan.domain.Instruction;

public class InstructionsServiceImpl implements InstructionsService {

	@Override
	public List<Instruction> getInstructions() {
		List<Instruction> instructions = new ArrayList<>();
		
		
		List<Instruction> monToFriInstructions = Arrays.asList(
				new Instruction(new Entity("foo-buy-sgp-1"), 
						Instruction.TransactionType.BUY,
						0.5,
						new Currency("SGP"),
						LocalDate.parse("01 Jan 2016", DateTimeFormatter.ofPattern("dd MMM yyyy")),
						LocalDate.parse("07 Jan 2016", DateTimeFormatter.ofPattern("dd MMM yyyy")),
						100,
						100.25),
				new Instruction(new Entity("foo-buy-sgp-2"), 
						Instruction.TransactionType.BUY,
						0.5,
						new Currency("SGP"),
						LocalDate.parse("01 Jan 2016", DateTimeFormatter.ofPattern("dd MMM yyyy")),
						LocalDate.parse("02 Jan 2016", DateTimeFormatter.ofPattern("dd MMM yyyy")),
						200,
						75.5),
				new Instruction(new Entity("foo-buy-inr-1"), 
						Instruction.TransactionType.BUY,
						0.015,
						new Currency("INR"),
						LocalDate.parse("04 Jan 2016", DateTimeFormatter.ofPattern("dd MMM yyyy")),
						LocalDate.parse("05 Jan 2016", DateTimeFormatter.ofPattern("dd MMM yyyy")),
						1000,
						1000.75),
				new Instruction(new Entity("foo-buy-inr-2"), 
						Instruction.TransactionType.BUY,
						0.015,
						new Currency("INR"),
						LocalDate.parse("04 Jan 2016", DateTimeFormatter.ofPattern("dd MMM yyyy")),
						LocalDate.parse("05 Jan 2016", DateTimeFormatter.ofPattern("dd MMM yyyy")),
						500,
						8937.85),
				new Instruction(new Entity("foo-sale-won-1"), 
						Instruction.TransactionType.SALE,
						0.00094,
						new Currency("WON"),
						LocalDate.parse("12 Feb 2016", DateTimeFormatter.ofPattern("dd MMM yyyy")),
						LocalDate.parse("14 Feb 2016", DateTimeFormatter.ofPattern("dd MMM yyyy")),
						50,
						1256.65),
				new Instruction(new Entity("foo-sale-won-4"), 
						Instruction.TransactionType.SALE,
						0.00094,
						new Currency("WON"),
						LocalDate.parse("12 Feb 2016", DateTimeFormatter.ofPattern("dd MMM yyyy")),
						LocalDate.parse("16 Feb 2016", DateTimeFormatter.ofPattern("dd MMM yyyy")),
						1000,
						234.85),
				new Instruction(new Entity("foo-sale-won-2"), 
						Instruction.TransactionType.SALE,
						0.00094,
						new Currency("WON"),
						LocalDate.parse("12 Feb 2016", DateTimeFormatter.ofPattern("dd MMM yyyy")),
						LocalDate.parse("16 Feb 2016", DateTimeFormatter.ofPattern("dd MMM yyyy")),
						50,
						234.85),
				new Instruction(new Entity("foo-sale-won-3"), 
						Instruction.TransactionType.SALE,
						0.00094,
						new Currency("WON"),
						LocalDate.parse("15 Feb 2016", DateTimeFormatter.ofPattern("dd MMM yyyy")),
						LocalDate.parse("16 Feb 2016", DateTimeFormatter.ofPattern("dd MMM yyyy")),
						10,
						5892.55)
				);
		
		
		List<Instruction> sunToThurInstructions = Arrays.asList(
				new Instruction(new Entity("e2-sale-dinar-1"), 
						Instruction.TransactionType.SALE,
						2.65,
						new Currency("BHD"),
						LocalDate.parse("22 Jan 2016", DateTimeFormatter.ofPattern("dd MMM yyyy")),
						LocalDate.parse("15 Feb 2016", DateTimeFormatter.ofPattern("dd MMM yyyy")),
						100,
						1563.85),
				new Instruction(new Entity("e2-sale-aed-1"), 
						Instruction.TransactionType.SALE,
						0.22,
						new Currency("AED"),
						LocalDate.parse("03 Jan 2016", DateTimeFormatter.ofPattern("dd MMM yyyy")),
						LocalDate.parse("04 Jan 2016", DateTimeFormatter.ofPattern("dd MMM yyyy")),
						500,
						890.55),
				new Instruction(new Entity("e2-sale-aed-2"), 
						Instruction.TransactionType.SALE,
						0.22,
						new Currency("AED"),
						LocalDate.parse("03 Jan 2016", DateTimeFormatter.ofPattern("dd MMM yyyy")),
						LocalDate.parse("04 Jan 2016", DateTimeFormatter.ofPattern("dd MMM yyyy")),
						1,
						890.55),
				new Instruction(new Entity("bar-sale-1"), 
						Instruction.TransactionType.SALE,
						0.22,
						new Currency("AED"),
						LocalDate.parse("03 Jan 2016", DateTimeFormatter.ofPattern("dd MMM yyyy")),
						LocalDate.parse("04 Jan 2016", DateTimeFormatter.ofPattern("dd MMM yyyy")),
						340,
						110.5),
				new Instruction(new Entity("e2-sale-dinar-2"), 
						Instruction.TransactionType.SALE,
						2.65,
						new Currency("BHD"),
						LocalDate.parse("22 Jan 2016", DateTimeFormatter.ofPattern("dd MMM yyyy")),
						LocalDate.parse("23 Jan 2016", DateTimeFormatter.ofPattern("dd MMM yyyy")),
						50,
						1563.85),
				new Instruction(new Entity("e2-buy-sgp-1"), 
						Instruction.TransactionType.BUY,
						0.27,
						new Currency("SAR"),
						LocalDate.parse("05 Jan 2016", DateTimeFormatter.ofPattern("dd MMM yyyy")),
						LocalDate.parse("07 Jan 2016", DateTimeFormatter.ofPattern("dd MMM yyyy")),
						400,
						2000.95),
				new Instruction(new Entity("e2-buy-egypt-pound-2"), 
						Instruction.TransactionType.BUY,
						0.057,
						new Currency("EGP"),
						LocalDate.parse("05 Jan 2016", DateTimeFormatter.ofPattern("dd MMM yyyy")),
						LocalDate.parse("07 Jan 2016", DateTimeFormatter.ofPattern("dd MMM yyyy")),
						2000,
						2274.35),
				new Instruction(new Entity("e2-buy-egypt-pound-1"), 
						Instruction.TransactionType.BUY,
						0.057,
						new Currency("EGP"),
						LocalDate.parse("07 Jan 2016", DateTimeFormatter.ofPattern("dd MMM yyyy")),
						LocalDate.parse("08 Jan 2016", DateTimeFormatter.ofPattern("dd MMM yyyy")),
						300,
						2274.35)
				
				);
		
		instructions.addAll(monToFriInstructions);
		instructions.addAll(sunToThurInstructions);
		return instructions;
	}
}
