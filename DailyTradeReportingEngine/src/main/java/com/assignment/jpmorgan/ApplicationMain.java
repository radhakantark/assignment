package com.assignment.jpmorgan;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.assignment.jpmorgan.config.Configuration;
import com.assignment.jpmorgan.controllers.TradingManager;
import com.assignment.jpmorgan.domain.Entity;
import com.assignment.jpmorgan.services.InstructionsService;
import com.assignment.jpmorgan.services.InstructionsServiceImpl;


class PrettyPrintingMap<K, V> {
    private Map<K, V> map;

    public PrettyPrintingMap(Map<K, V> map) {
        this.map = map;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        Iterator<Entry<K, V>> iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<K, V> entry = iter.next();
            sb.append(entry.getKey());
            sb.append(" = ");
            sb.append(entry.getValue());                      
            sb.append("\n");
        }
        return sb.toString();
    }
}

class PrettyPrintingMapWithDoubleValue<K, V> {
    private Map<K, V> map;
    NumberFormat formatter = new DecimalFormat("#0.00");

    public PrettyPrintingMapWithDoubleValue(Map<K, V> map) {
        this.map = map;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        Iterator<Entry<K, V>> iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<K, V> entry = iter.next();
            sb.append(entry.getKey());
            sb.append(" = ");
            sb.append(formatter.format(entry.getValue()));                      
            sb.append("\n");
        }
        return sb.toString();
    }
}


public class ApplicationMain {
	public static final String PACKAGE_NAME = "com.assignment.jpmorgan";
	public static final Logger logger = Logger.getLogger(PACKAGE_NAME);

	public static void main(String[] args) {
		Logger.getLogger(PACKAGE_NAME).setLevel(Level.OFF);		
		logger.info("Enter ApplicationMain");
		InstructionsService instructionsService = new InstructionsServiceImpl();				
		TradingManager tradingManager = new TradingManager(instructionsService,
				new Configuration());
		
		tradingManager.init();
		
		Map<LocalDate, Double> incomingSettleAmt = tradingManager.getDailyIncomingSettlement();
		Map<LocalDate, Double> sortedIncomingSettleAmt = new TreeMap<LocalDate, Double>(incomingSettleAmt);
		System.out.println("Amount in USD settled incoming daily:\n" + new PrettyPrintingMapWithDoubleValue<LocalDate, Double>(sortedIncomingSettleAmt));
		System.out.println("");
		
		Map<LocalDate, Double> outgoingSettleAmt = tradingManager.getDailyOutgoingSettlement();
		Map<LocalDate, Double> sortedOutgoingSettleAmt = new TreeMap<LocalDate, Double>(outgoingSettleAmt);
		System.out.println("Amount in USD settled outgoing daily:\n" + new PrettyPrintingMapWithDoubleValue<LocalDate, Double>(sortedOutgoingSettleAmt));
		System.out.println();
		
		Map<LocalDate, List<Entity>> incEntitiesRanking = tradingManager.getRankingOfIncomingEntities();
		Map<LocalDate, List<Entity>> sortedIncEntitiesRanking = new TreeMap<LocalDate, List<Entity>>(incEntitiesRanking);
		System.out.println("Ranking of entities based on incoming:\n" + new PrettyPrintingMap<LocalDate, List<Entity>>(sortedIncEntitiesRanking));
		System.out.println();
		
		Map<LocalDate, List<Entity>> outEntitiesRanking = tradingManager.getRankingOfOutgoingEntities();
		Map<LocalDate, List<Entity>> sortedOutEntitiesRanking = new TreeMap<LocalDate, List<Entity>>(outEntitiesRanking);
		System.out.println("Ranking of entities based on outgoing:\n" + new PrettyPrintingMap<LocalDate, List<Entity>>(sortedOutEntitiesRanking));		
	}
}


