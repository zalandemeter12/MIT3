package hu.bme.mit.yakindu.analysis.workhere;

import java.io.IOException;
import java.util.Scanner;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.junit.Test;
import org.yakindu.sct.model.sgraph.State;
import org.yakindu.sct.model.sgraph.Statechart;
import org.yakindu.sct.model.sgraph.Transition;
import org.yakindu.sct.model.stext.stext.EventDefinition;
import org.yakindu.sct.model.stext.stext.VariableDefinition;

import hu.bme.mit.model2gml.Model2GML;
import hu.bme.mit.yakindu.analysis.RuntimeService;
import hu.bme.mit.yakindu.analysis.TimerService;
import hu.bme.mit.yakindu.analysis.example.ExampleStatemachine;
import hu.bme.mit.yakindu.analysis.modelmanager.ModelManager;

public class Main {
	@Test
	public void test() {
		main(new String[0]);
	}
	
	public static void main(String[] args) {
		ModelManager manager = new ModelManager();
		Model2GML model2gml = new Model2GML();
		
		// Loading model
		EObject root = manager.loadModel("model_input/example.sct");
		
		// Reading model
		Statechart s = (Statechart) root;
		TreeIterator<EObject> iterator = s.eAllContents();
		/*
		while (iterator.hasNext()) {
			EObject content = iterator.next();
			if(content instanceof EventDefinition) {
				EventDefinition event = (EventDefinition) content;
				System.out.println(event.getName());
			}
			if(content instanceof VariableDefinition) {
				VariableDefinition variable = (VariableDefinition) content;
				System.out.println(variable.getName());
			}
		}
		*/
		System.out.println("package hu.bme.mit.yakindu.analysis.workhere;");
		System.out.println("");
		System.out.println("import java.io.IOException;");
		System.out.println("import hu.bme.mit.yakindu.analysis.RuntimeService;");
		System.out.println("import hu.bme.mit.yakindu.analysis.TimerService;");
		System.out.println("import hu.bme.mit.yakindu.analysis.example.ExampleStatemachine;");
		System.out.println("import hu.bme.mit.yakindu.analysis.example.IExampleStatemachine;");
		System.out.println("import java.util.Scanner;");
		System.out.println("");
		System.out.println("public class RunStatechart {");
		System.out.println("");
		System.out.println("\tpublic static void main(String[] args) throws IOException {");
		System.out.println("\t\tExampleStatemachine s = new ExampleStatemachine();");
		System.out.println("\t\ts.setTimer(new TimerService());");
		System.out.println("\t\tRuntimeService.getInstance().registerStatemachine(s, 200);");
		System.out.println("\t\ts.init();");
		System.out.println("\t\ts.enter();");
		System.out.println("\t\tScanner scanner = new Scanner(System.in);");
		System.out.println("\t\tString last = \"\";");
		System.out.println("\t\twhile(!last.equals(\"exit\"))");
		System.out.println("\t\t{");
		System.out.println("\t\t\tlast = scanner.next();");
		System.out.println("\t\t\tswitch (last)");
		System.out.println("\t\t\t{");
		while (iterator.hasNext()) {
			EObject content = iterator.next();
			if(content instanceof EventDefinition) {
				EventDefinition event = (EventDefinition) content;
				String name = event.getName();
				String cap = name.substring(0, 1).toUpperCase() + name.substring(1);
				System.out.println("\t\t\tcase \"" + event.getName() + "\":");
				System.out.println("\t\t\t\ts.raise" + cap + "();");
				System.out.println("\t\t\t\ts.runCycle();");
				System.out.println("\t\t\t\tbreak;");
			}
		}
		System.out.println("\t\t\tdefault:");
		System.out.println("\t\t\t\tbreak;");
		System.out.println("\t\t\t}");
		System.out.println("\t\t\tprint(s);");
		System.out.println("\t\t}");
		System.out.println("\t\tscanner.close();");
		System.out.println("\t\tSystem.exit(0);");
		System.out.println("\t}");
		System.out.println("");
		System.out.println("\tpublic static void print(IExampleStatemachine s) {");
		s = (Statechart) root;
		iterator = s.eAllContents();
		while (iterator.hasNext()) {
			EObject content = iterator.next();
			if(content instanceof VariableDefinition) {
				VariableDefinition variable = (VariableDefinition) content;
				String name = variable.getName();
				String cap = name.substring(0, 1).toUpperCase() + name.substring(1);
				System.out.println("\t\tSystem.out.println(\"" + name.substring(0, 1).toUpperCase() + " = \" + s.getSCInterface().get" + cap + "());");
			}
		}
		System.out.println("\t}");
		System.out.println("}");

		// Transforming the model into a graph representation
		String content = model2gml.transform(root);
		// and saving it
		manager.saveFile("model_output/graph.gml", content);
	}
}
