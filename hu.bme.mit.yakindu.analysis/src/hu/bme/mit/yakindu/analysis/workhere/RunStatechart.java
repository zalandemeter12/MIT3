package hu.bme.mit.yakindu.analysis.workhere;

import java.io.IOException;

import hu.bme.mit.yakindu.analysis.RuntimeService;
import hu.bme.mit.yakindu.analysis.TimerService;
import hu.bme.mit.yakindu.analysis.example.ExampleStatemachine;
import hu.bme.mit.yakindu.analysis.example.IExampleStatemachine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RunStatechart {
	
	public static void main(String[] args) throws IOException {
//		ExampleStatemachine s = new ExampleStatemachine();
//		s.setTimer(new TimerService());
//		RuntimeService.getInstance().registerStatemachine(s, 200);
//		s.init();
//		s.enter();
//		s.runCycle();
//		print(s);
//		s.raiseStart();
//		s.runCycle();
//		System.in.read();
//		s.raiseWhite();
//		s.runCycle();
//		print(s);
//		System.exit(0);
		ExampleStatemachine s = new ExampleStatemachine();
		s.setTimer(new TimerService());
		RuntimeService.getInstance().registerStatemachine(s, 200);
		s.init();
		s.enter();
		Scanner scanner = new Scanner(System.in);
		String last = "";
		while(!last.equals("exit"))
		{
			last = scanner.next();
			switch (last)
			{
			case "start":
				s.raiseStart();
				s.runCycle();
				break;
			case "white":
				s.raiseWhite();
				s.runCycle();
				break;
			case "black":
				s.raiseBlack();
				s.runCycle();
				break;
			default:
				break;
			}
			print(s);
		}
		scanner.close();
		System.exit(0);
	}

	public static void print(IExampleStatemachine s) {
		System.out.println("W = " + s.getSCInterface().getWhiteTime());
		System.out.println("B = " + s.getSCInterface().getBlackTime());
	}
}
