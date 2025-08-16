package cities;

import java.time.LocalDate;
import java.util.HashSet;

import entity.*;
import reservation.SingleReservations;

public interface City {

	static HashSet<Flight> getDepartureCityFlightEntities(String fromCity, String toCity, LocalDate startDate,
			LocalDate endDate) {
		HashSet<Entity> fromCities = new HashSet<>(FromCity.getCitysEntities(fromCity));
		HashSet<Entity> toCities = new HashSet<>(ToCity.getCitysEntities(toCity));

		fromCities.retainAll(toCities);

		HashSet<Flight> flightEntities = new HashSet<>();

		for (Entity entity : fromCities) {
			if (entity instanceof Flight && SingleReservations.getNumberAvailiblityForStartDate(entity, startDate) > 0) {
				flightEntities.add((Flight) entity);
			}
		}

		return flightEntities;
	}

	static HashSet<Flight> getArrivalCityFlightEntities(String fromCity, String toCity, LocalDate startDate,
			LocalDate endDate) {
		HashSet<Entity> fromCities = new HashSet<>(FromCity.getCitysEntities(toCity));
		HashSet<Entity> toCities = new HashSet<>(ToCity.getCitysEntities(fromCity));
		fromCities.retainAll(toCities);

		HashSet<Flight> flightEntities = new HashSet<>();

		for (Entity entity : fromCities) {
			if (entity instanceof Flight && SingleReservations.getNumberAvailiblityForEndDate(entity, endDate) > 0) {
				flightEntities.add((Flight) entity);
			}
		}

		return flightEntities;
	}

	static HashSet<Hotel> getArrivalCityHotelEntities(String toCity, LocalDate startDate, LocalDate endDate) {
		HashSet<Entity> toCities = new HashSet<>(ToCity.getCitysEntities(toCity));

		HashSet<Hotel> hotelEntities = new HashSet<>();

		for (Entity entity : toCities) {
			if (entity instanceof Hotel && SingleReservations.getNumberAvailiblity(entity, startDate, endDate) > 0) {
				hotelEntities.add((Hotel) entity);
			}
		}

		return hotelEntities;
	}

	static HashSet<Taxi> getArrivalCityTaxiEntities(String toCity, LocalDate startDate, LocalDate endDate) {
		HashSet<Entity> toCities = new HashSet<>(ToCity.getCitysEntities(toCity));

		HashSet<Taxi> taxiEntities = new HashSet<>();

		for (Entity entity : toCities) {
			if (entity instanceof Taxi && SingleReservations.getNumberAvailiblityForStartDate(entity, startDate) > 0) {
				taxiEntities.add((Taxi) entity);
			}
		}

		return taxiEntities;
	}
	
	
	static HashSet<Taxi> getArrivalCityReturnTaxiEntities(String toCity, LocalDate startDate, LocalDate endDate) {
		HashSet<Entity> toCities = new HashSet<>(ToCity.getCitysEntities(toCity));

		HashSet<Taxi> taxiEntities = new HashSet<>();

		for (Entity entity : toCities) {
			if (entity instanceof Taxi && SingleReservations.getNumberAvailiblityForEndDate(entity, endDate) > 0) {
				taxiEntities.add((Taxi) entity);
			}
		}

		return taxiEntities;
	}
	
	

	static void addEntityCity(Entity entity) {
		FromCity.addEntityFromCity(entity);
		ToCity.addEntityToCity(entity);
	}

	static HashSet<Entity> getSingleReservationEntities(String fromCity, String toCity, LocalDate startDate, LocalDate endDate){
		HashSet<Entity> totalSet = new HashSet<Entity>();
		
		totalSet.addAll(getArrivalCityTaxiEntities(toCity, startDate, endDate));
		totalSet.addAll(getArrivalCityHotelEntities(toCity,startDate,endDate));
		totalSet.addAll(getArrivalCityFlightEntities(fromCity, toCity, startDate, endDate));
		totalSet.addAll(getDepartureCityFlightEntities(fromCity, toCity, startDate, endDate));
		 
		return totalSet;
	}
	
	
	static HashSet<Flight> getDepartureCityFlightEntitiesForTravelPackage(String fromCity, String toCity) {
		HashSet<Entity> fromCities = new HashSet<>(FromCity.getCitysEntities(fromCity));
		HashSet<Entity> toCities = new HashSet<>(ToCity.getCitysEntities(toCity));

		fromCities.retainAll(toCities);

		HashSet<Flight> flightEntities = new HashSet<>();

		for (Entity entity : fromCities) {
			if (entity instanceof Flight) {
				flightEntities.add((Flight) entity);
			}
		}

		return flightEntities;
	}

	static HashSet<Flight> getArrivalCityFlightEntitiesForTravelPackage(String fromCity, String toCity) {
		HashSet<Entity> fromCities = new HashSet<>(FromCity.getCitysEntities(toCity));
		HashSet<Entity> toCities = new HashSet<>(ToCity.getCitysEntities(fromCity));
		fromCities.retainAll(toCities);

		HashSet<Flight> flightEntities = new HashSet<>();

		for (Entity entity : fromCities) {
			if (entity instanceof Flight ) {
				flightEntities.add((Flight) entity);
			}
		}

		return flightEntities;
	}

	static HashSet<Hotel> getArrivalCityHotelEntitiesForTravelPackage(String toCity) {
		HashSet<Entity> toCities = new HashSet<>(ToCity.getCitysEntities(toCity));

		HashSet<Hotel> hotelEntities = new HashSet<>();

		for (Entity entity : toCities) {
			if (entity instanceof Hotel) {
				hotelEntities.add((Hotel) entity);
			}
		}

		return hotelEntities;
	}

	static HashSet<Taxi> getArrivalCityTaxiEntitiesForTravelPackage(String toCity) {
		HashSet<Entity> toCities = new HashSet<>(ToCity.getCitysEntities(toCity));

		HashSet<Taxi> taxiEntities = new HashSet<>();

		for (Entity entity : toCities) {
			if (entity instanceof Taxi) {
				taxiEntities.add((Taxi) entity);
			}
		}

		return taxiEntities;
	}

}
