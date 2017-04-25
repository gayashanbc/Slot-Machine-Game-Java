package test;

import static org.junit.Assert.*;

import org.junit.Test;

import controller.SlotMachine_Controller;

public class SlotMachine_Controller_Test {

	@Test
	public void addCoin_PositiveInputs() {
		SlotMachine_Controller s = new SlotMachine_Controller();
		assertEquals(10, s.getPlayer().getCreditAmount());
		assertEquals(15, s.addCoin(5));
	}

	@Test
	public void addCoin_NegativeInputs() {
		SlotMachine_Controller s = new SlotMachine_Controller();
		assertEquals(10, s.getPlayer().getCreditAmount());
		assertEquals(5, s.addCoin(-5));
	}

	@Test
	public void addCoin_Zero() {
		SlotMachine_Controller s = new SlotMachine_Controller();
		assertEquals(10, s.getPlayer().getCreditAmount());
		assertEquals(10, s.addCoin(0));
	}

	@Test
	public void addBet_PositiveInputs() {
		SlotMachine_Controller s = new SlotMachine_Controller();
		assertEquals(0, s.getPlayer().getBetAmount());
		assertEquals(5, s.addBet(5));
	}

	@Test
	public void addBet_NegativeInputs() {
		SlotMachine_Controller s = new SlotMachine_Controller();
		assertEquals(0, s.getPlayer().getBetAmount());
		assertEquals(-5, s.addBet(-5));
	}

	@Test
	public void addBet_Zero() {
		SlotMachine_Controller s = new SlotMachine_Controller();
		assertEquals(0, s.getPlayer().getBetAmount());
		assertEquals(0, s.addBet(0));
	}

	@Test
	public void resetBet() {
		SlotMachine_Controller s = new SlotMachine_Controller();
		s.addBet(5);
		s.resetBet();
		assertEquals(0, s.getPlayer().getBetAmount());
		assertEquals(10, s.getPlayer().getCreditAmount());
	}
	

}



