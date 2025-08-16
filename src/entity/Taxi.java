package entity;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Locale;

import cities.City;
import reservation.SingleReservations;

public class Taxi implements Entity {

	private String city;
	private String taxiType;
	private String availableTaxis;
	private String baseFare;
	private String perKmRate;
	private static HashSet<Taxi> allTaxis = new HashSet<Taxi>();

	public Taxi(String[] hotelInfo) {
		int i = 0;
		city = hotelInfo[i++];
		taxiType = hotelInfo[i++];
		availableTaxis = hotelInfo[i++];
		baseFare = hotelInfo[i++];
		perKmRate = hotelInfo[i++];
		allTaxis.add(this);
		City.addEntityCity(this);
	}

	@Override
	public String getFromCity() {
		return city;
	}

	@Override
	public String getToCity() {
		return city;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @return the taxiType
	 */
	public String getTaxiType() {
		return taxiType;
	}

	/**
	 * @return the availableTaxis
	 */
	public String getAvailableTaxis() {
		return availableTaxis;
	}

	/**
	 * @return the baseFare
	 */
	public String getBaseFare() {
		return baseFare;
	}

	/**
	 * @return the allTaxis
	 */
	public static HashSet<Taxi> getAllTaxis() {
		return allTaxis;
	}

	/**
	 * @return the perKmRate
	 */
	public String getPerKmRate() {
		return perKmRate;
	}
	

	public int getNumberAvailability() {
		try {
			return Integer.parseInt(availableTaxis);
		} catch (NumberFormatException e) {
			throw new NumberFormatException("availableTaxis format cannot be converted to double at Taxis");
		}
	}

	@Override
	public String getEntityShortUniqueInfo() {
		return "(" + city + "|" + taxiType + ")";
	}

	@Override
	public String getEntityShortUniqueInfoWithPlaces() {
		return getEntityShortUniqueInfo() + ", in " + getCity();
	}

	public static Entity returnEntity(String taxiInfo) {
		for (Taxi taxi : allTaxis) {
			if (taxiInfo.equals(taxi.getEntityShortUniqueInfo())) {
				return taxi;
			}
		}
		System.err.println("No such entity: " + taxiInfo);
		return null;
	}

	public double getDoubleBaseFare() {
		try {
			return Double.parseDouble(baseFare);
		} catch (NumberFormatException e) {
			throw new NumberFormatException("cost format cannot be converted to double at Hotel");
		}
	}

	public double getDoublePerKmRate() {
		try {
			return Double.parseDouble(perKmRate);
		} catch (NumberFormatException e) {
			throw new NumberFormatException("cost format cannot be converted to double at Hotel");
		}
	}

	@Override
	public String toString() {
		return getEntityShortUniqueInfo();
	}

	public String getEntityForOptionPrefix() {
	    NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
	    numberFormat.setMinimumFractionDigits(2);
	    numberFormat.setMaximumFractionDigits(2);

	    String formattedBaseFare = numberFormat.format(getDoubleBaseFare());
	    String formattedPerKmRate = numberFormat.format(getDoublePerKmRate());

	    return String.format("type: %s; base: %s$; rate: %s$/km",
	            taxiType,
	            formattedBaseFare,
	            formattedPerKmRate);
	}


	public String getEntityForOption(LocalDate startDate, LocalDate endDate) {
	    NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
	    numberFormat.setMinimumFractionDigits(2);
	    numberFormat.setMaximumFractionDigits(2);

	    String formattedBaseFare = numberFormat.format(getDoubleBaseFare());
	    String formattedPerKmRate = numberFormat.format(getDoublePerKmRate());

	    return String.format("type: %s; base: %s$; rate: %s$/km; (Empty fields: %d)",
	            taxiType,
	            formattedBaseFare,
	            formattedPerKmRate,
	            (SingleReservations.getNumberAvailiblity(this, startDate, endDate) > 0
	                    ? SingleReservations.getNumberAvailiblity(this, startDate, endDate)
	                    : 0));
	}
	
	public String getEntityForOptionGo(LocalDate startDate) {
	    NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
	    numberFormat.setMinimumFractionDigits(2);
	    numberFormat.setMaximumFractionDigits(2);

	    String formattedBaseFare = numberFormat.format(getDoubleBaseFare());
	    String formattedPerKmRate = numberFormat.format(getDoublePerKmRate());

	    return String.format("type: %s; base: %s$; rate: %s$/km; (Empty fields: %d)",
	            taxiType,
	            formattedBaseFare,
	            formattedPerKmRate,
	            (SingleReservations.getNumberAvailiblityForStartDate(this, startDate) > 0
	                    ? SingleReservations.getNumberAvailiblityForStartDate(this, startDate)
	                    : 0));
	}
	
	public String getEntityForOptionReturn(LocalDate endDate) {
	    NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
	    numberFormat.setMinimumFractionDigits(2);
	    numberFormat.setMaximumFractionDigits(2);

	    String formattedBaseFare = numberFormat.format(getDoubleBaseFare());
	    String formattedPerKmRate = numberFormat.format(getDoublePerKmRate());

	    return String.format("type: %s; base: %s$; rate: %s$/km; (Empty fields: %d)",
	            taxiType,
	            formattedBaseFare,
	            formattedPerKmRate,
	            (SingleReservations.getNumberAvailiblityForEndDate(this,endDate) > 0
	                    ? SingleReservations.getNumberAvailiblityForEndDate(this, endDate)
	                    : 0));
	}
	


	public static Entity returnEntity(String taxiInfo, String city) {
		for (Taxi taxi : allTaxis) {
			if (taxiInfo.contains(taxi.getEntityForOptionPrefix()) && taxi.getFromCity().equals(city)) {
				return taxi;
			}
		}
		System.err.println("No such entity: " + taxiInfo);
		return null;
	}
	
	public String getEntityForOptionGoBack(LocalDate startDate, LocalDate endDate) {
		return  "T: " +getEntityForOptionGo(startDate);
	}

}
