package com.assignment.jpmorgan.controllers;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingDouble;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.assignment.jpmorgan.domain.Instruction;

public class SunToThurInstructionsSettler implements InstructionsSettler {
	
	
	private boolean shouldSettle(LocalDate settleDate) {		
		if(settleDate.getDayOfWeek() != DayOfWeek.FRIDAY &&
				settleDate.getDayOfWeek() != DayOfWeek.SATURDAY) {
			return true;
		}
		return false;
	}
	

	@Override
	public Map<LocalDate, Double> settleIncoming(List<Instruction> instructions) {
		Map<LocalDate, Double> everyDay= instructions.stream()
				.filter(inst -> inst.getTransactionType()==Instruction.TransactionType.SALE &&
						shouldSettle(inst.getSettlementDate()))
				.collect(groupingBy(Instruction::getSettlementDate, summingDouble(Instruction::getTradeAmount)));				
		
		return everyDay;	
	}
	

	@Override
	public Map<LocalDate, Double> settleOutgoing(List<Instruction> instructions) {
		Map<LocalDate, Double> everyDay= instructions.stream()
				.filter(inst -> inst.getTransactionType()==Instruction.TransactionType.BUY &&
						shouldSettle(inst.getSettlementDate()))
				.collect(groupingBy(Instruction::getSettlementDate, summingDouble(Instruction::getTradeAmount)));				
		
		return everyDay;		
	}

}
