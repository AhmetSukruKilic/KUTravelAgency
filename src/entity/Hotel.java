package entity;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Locale;

import cities.City;
import reservation.SingleReservations;

public class Hotel implements Entity {

	private String hotelName;
	private String city;
	private String roomType;
	private String availableRooms;
	private String pricePerNight;
	private String distanceToAirport;
	private static HashSet<Hotel> allHotels = new HashSet<>();

	public Hotel(String[] hotelInfo) {
		int i = 0;
		hotelName = hotelInfo[i++];
		city = hotelInfo[i++];
		roomType = hotelInfo[i++];
		availableRooms = hotelInfo[i++];
		pricePerNight = hotelInfo[i++];
		distanceToAirport = hotelInfo[i++];
		allHotels.add(this);
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
	 * @return the hotelName
	 */
	public String getHotelName() {
		return hotelName;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @return the roomType
	 */
	public String getRoomType() {
		return roomType;
	}

	/**
	 * @return the availableRooms
	 */
	public String getAvailableRooms() {
		return availableRooms;
	}

	/**
	 * @return the pricePerNight
	 */
	public String getPricePerNight() {
		return pricePerNight;
	}

	/**
	 * @return the distanceToAirport
	 */
	public String getDistanceToAirport() {
		return distanceToAirport;
	}

	public double getDoublePricePerNight() {
		try {
			return Double.parseDouble(pricePerNight);
		} catch (NumberFormatException e) {
			throw new NumberFormatException("cost format cannot be converted to double at Hotel");
		}

	}

	public int getNumberAvailability() {
		try {
			return Integer.parseInt(availableRooms);
		} catch (NumberFormatException e) {
			throw new NumberFormatException("availableRooms format cannot be converted to double at Hotel");
		}
	}

	public double getDoubleCost() {
		return getDoublePricePerNight();
	}

	public double getDoubleDistanceToAirport() {
		try {
			return Double.parseDouble(distanceToAirport);
		} catch (NumberFormatException e) {
			throw new NumberFormatException("distanceToAirport format cannot be converted to double at Hotel");
		}
	}

	/**
	 * @return the allHotels
	 */
	public static HashSet<Hotel> getAllHotels() {
		return allHotels;
	}

	@Override
	public String getEntityShortUniqueInfo() {
		return "(" + hotelName + "|" + city + "|" + roomType + ")";
	}

	@Override
	public String getEntityShortUniqueInfoWithPlaces() {
		return getEntityShortUniqueInfo() + ", in " + getCity();
	}

	public static Entity returnEntity(String hotelInfo) {
		for (Hotel hotel : allHotels) {
			if (hotelInfo.equals(hotel.getEntityShortUniqueInfo())) {
				return hotel;
			}
		}
		System.err.println("No such entity: " + hotelInfo);
		return null;
	}

	@Override
	public String toString() {
		return getEntityShortUniqueInfo();
	}

	public String getEntityForOption(LocalDate startDate, LocalDate endDate) {
	    NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
	    numberFormat.setMinimumFractionDigits(2);
	    numberFormat.setMaximumFractionDigits(2);

	    String formattedCost = numberFormat.format(getDoublePricePerNight());
	    String formattedDistance = numberFormat.format(getDoubleDistanceToAirport());

	    return String.format("name: %s; type: %s; cost: %s$; distance to airport: %s km; (Empty fields: %d)",
	            hotelName, 
	            roomType, 
	            formattedCost, 
	            formattedDistance,
	            (SingleReservations.getNumberAvailiblity(this, startDate, endDate) > 0
	                    ? SingleReservations.getNumberAvailiblity(this, startDate, endDate)
	                    : 0)
	    );
	}
	
	public String getEntityForOptionPrefix() {
	    NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
	    numberFormat.setMinimumFractionDigits(2);
	    numberFormat.setMaximumFractionDigits(2);

	    String formattedCost = numberFormat.format(getDoublePricePerNight());
	    String formattedDistance = numberFormat.format(getDoubleDistanceToAirport());

	    return String.format("name: %s; type: %s; cost: %s$; distance to airport: %s km",
	            hotelName, 
	            roomType, 
	            formattedCost, 
	            formattedDistance);
	}

	
	public static Entity returnEntity(String hotelInfo, String city) {
		for (Hotel hotel : allHotels) {
			if (hotelInfo.contains(hotel.getEntityForOptionPrefix()) && hotel.getFromCity().equals(city)) {
				return hotel;
			}
		}
		System.err.println("No such entity: " + hotelInfo);
		return null;
	}

	@Override
	public String getEntityForOptionGo(LocalDate startDate) {
	    NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
	    numberFormat.setMinimumFractionDigits(2);
	    numberFormat.setMaximumFractionDigits(2);

	    String formattedCost = numberFormat.format(getDoublePricePerNight());
	    String formattedDistance = numberFormat.format(getDoubleDistanceToAirport());

	    return String.format("name: %s; type: %s; cost: %s$; distance to airport: %s km; (Empty fields: %d)",
	            hotelName, 
	            roomType, 
	            formattedCost, 
	            formattedDistance,
	            (SingleReservations.getNumberAvailiblity(this, startDate, startDate) > 0
	                    ? SingleReservations.getNumberAvailiblity(this, startDate, startDate)
	                    : 0)
	    );
	}

	@Override
	public String getEntityForOptionReturn(LocalDate endDate) {
	    NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
	    numberFormat.setMinimumFractionDigits(2);
	    numberFormat.setMaximumFractionDigits(2);

	    String formattedCost = numberFormat.format(getDoublePricePerNight());
	    String formattedDistance = numberFormat.format(getDoubleDistanceToAirport());

	    return String.format("name: %s; type: %s; cost: %s$; distance to airport: %s km; (Empty fields: %d)",
	            hotelName, 
	            roomType, 
	            formattedCost, 
	            formattedDistance,
	            (SingleReservations.getNumberAvailiblity(this, endDate, endDate) > 0
	                    ? SingleReservations.getNumberAvailiblity(this, endDate, endDate)
	                    : 0)
	    );
	}
	
	public String getEntityForOptionGoBack(LocalDate startDate, LocalDate endDate) {
		return  "H: " + getEntityForOption(startDate, endDate);
	}

}
