package com.assignment.jpmorgan.controllers;

import static java.util.stream.Collectors.toList;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.assignment.jpmorgan.config.Configuration;
import com.assignment.jpmorgan.domain.Entity;
import com.assignment.jpmorgan.domain.Instruction;
import com.assignment.jpmorgan.services.InstructionsService;

public class TradingManager {
	private static final Logger logger = Logger.getLogger(TradingManager.class.getName());
	private InstructionsService instructionsService;
	private List<Instruction> instructions;
	private List<Instruction> monToFriInstructions;
	private List<Instruction> sunToThurInstructions;
	private Configuration configuration;
	
	
	public TradingManager(InstructionsService instructionsService, Configuration configuration) {		
		this.instructionsService = instructionsService;		
		this.configuration = configuration;		
	}
	
	
	public void init() {
		instructions = this.instructionsService.getInstructions();
		
		monToFriInstructions = instructions.stream()
				.filter(ins -> configuration.getMonToFridTradingCurrency().contains(ins.getCurrency()))
				.collect(toList());
		
		logger.info("monToFriInstructions: " + monToFriInstructions);
		
		for(Instruction inst : monToFriInstructions) {
			if(inst.getSettlementDate().getDayOfWeek() == DayOfWeek.SATURDAY ) {
				inst.setSettlementDate(inst.getSettlementDate().plusDays(2));
			} else if(inst.getSettlementDate().getDayOfWeek() == DayOfWeek.SUNDAY) {
				inst.setSettlementDate(inst.getSettlementDate().plusDays(1));
			}
		}
		
		logger.info("monToFriInstructions After date adjustment: " + monToFriInstructions);				
		
		sunToThurInstructions = instructions.stream()
				.filter(ins -> configuration.getSunToThursTradingCurrency().contains(ins.getCurrency()))
				.collect(toList());
		
		logger.info("sunToThurInstructions: " + sunToThurInstructions);
		
		for(Instruction inst : sunToThurInstructions) {
			if(inst.getSettlementDate().getDayOfWeek() == DayOfWeek.FRIDAY ) {
				inst.setSettlementDate(inst.getSettlementDate().plusDays(2));
			} else if(inst.getSettlementDate().getDayOfWeek() == DayOfWeek.SATURDAY) {
				inst.setSettlementDate(inst.getSettlementDate().plusDays(1));
			}
		}
		
		logger.info("sunToThurInstructions After date adjustment: " + sunToThurInstructions);
		
		logger.info("instructions: " + instructions);
		
	}
	
	
	public Map<LocalDate, Double> getDailyIncomingSettlement() {
				
		Map<LocalDate, Double> monToFriIncSett = getDailyIncomingSettlement(monToFriInstructions,
				new MonToFriInstructionsSettler());
		logger.info("monToFriIncSett: " + monToFriIncSett);
		
		Map<LocalDate, Double> sunToThurIncSett = getDailyIncomingSettlement(sunToThurInstructions,
				new SunToThurInstructionsSettler());
		logger.info("sunToThurIncSett: " + sunToThurIncSett);
		
		Map<LocalDate, Double> dailyIncSett = Stream.of(monToFriIncSett, sunToThurIncSett)
        		.flatMap(map -> map.entrySet().stream())
        		.collect(
        				Collectors.toMap(
        						Map.Entry::getKey,
        						Map.Entry::getValue,
        						Double::sum
        						)
        				);
		
		return dailyIncSett;		
	}
	
	
	public Map<LocalDate, Double> getDailyOutgoingSettlement() {		
		
		Map<LocalDate, Double> monToFriOutSett = getDailyOutgoingSettlement(monToFriInstructions,
				new MonToFriInstructionsSettler());
		logger.info("monToFriOutSett: " + monToFriOutSett);
		
		Map<LocalDate, Double> sunToThurOutSett = getDailyOutgoingSettlement(sunToThurInstructions,
				new SunToThurInstructionsSettler());
		logger.info("sunToThurOutSett: " + sunToThurOutSett);
		
		Map<LocalDate, Double> dailyOutSett = Stream.of(monToFriOutSett, sunToThurOutSett)
        		.flatMap(map -> map.entrySet().stream())
        		.collect(
        				Collectors.toMap(
        						Map.Entry::getKey,
        						Map.Entry::getValue,
        						Double::sum
        						)
        				);
		
		return dailyOutSett;
	}
	
	
	public Map<LocalDate, List<Entity>> getRankingOfIncomingEntities() {
		Reporter reporter = new Reporter(instructions);
		Map<LocalDate, List<Entity>> entities = reporter.getRankingOfIncomingEntities();		
		return entities;
	}
	
	
	public Map<LocalDate, List<Entity>> getRankingOfOutgoingEntities() {
		Reporter reporter = new Reporter(instructions);
		Map<LocalDate, List<Entity>> entities= reporter.getRankingOfOutgoingEntities();
		return entities;
	}
	
	
	private Map<LocalDate, Double> getDailyIncomingSettlement(
			List<Instruction> instructions,
			InstructionsSettler instructionsSettler) {
		Reporter reporter = new Reporter(instructions, instructionsSettler);			
		
		Map<LocalDate, Double> incSett = reporter.getDailyIncomingSettlement();
		logger.info("incSett: " + incSett);
		return incSett;
	}
	
	
	private Map<LocalDate, Double> getDailyOutgoingSettlement(
			List<Instruction> instructions,
			InstructionsSettler instructionsSettler) {
		Reporter reporter = new Reporter(instructions, instructionsSettler);			
		
		Map<LocalDate, Double> outSett = reporter.getDailyOutgoingSettlement();
		logger.info("outSett: " + outSett);
		return outSett;
	}

}
