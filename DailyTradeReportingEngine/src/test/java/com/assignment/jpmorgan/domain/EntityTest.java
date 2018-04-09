package com.assignment.jpmorgan.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.assignment.jpmorgan.domain.Entity;

public class EntityTest {
	
	Entity entity;

	@Before
	public void setUp() throws Exception {
		entity = new Entity("entity-name");
	}
	

	@Test
	public void testGetName() {
		assertEquals("entity-name", entity.getName());
	}

	@Test
	public void testSetName() {
		entity.setName("new-entity-name");
		assertEquals("new-entity-name", entity.getName());
	}

}
