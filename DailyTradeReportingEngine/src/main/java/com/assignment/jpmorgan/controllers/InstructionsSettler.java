package com.assignment.jpmorgan.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.assignment.jpmorgan.domain.Instruction;

public interface InstructionsSettler {
	public Map<LocalDate, Double> settleIncoming(List<Instruction> instructions);
	public Map<LocalDate, Double> settleOutgoing(List<Instruction> instructions);
}
