package com.ubiquisoft.evaluation;
import com.ubiquisoft.evaluation.domain.Car;
import com.ubiquisoft.evaluation.domain.ConditionType;
import com.ubiquisoft.evaluation.domain.Part;
import com.ubiquisoft.evaluation.domain.PartType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.util.Map;
import java.util.List;
import java.util.Iterator;

public class CarDiagnosticEngine {

	public void executeDiagnostics(Car car) {
		/*
		 * Implement basic diagnostics and print results to console.
		 *
		 * The purpose of this method is to find any problems with a car's data or parts.
		 *
		 * Diagnostic Steps:
		 *      First   - Validate the 3 data fields are present, if one or more are
		 *                then print the missing fields to the console
		 *                in a similar manner to how the provided methods do.
		 *
		 *      Second  - Validate that no parts are missing using the 'getMissingPartsMap' method in the Car class,
		 *                if one or more are then run each missing part and its count through the provided missing part method.
		 *
		 *      Third   - Validate that all parts are in working condition, if any are not
		 *                then run each non-working part through the provided damaged part method.
		 *
		 *      Fourth  - If validation succeeds for the previous steps then print something to the console informing the user as such.
		 * A damaged part is one that has any condition other than NEW, GOOD, or WORN.
		 *
		 * Important:
		 *      If any validation fails, complete whatever step you are actively one and end diagnostics early.
		 *
		 * Treat the console as information being read by a user of this application. Attempts should be made to ensure
		 * console output is as least as informative as the provided methods.
		 */

		System.out.println("Beginning Diagnostics...");
		boolean continueFlag = true;

		// diagnostic step 1
		System.out.println("Retrieving car info...");
		String year = car.getYear();
		String make = car.getMake();
		String model = car.getModel();

		if(year.equals(""))
		{
			System.out.println("There is no year specified");
			continueFlag = false;
		}
		if(make.equals(""))
		{
			System.out.println("There is no make specified");
			continueFlag = false;
		}
		if(model.equals(""))
		{
			System.out.println("There is no model specified");
			continueFlag = false;
		}
		if(continueFlag)
		{
			System.out.println("Car{" +
					"year='" + year + '\'' +
					", make='" + make + '\'' +
					", model='" + model + '\'' +
					'}');

			//diagnostic step 2
			System.out.println("Checking for missing parts...");

			Map<PartType, Integer> missingPartsMap = car.getMissingPartsMap();
			if(missingPartsMap.isEmpty())
			{
				System.out.println("There are no missing parts");

				//diagnostic step 3
				System.out.println("Checking part conditions...");

				List<Part> parts = car.getParts();
				Iterator aPart = parts.iterator();
				while(aPart.hasNext())
				{	// iterate across the parts list whilst checking part condition
					Part thisPart = (Part)aPart.next();
					if(!thisPart.isInWorkingCondition())
					{
						PartType thisPartType = thisPart.getType();
						ConditionType thisPartCondition = thisPart.getCondition();
						this.printDamagedPart(thisPartType, thisPartCondition);
						continueFlag = false;
					}
				}
				if(continueFlag)
				{	// diagnostic step 4
					System.out.println("All parts are in working condition");
					System.out.println("Diagnostics Complete: Vehicle Passed");
				}
				else
				{	// diagnostic step 3 failed
					System.out.println("Aborting Diagnostics: Damaged Parts Detected");
				}
			}
			else
			{	// diagnostic step 2 failed
				if(missingPartsMap.containsKey(PartType.ENGINE))
				{
					int x = missingPartsMap.get(PartType.ENGINE);
					this.printMissingPart(PartType.ENGINE, x);
				}
				if(missingPartsMap.containsKey(PartType.ELECTRICAL))
				{
					int x = missingPartsMap.get(PartType.ELECTRICAL);
					this.printMissingPart(PartType.ELECTRICAL, x);
				}
				if(missingPartsMap.containsKey(PartType.TIRE))
				{
					int x = missingPartsMap.get(PartType.TIRE);
					this.printMissingPart(PartType.TIRE, x);
				}
				if(missingPartsMap.containsKey(PartType.FUEL_FILTER))
				{
					int x = missingPartsMap.get(PartType.FUEL_FILTER);
					this.printMissingPart(PartType.FUEL_FILTER, x);
				}
				if(missingPartsMap.containsKey(PartType.OIL_FILTER))
				{
					int x = missingPartsMap.get(PartType.OIL_FILTER);
					this.printMissingPart(PartType.OIL_FILTER, x);
				}
				System.out.println("Aborting Diagnostics: Missing Parts Detected");
			}
		}
		else
		{	// diagnostic step 1 failed
			System.out.println("Aborting Diagnostics: Critical Information Missing");
		}
	}

	private void printMissingPart(PartType partType, Integer count) {
		if (partType == null) throw new IllegalArgumentException("PartType must not be null");
		if (count == null || count <= 0) throw new IllegalArgumentException("Count must be greater than 0");

		System.out.println(String.format("Missing Part(s) Detected: %s - Count: %s", partType, count));
	}

	private void printDamagedPart(PartType partType, ConditionType condition) {
		if (partType == null) throw new IllegalArgumentException("PartType must not be null");
		if (condition == null) throw new IllegalArgumentException("ConditionType must not be null");

		System.out.println(String.format("Damaged Part Detected: %s - Condition: %s", partType, condition));
	}

	public static void main(String[] args) throws JAXBException {
		// Load classpath resource
		InputStream xml = ClassLoader.getSystemResourceAsStream("SampleCar.xml");

		// Verify resource was loaded properly
		if (xml == null) {
			System.err.println("An error occurred attempting to load SampleCar.xml");

			System.exit(1);
		}

		// Build JAXBContext for converting XML into an Object
		JAXBContext context = JAXBContext.newInstance(Car.class, Part.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();

		Car car = (Car) unmarshaller.unmarshal(xml);

		// Build new Diagnostics Engine and execute on deserialized car object.

		CarDiagnosticEngine diagnosticEngine = new CarDiagnosticEngine();

		diagnosticEngine.executeDiagnostics(car);

	}

}
