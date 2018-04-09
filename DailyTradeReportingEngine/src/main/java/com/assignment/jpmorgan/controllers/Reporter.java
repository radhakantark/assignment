package com.assignment.jpmorgan.controllers;

import static java.util.stream.Collectors.toList;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.assignment.jpmorgan.domain.Entity;
import com.assignment.jpmorgan.domain.Instruction;
import static java.util.stream.Collectors.*;

public class Reporter {
	private List<Instruction> instructions;
	private InstructionsSettler instructionsSettler;
	
	
	public Reporter(List<Instruction> instructions) {		
		this.instructions = instructions;		
	}
	
	
	public Reporter(List<Instruction> instructions, InstructionsSettler instructionsSettler) {		
		this.instructions = instructions;
		this.instructionsSettler = instructionsSettler;
	}
	

	public Map<LocalDate, Double> getDailyIncomingSettlement() {
		assert instructionsSettler!= null;
		return instructionsSettler.settleIncoming(instructions);		
	}
	
	
	public Map<LocalDate, Double> getDailyOutgoingSettlement() {
		assert instructionsSettler!= null;
		return instructionsSettler.settleOutgoing(instructions);
	}
	
	
	public Map<LocalDate, List<Entity>> getRankingOfIncomingEntities() {
		Map<LocalDate, List<Entity>> entitiesRanking = instructions.stream()
								.filter(inst -> inst.getTransactionType() == Instruction.TransactionType.SALE)
								.sorted(Comparator.comparing(Instruction::getTradeAmount).reversed())
								.collect(groupingBy(Instruction::getSettlementDate, 
										mapping(instruction->instruction.getEntity(),toList())
										));
		
		return entitiesRanking;
	}
	
	
	public Map<LocalDate, List<Entity>> getRankingOfOutgoingEntities() {
		Map<LocalDate, List<Entity>> entitiesRanking = instructions.stream()
				.filter(inst -> inst.getTransactionType() == Instruction.TransactionType.BUY)
				.sorted(Comparator.comparing(Instruction::getTradeAmount).reversed())
				.collect(groupingBy(Instruction::getSettlementDate, 
						mapping(instruction->instruction.getEntity(),toList())
						));

		return entitiesRanking;
	}
}
