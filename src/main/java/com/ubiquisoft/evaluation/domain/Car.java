package com.ubiquisoft.evaluation.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Map;
import java.util.Iterator;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Car {

	private String year;
	private String make;
	private String model;

	private List<Part> parts;

	public Map<PartType, Integer> getMissingPartsMap() {
		/*
		 * Return map of the part types missing.
		 *
		 * Each car requires one of each of the following types:
		 *      ENGINE, ELECTRICAL, FUEL_FILTER, OIL_FILTER
		 * and four of the type: TIRE
		 *
		 * Example: a car only missing three of the four tires should return a map like this:
		 *
		 *      {
		 *          "TIRE": 3
		 *      }
		 */

		// Instantiate the return Map
		Map<PartType, Integer> missingPartsMap = new java.util.HashMap<PartType, Integer>();
		missingPartsMap.put(PartType.ENGINE, 1);
		missingPartsMap.put(PartType.ELECTRICAL, 1);
		missingPartsMap.put(PartType.TIRE, 4);
		missingPartsMap.put(PartType.FUEL_FILTER, 1);
		missingPartsMap.put(PartType.OIL_FILTER, 1);

		if(this.parts.isEmpty())
		{	// Check edge case
			return missingPartsMap;
		}
		else
		{
			Iterator aPart = parts.iterator();
			while(aPart.hasNext())
			{	// iterate across the parts list whilst checking against the missing parts map
				Part thisPart = (Part)aPart.next();
				PartType thisPartType = thisPart.getType();
				int x = missingPartsMap.get(thisPartType);

				if(x == 1)
				{	// remove the part type from the map
					missingPartsMap.remove(thisPartType, x);
				}
				else
				{	// otherwise decrement it for tire case
					missingPartsMap.replace(thisPartType, x, --x);
				}
			}
		}
		return missingPartsMap;
	}

	@Override
	public String toString() {
		return "Car{" +
				"year='" + year + '\'' +
				", make='" + make + '\'' +
				", model='" + model + '\'' +
				", parts=" + parts +
				'}';
	}

	/* --------------------------------------------------------------------------------------------------------------- */
	/*  Getters and Setters *///region
	/* --------------------------------------------------------------------------------------------------------------- */

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public List<Part> getParts() {
		return parts;
	}

	public void setParts(List<Part> parts) {
		this.parts = parts;
	}

	/* --------------------------------------------------------------------------------------------------------------- */
	/*  Getters and Setters End *///endregion
	/* --------------------------------------------------------------------------------------------------------------- */

}
