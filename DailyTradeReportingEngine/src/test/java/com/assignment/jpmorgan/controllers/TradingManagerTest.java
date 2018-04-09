package com.assignment.jpmorgan.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.assignment.jpmorgan.config.Configuration;
import com.assignment.jpmorgan.domain.Currency;
import com.assignment.jpmorgan.domain.Entity;
import com.assignment.jpmorgan.domain.Instruction;
import com.assignment.jpmorgan.services.InstructionsService;

@RunWith(MockitoJUnitRunner.class)
public class TradingManagerTest {
	@Mock
	InstructionsService instructionsServiceMock;
	
	Configuration configuration = new Configuration();
	
	@Before
	public void setUp() throws Exception {
		
	}
	

	@Test
	public void testGetDailyIncomingSettlement_testMonToFriOneRecordOneResult() {		
		List<Instruction> monToFriInstructions = Arrays.asList(					
			new Instruction(new Entity("foo-sale-won-2"), 
					Instruction.TransactionType.SALE,
					0.00094,
					new Currency("WON"),
					LocalDate.parse("12 Feb 2016", DateTimeFormatter.ofPattern("dd MMM yyyy")),
					LocalDate.parse("16 Feb 2016", DateTimeFormatter.ofPattern("dd MMM yyyy")),
					50,
					234.85)
			);	
		
		when(instructionsServiceMock.getInstructions()).thenReturn(monToFriInstructions);
				
		TradingManager tradingManager = new TradingManager(instructionsServiceMock,	configuration);		
		tradingManager.init();		
		
		Map<LocalDate, Double> incomingSettleAmt = tradingManager.getDailyIncomingSettlement();
		assertEquals("Number of record must be one: ", 1, incomingSettleAmt.size());
		LocalDate settleDate = LocalDate.parse("16 Feb 2016", DateTimeFormatter.ofPattern("dd MMM yyyy"));		
		assertEquals("Trade Amounts must be same: ", 0.00094 * 50 * 234.85, incomingSettleAmt.get(settleDate).doubleValue(), 0.001);
		verify(instructionsServiceMock, times(1)).getInstructions();
	}
	
	
	@Test
	public void testGetDailyIncomingSettlement_testMonToFriTwoRecordsOneResult() {		
		List<Instruction> monToFriInstructions = Arrays.asList(					
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
		
		when(instructionsServiceMock.getInstructions()).thenReturn(monToFriInstructions);
		
		TradingManager tradingManager = new TradingManager(instructionsServiceMock, configuration);		
		tradingManager.init();
		Map<LocalDate, Double> incomingSettleAmt = tradingManager.getDailyIncomingSettlement();
		assertEquals("Number of records must be 1: ", 1, incomingSettleAmt.size());
		LocalDate settleDate = LocalDate.parse("16 Feb 2016", DateTimeFormatter.ofPattern("dd MMM yyyy"));
		double expected = (0.00094 * 50 * 234.85) + (0.00094 * 10 * 5892.55);
		assertEquals("Trade Amounts must be same: ", expected, incomingSettleAmt.get(settleDate).doubleValue(), 0.001);
		verify(instructionsServiceMock, times(1)).getInstructions();
	}
	
	
	@Test
	public void testGetDailyIncomingSettlement_testMonToFriTwoRecordsTwoResults() {		
		List<Instruction> monToFriInstructions = Arrays.asList(					
			new Instruction(new Entity("foo-sale-won-2"), 
					Instruction.TransactionType.SALE,
					0.00094,
					new Currency("WON"),
					LocalDate.parse("16 Feb 2016", DateTimeFormatter.ofPattern("dd MMM yyyy")),
					LocalDate.parse("17 Feb 2016", DateTimeFormatter.ofPattern("dd MMM yyyy")),
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
		
		when(instructionsServiceMock.getInstructions()).thenReturn(monToFriInstructions);
		
		TradingManager tradingManager = new TradingManager(instructionsServiceMock, configuration);		
		tradingManager.init();
		Map<LocalDate, Double> incomingSettleAmt = tradingManager.getDailyIncomingSettlement();
		assertEquals("Number of records must be 2: ", 2, incomingSettleAmt.size());
		LocalDate settleDateFirst = LocalDate.parse("17 Feb 2016", DateTimeFormatter.ofPattern("dd MMM yyyy"));
		double expectedFirst = (0.00094 * 50 * 234.85);
		assertEquals("Trade Amounts must be same: ", expectedFirst, incomingSettleAmt.get(settleDateFirst).doubleValue(), 0.001);
		LocalDate settleDateSecond = LocalDate.parse("16 Feb 2016", DateTimeFormatter.ofPattern("dd MMM yyyy"));
		double expectedSecond = (0.00094 * 10 * 5892.55);
		assertEquals("Trade Amounts must be same: ", expectedSecond, incomingSettleAmt.get(settleDateSecond).doubleValue(), 0.001);
		verify(instructionsServiceMock, times(1)).getInstructions();
	}
	
	
	@Test
	public void testGetDailyIncomingSettlement_testMonToFriWeekendSunday() {		
		List<Instruction> monToFriInstructions = Arrays.asList(					
			new Instruction(new Entity("foo-sale-won-2"), 
					Instruction.TransactionType.SALE,
					0.00094,
					new Currency("WON"),
					LocalDate.parse("06 Apr 2018", DateTimeFormatter.ofPattern("dd MMM yyyy")),
					LocalDate.parse("08 Apr 2018", DateTimeFormatter.ofPattern("dd MMM yyyy")),
					50,
					234.85)
			);		
		
		when(instructionsServiceMock.getInstructions()).thenReturn(monToFriInstructions);
		
		TradingManager tradingManager = new TradingManager(instructionsServiceMock, configuration);		
		tradingManager.init();
		Map<LocalDate, Double> incomingSettleAmt = tradingManager.getDailyIncomingSettlement();
		
		LocalDate settleDate = LocalDate.parse("09 Apr 2018", DateTimeFormatter.ofPattern("dd MMM yyyy"));		
		assertTrue("Trade must be settled on 09 Apr 2018: ", incomingSettleAmt.containsKey(settleDate));
		verify(instructionsServiceMock, times(1)).getInstructions();
	}
	
	@Test
	public void testGetDailyIncomingSettlement_testMonToFriWeekendSaturday() {		
		List<Instruction> monToFriInstructions = Arrays.asList(					
			new Instruction(new Entity("foo-sale-won-2"), 
					Instruction.TransactionType.SALE,
					0.00094,
					new Currency("WON"),
					LocalDate.parse("06 Apr 2018", DateTimeFormatter.ofPattern("dd MMM yyyy")),
					LocalDate.parse("07 Apr 2018", DateTimeFormatter.ofPattern("dd MMM yyyy")),
					50,
					234.85)
			);		
		
		when(instructionsServiceMock.getInstructions()).thenReturn(monToFriInstructions);
		
		TradingManager tradingManager = new TradingManager(instructionsServiceMock, configuration);		
		tradingManager.init();
		Map<LocalDate, Double> incomingSettleAmt = tradingManager.getDailyIncomingSettlement();
		
		LocalDate settleDate = LocalDate.parse("09 Apr 2018", DateTimeFormatter.ofPattern("dd MMM yyyy"));		
		assertTrue("Trade must be settled on 09 Apr 2018: ", incomingSettleAmt.containsKey(settleDate));
		verify(instructionsServiceMock, times(1)).getInstructions();
	}
	
	
	@Test
	public void testGetDailyIncomingSettlement_testSunToThurWeekendFriday() {		
		List<Instruction> monToFriInstructions = Arrays.asList(					
		new Instruction(new Entity("e2-sale-dinar-2"), 
				Instruction.TransactionType.SALE,
				2.65,
				new Currency("BHD"),
				LocalDate.parse("06 Apr 2018", DateTimeFormatter.ofPattern("dd MMM yyyy")),
				LocalDate.parse("06 Apr 2018", DateTimeFormatter.ofPattern("dd MMM yyyy")),
				3004,
				1563.85)
		);		
		
		when(instructionsServiceMock.getInstructions()).thenReturn(monToFriInstructions);
		
		TradingManager tradingManager = new TradingManager(instructionsServiceMock, configuration);		
		tradingManager.init();
		Map<LocalDate, Double> incomingSettleAmt = tradingManager.getDailyIncomingSettlement();
		
		LocalDate settleDate = LocalDate.parse("08 Apr 2018", DateTimeFormatter.ofPattern("dd MMM yyyy"));		
		assertTrue("Trade must be settled on 08 Apr 2018: ", incomingSettleAmt.containsKey(settleDate));
		verify(instructionsServiceMock, times(1)).getInstructions();
	}
	
	
	@Test
	public void testGetDailyIncomingSettlement_testSunToThurWeekendSaturday() {		
		List<Instruction> monToFriInstructions = Arrays.asList(					
		new Instruction(new Entity("e2-sale-dinar-2"), 
				Instruction.TransactionType.SALE,
				2.65,
				new Currency("BHD"),
				LocalDate.parse("06 Apr 2018", DateTimeFormatter.ofPattern("dd MMM yyyy")),
				LocalDate.parse("07 Apr 2018", DateTimeFormatter.ofPattern("dd MMM yyyy")),
				3004,
				1563.85)
		);		
		
		when(instructionsServiceMock.getInstructions()).thenReturn(monToFriInstructions);
		
		TradingManager tradingManager = new TradingManager(instructionsServiceMock, configuration);		
		tradingManager.init();
		Map<LocalDate, Double> incomingSettleAmt = tradingManager.getDailyIncomingSettlement();
		
		LocalDate settleDate = LocalDate.parse("08 Apr 2018", DateTimeFormatter.ofPattern("dd MMM yyyy"));		
		assertTrue("Trade must be settled on 08 Apr 2018: ", incomingSettleAmt.containsKey(settleDate));
		verify(instructionsServiceMock, times(1)).getInstructions();
	}
	
	
	@Test
	public void testGetDailyIncomingSettlement_testSunToThurWeekend() {		
		List<Instruction> monToFriInstructions = Arrays.asList(	
			new Instruction(new Entity("e2-sale-dinar-1"),
					Instruction.TransactionType.SALE,
					2.65,
					new Currency("BHD"),
					LocalDate.parse("06 Apr 2018", DateTimeFormatter.ofPattern("dd MMM yyyy")),
					LocalDate.parse("06 Apr 2018", DateTimeFormatter.ofPattern("dd MMM yyyy")),
					3004,
					1563.85),			
			new Instruction(new Entity("e2-sale-dinar-2"), 
					Instruction.TransactionType.SALE,
					2.65,
					new Currency("BHD"),
					LocalDate.parse("06 Apr 2018", DateTimeFormatter.ofPattern("dd MMM yyyy")),
					LocalDate.parse("07 Apr 2018", DateTimeFormatter.ofPattern("dd MMM yyyy")),
					3004,
					1563.85)
		);		
		
		when(instructionsServiceMock.getInstructions()).thenReturn(monToFriInstructions);
		
		TradingManager tradingManager = new TradingManager(instructionsServiceMock, configuration);		
		tradingManager.init();
		Map<LocalDate, Double> incomingSettleAmt = tradingManager.getDailyIncomingSettlement();
		
		LocalDate settleDate = LocalDate.parse("08 Apr 2018", DateTimeFormatter.ofPattern("dd MMM yyyy"));		
		double expected = 2 * (2.65 * 3004 * 1563.85);
		assertEquals("Trade must be settled on 08 Apr 2018", expected, incomingSettleAmt.get(settleDate), 0.001);
		verify(instructionsServiceMock, times(1)).getInstructions();
	}
	
	
	@Test
	public void testGetDailyOutgoingSettlement_testMonToFriTwoRecordsOneResult() {		
		List<Instruction> monToFriInstructions = Arrays.asList(					
			new Instruction(new Entity("foo-sale-won-2"), 
					Instruction.TransactionType.BUY,
					0.00094,
					new Currency("WON"),
					LocalDate.parse("12 Feb 2016", DateTimeFormatter.ofPattern("dd MMM yyyy")),
					LocalDate.parse("16 Feb 2016", DateTimeFormatter.ofPattern("dd MMM yyyy")),
					50,
					234.85),
			new Instruction(new Entity("foo-sale-won-3"), 
					Instruction.TransactionType.BUY,
					0.00094,
					new Currency("WON"),
					LocalDate.parse("15 Feb 2016", DateTimeFormatter.ofPattern("dd MMM yyyy")),
					LocalDate.parse("16 Feb 2016", DateTimeFormatter.ofPattern("dd MMM yyyy")),
					10,
					5892.55)
			);		
		
		when(instructionsServiceMock.getInstructions()).thenReturn(monToFriInstructions);
		
		TradingManager tradingManager = new TradingManager(instructionsServiceMock, configuration);		
		tradingManager.init();
		Map<LocalDate, Double> incomingSettleAmt = tradingManager.getDailyOutgoingSettlement();
		assertEquals("Number of records must be 1: ", 1, incomingSettleAmt.size());
		LocalDate settleDate = LocalDate.parse("16 Feb 2016", DateTimeFormatter.ofPattern("dd MMM yyyy"));
		double expected = (0.00094 * 50 * 234.85) + (0.00094 * 10 * 5892.55);
		assertEquals("Trade Amounts must be same: ", expected, incomingSettleAmt.get(settleDate).doubleValue(), 0.001);
		verify(instructionsServiceMock, times(1)).getInstructions();
	}
	
	
	@Test
	public void testGetDailyOutgoingSettlement_testSunToThurWeekend() {		
		List<Instruction> monToFriInstructions = Arrays.asList(	
			new Instruction(new Entity("e2-sale-dinar-1"),
					Instruction.TransactionType.BUY,
					2.65,
					new Currency("BHD"),
					LocalDate.parse("06 Apr 2018", DateTimeFormatter.ofPattern("dd MMM yyyy")),
					LocalDate.parse("06 Apr 2018", DateTimeFormatter.ofPattern("dd MMM yyyy")),
					3004,
					1563.85),			
			new Instruction(new Entity("e2-sale-dinar-2"), 
					Instruction.TransactionType.BUY,
					2.65,
					new Currency("BHD"),
					LocalDate.parse("06 Apr 2018", DateTimeFormatter.ofPattern("dd MMM yyyy")),
					LocalDate.parse("07 Apr 2018", DateTimeFormatter.ofPattern("dd MMM yyyy")),
					3004,
					1563.85)
		);		
		
		when(instructionsServiceMock.getInstructions()).thenReturn(monToFriInstructions);
		
		TradingManager tradingManager = new TradingManager(instructionsServiceMock, configuration);		
		tradingManager.init();
		Map<LocalDate, Double> incomingSettleAmt = tradingManager.getDailyOutgoingSettlement();
		
		LocalDate settleDate = LocalDate.parse("08 Apr 2018", DateTimeFormatter.ofPattern("dd MMM yyyy"));		
		double expected = 2 * (2.65 * 3004 * 1563.85);
		assertEquals("Trade must be settled on 08 Apr 2018", expected, incomingSettleAmt.get(settleDate), 0.001);
		verify(instructionsServiceMock, times(1)).getInstructions();
	}
	
	
	@Test
	public void testGetDailyIncomingSettlement_testRankingsForEachSettleDay() {
		List<Instruction> instructions = Arrays.asList(					
				new Instruction(new Entity("foo-sale-won-middle-value"), 
						Instruction.TransactionType.SALE,
						0.00094,
						new Currency("WON"),
						LocalDate.parse("16 Feb 2016", DateTimeFormatter.ofPattern("dd MMM yyyy")),
						LocalDate.parse("17 Feb 2016", DateTimeFormatter.ofPattern("dd MMM yyyy")),
						100,
						234.85),
				new Instruction(new Entity("foo-sale-won-min-value"), 
						Instruction.TransactionType.SALE,
						0.00094,
						new Currency("WON"),
						LocalDate.parse("16 Feb 2016", DateTimeFormatter.ofPattern("dd MMM yyyy")),
						LocalDate.parse("17 Feb 2016", DateTimeFormatter.ofPattern("dd MMM yyyy")),
						50,
						234.85),
				new Instruction(new Entity("foo-sale-won-max-value"), 
						Instruction.TransactionType.SALE,
						0.00094,
						new Currency("WON"),
						LocalDate.parse("16 Feb 2016", DateTimeFormatter.ofPattern("dd MMM yyyy")),
						LocalDate.parse("17 Feb 2016", DateTimeFormatter.ofPattern("dd MMM yyyy")),
						200,
						234.85)
				);		
			
			when(instructionsServiceMock.getInstructions()).thenReturn(instructions);
			
			TradingManager tradingManager = new TradingManager(instructionsServiceMock, configuration);		
			tradingManager.init();
			Map<LocalDate, List<Entity>> entitiesRanking = tradingManager.getRankingOfIncomingEntities();
			assertEquals("Number of records must be 1: ", 1, entitiesRanking.size());			
			List<Entity> expected = Arrays.asList(new Entity("foo-sale-won-max-value"), 
					new Entity("foo-sale-won-middle-value"), 
					new Entity("foo-sale-won-min-value"));
			LocalDate settleDate = LocalDate.parse("17 Feb 2016", DateTimeFormatter.ofPattern("dd MMM yyyy"));
			assertThat(entitiesRanking.get(settleDate), is(expected));
			verify(instructionsServiceMock, times(1)).getInstructions();
	}
	
	
	@Test
	public void testGetDailyOutgoingSettlement_testRankingsForEachSettleDay() {
		List<Instruction> instructions = Arrays.asList(					
				new Instruction(new Entity("foo-sale-won-middle-value"), 
						Instruction.TransactionType.BUY,
						0.00094,
						new Currency("WON"),
						LocalDate.parse("16 Feb 2016", DateTimeFormatter.ofPattern("dd MMM yyyy")),
						LocalDate.parse("17 Feb 2016", DateTimeFormatter.ofPattern("dd MMM yyyy")),
						100,
						234.85),
				new Instruction(new Entity("foo-sale-won-min-value"), 
						Instruction.TransactionType.BUY,
						0.00094,
						new Currency("WON"),
						LocalDate.parse("16 Feb 2016", DateTimeFormatter.ofPattern("dd MMM yyyy")),
						LocalDate.parse("17 Feb 2016", DateTimeFormatter.ofPattern("dd MMM yyyy")),
						50,
						234.85),
				new Instruction(new Entity("foo-sale-won-max-value"), 
						Instruction.TransactionType.BUY,
						0.00094,
						new Currency("WON"),
						LocalDate.parse("16 Feb 2016", DateTimeFormatter.ofPattern("dd MMM yyyy")),
						LocalDate.parse("17 Feb 2016", DateTimeFormatter.ofPattern("dd MMM yyyy")),
						200,
						234.85)
				);		
			
			when(instructionsServiceMock.getInstructions()).thenReturn(instructions);
			
			TradingManager tradingManager = new TradingManager(instructionsServiceMock, configuration);		
			tradingManager.init();
			Map<LocalDate, List<Entity>> entitiesRanking = tradingManager.getRankingOfOutgoingEntities();
			assertEquals("Number of records must be 1: ", 1, entitiesRanking.size());			
			List<Entity> expected = Arrays.asList(new Entity("foo-sale-won-max-value"), 
					new Entity("foo-sale-won-middle-value"), 
					new Entity("foo-sale-won-min-value"));
			LocalDate settleDate = LocalDate.parse("17 Feb 2016", DateTimeFormatter.ofPattern("dd MMM yyyy"));
			assertThat(entitiesRanking.get(settleDate), is(expected));
			verify(instructionsServiceMock, times(1)).getInstructions();
	}

 
}



