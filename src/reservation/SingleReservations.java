package reservation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import entity.*;

public class SingleReservations {

	private static Map<String, Integer> singleFlightReservations = new HashMap<>();
	private static Map<String, Integer> singleTaxiReservations = new HashMap<>();
	private static Map<String, Integer> singleHotelReservations = new HashMap<>();

	private Entity entity;
	private LocalDate startDate;
	private LocalDate endDate;

	public SingleReservations(Entity entity, String startDate, String endDate) {
		this.entity = entity;
		if (startDate != null) {
			this.startDate = LocalDate.parse(startDate);
		}
		if (endDate != null) {
			this.endDate = LocalDate.parse(endDate);

		}
	}

	public static Map<String, Integer> getSingleFlightReservations() {
		return singleFlightReservations;
	}

	public static Map<String, Integer> getSingleTaxiReservations() {
		return singleTaxiReservations;
	}

	public static Map<String, Integer> getSingleHotelReservations() {
		return singleHotelReservations;
	}

	public Entity getEntity() {
		return entity;
	}

	public static void addReservationToSingleReservations(Reservation reservation) {
		ArrayList<Entity> entities = reservation.getEntities();
		boolean isFlight = true;
		boolean isTaxi = true;

		for (final Entity entity : entities) {

			if (entity instanceof Flight) {
				if (isFlight) {
					SingleReservations singleReservation = new SingleReservations(entity,
							reservation.getStartDate().toString(), null);
					addReservationToSystem(singleReservation);
					isFlight = false;
				}
				else {
					SingleReservations singleReservation = new SingleReservations(entity,
							null, reservation.getEndDate().toString());
					addReservationToSystem(singleReservation);
				}

			}
			if (entity instanceof Taxi){
				if (isTaxi) {
					SingleReservations singleReservation = new SingleReservations(entity,
							reservation.getStartDate().toString(), null);
					addReservationToSystem(singleReservation);
					isTaxi = false;
				}
				else {
					SingleReservations singleReservation = new SingleReservations(entity,
							null, reservation.getEndDate().toString());
					addReservationToSystem(singleReservation);
				}
			}
			if (entity instanceof Hotel) {
				SingleReservations singleReservation = new SingleReservations(entity,
						reservation.getStartDate().toString(), reservation.getEndDate().toString());
				addReservationToSystem(singleReservation);
			}

		}
	}

	public static void deleteTravelPackageFromSingleReservations(Reservation reservation) {
		ArrayList<Entity> entities = reservation.getEntities();
		boolean isFlight = true;
		boolean isTaxi = true;

		for (final Entity entity : entities) {

			if (entity instanceof Flight) {
				if (isFlight) {
					SingleReservations singleReservation = new SingleReservations(entity,
							reservation.getStartDate().toString(), null);
					deleteReservationToSystem(singleReservation);
					isFlight = false;
				}
				else {
					SingleReservations singleReservation = new SingleReservations(entity,
							null, reservation.getEndDate().toString());
					deleteReservationToSystem(singleReservation);
				}

			}
			if (entity instanceof Taxi){
				if (isTaxi) {
					SingleReservations singleReservation = new SingleReservations(entity,
							reservation.getStartDate().toString(), null);
					deleteReservationToSystem(singleReservation);
					isTaxi = false;
				}
				else {
					SingleReservations singleReservation = new SingleReservations(entity,
							null, reservation.getEndDate().toString());
					deleteReservationToSystem(singleReservation);
				}
			}
			if (entity instanceof Hotel) {
				SingleReservations singleReservation = new SingleReservations(entity,
						reservation.getStartDate().toString(), reservation.getEndDate().toString());
				deleteReservationToSystem(singleReservation);
			}

		}
	}

	private static void addReservationToSystem(SingleReservations singleReservation) {
		try {
			if (singleReservation.entity instanceof Flight) {
				addToFlightReservations(singleReservation);
			} else if (singleReservation.entity instanceof Taxi) {
				addToTaxiReservations(singleReservation);
			} else if (singleReservation.entity instanceof Hotel) {
				addToHotelReservations(singleReservation);
			} else {
				throw new IllegalArgumentException("Entity is unknown in SingleReservation");
			}
		} catch (IllegalArgumentException e) {
			System.err.println("Entity is unknown in SingleReservation");
		}
	}

	private static void addToFlightReservations(SingleReservations singleReservation) {
		if (singleReservation.endDate == null) {
			String flightInfo = singleReservation.entity.toString() + " " + singleReservation.startDate;
			singleFlightReservations.put(flightInfo, singleFlightReservations.getOrDefault(flightInfo, 0) + 1);

		}
		else if (singleReservation.startDate == null){
			String returnflightInfo = singleReservation.entity.toString() + " " + singleReservation.endDate;
			singleFlightReservations.put(returnflightInfo, singleFlightReservations.getOrDefault(returnflightInfo, 0) + 1);
		}
		else {
			System.err.println("invaild flight in SingleReservations");
		}
	}

	private static void addToTaxiReservations(SingleReservations singleReservation) {
		if (singleReservation.endDate == null) {
			String taxiInfo = singleReservation.entity.toString() + " " + singleReservation.startDate;
			singleTaxiReservations.put(taxiInfo, singleTaxiReservations.getOrDefault(taxiInfo, 0) + 1);

		}
		else if (singleReservation.startDate == null){
			String returnTaxiInfo = singleReservation.entity.toString() + " " + singleReservation.endDate;
			singleTaxiReservations.put(returnTaxiInfo, singleTaxiReservations.getOrDefault(returnTaxiInfo, 0) + 1);
		}
		else {
			System.err.println("invaild taxi in SingleReservations");
		}
	}

	private static void addToHotelReservations(SingleReservations singleReservation) {
		LocalDate currentDate = singleReservation.startDate;

		while (!currentDate.isAfter(singleReservation.endDate)) {
			String hotelInfo = singleReservation.entity.toString() + " " + currentDate.toString();
			singleHotelReservations.put(hotelInfo, singleHotelReservations.getOrDefault(hotelInfo, 0) + 1);
			currentDate = currentDate.plusDays(1);
		}
	}

	private static void deleteReservationToSystem(SingleReservations singleReservation) {
		try {
			if (singleReservation.entity instanceof Flight) {
				deleteToFlightReservations(singleReservation);
			} else if (singleReservation.entity instanceof Taxi) {
				deleteToTaxiReservations(singleReservation);
			} else if (singleReservation.entity instanceof Hotel) {
				deleteToHotelReservations(singleReservation);
			} else {
				throw new IllegalArgumentException("Entity is unknown in SingleReservation");
			}
		} catch (IllegalArgumentException e) {
			System.err.println("Entity is unknown in SingleReservation");
		}
	}

	private static void deleteToFlightReservations(SingleReservations singleReservation) {
		if (singleReservation.endDate == null) {
			String flightInfo = singleReservation.entity.toString() + " " + singleReservation.startDate;
			singleFlightReservations.put(flightInfo, singleFlightReservations.getOrDefault(flightInfo, 0) - 1);
			
			if (singleFlightReservations.get(flightInfo) <= 0) {
				singleFlightReservations.put(flightInfo, 0);
			}

		}
		else if (singleReservation.startDate == null){
			String returnflightInfo = singleReservation.entity.toString() + " " + singleReservation.endDate;
			singleFlightReservations.put(returnflightInfo, singleFlightReservations.getOrDefault(returnflightInfo, 0) - 1);
			
			if (singleFlightReservations.get(returnflightInfo) <= 0) {
				singleFlightReservations.put(returnflightInfo, 0);
			}
		}
		else {
			System.err.println("invaild flight in SingleReservations");
		}
	}

	private static void deleteToTaxiReservations(SingleReservations singleReservation) {
		if (singleReservation.endDate == null) {
			String taxiInfo = singleReservation.entity.toString() + " " + singleReservation.startDate;
			singleTaxiReservations.put(taxiInfo, singleTaxiReservations.getOrDefault(taxiInfo, 0) - 1);

			if (singleTaxiReservations.get(taxiInfo) <= 0) {
				singleTaxiReservations.put(taxiInfo, 0);
			}

		}
		else if (singleReservation.startDate == null){
			String returntaxiInfo = singleReservation.entity.toString() + " " + singleReservation.endDate;
			singleTaxiReservations.put(returntaxiInfo, singleTaxiReservations.getOrDefault(returntaxiInfo, 0) - 1);
			
			if (singleTaxiReservations.get(returntaxiInfo) <= 0) {
				singleTaxiReservations.put(returntaxiInfo, 0);
			}
		}
		else {
			System.err.println("invaild taxi in SingleReservations");
		}

	}

	private static void deleteToHotelReservations(SingleReservations singleReservation) {
		LocalDate currentDate = singleReservation.startDate;

		while (!currentDate.isAfter(singleReservation.endDate)) {
			String hotelInfo = singleReservation.entity.toString() + " " + currentDate.toString();
			singleHotelReservations.put(hotelInfo, singleHotelReservations.getOrDefault(hotelInfo, 0) - 1);
			currentDate = currentDate.plusDays(1);

			if (singleHotelReservations.get(hotelInfo) <= 0) {
				singleHotelReservations.put(hotelInfo, 0);
			}
		}
	}

	public static int getNumberAvailiblity(Entity entity, LocalDate startDate, LocalDate endDate) {
		int minAvailiblity = Integer.MAX_VALUE;
		LocalDate currentDate = startDate;

		while (!currentDate.isAfter(endDate)) {
			String info = entity.toString() + " " + currentDate;

			if (entity instanceof Hotel) {
				minAvailiblity = Math.min(minAvailiblity,
						entity.getNumberAvailability() - singleHotelReservations.getOrDefault(info, 0));
			}

			else if (entity instanceof Taxi) {
				minAvailiblity = entity.getNumberAvailability() - singleTaxiReservations.getOrDefault(info, 0);
			}

			else if (entity instanceof Flight) {
				minAvailiblity = Math.min(minAvailiblity,
						entity.getNumberAvailability() - singleFlightReservations.getOrDefault(info, 0));
			} else
				throw new NoSuchElementException("No such entity: " + info);
			currentDate = currentDate.plusDays(1);
		}

		return minAvailiblity;

	}

	public static int getNumberAvailiblityForEndDate(Entity entity, LocalDate endDate) {
		LocalDate currentDate = endDate;

		String info = entity.toString() + " " + currentDate;

		if (entity instanceof Hotel) {
			return entity.getNumberAvailability() - singleHotelReservations.getOrDefault(info, 0);
		}

		else if (entity instanceof Taxi) {
			return entity.getNumberAvailability() - singleTaxiReservations.getOrDefault(info, 0);
		}

		else if (entity instanceof Flight) {
			return entity.getNumberAvailability() - singleFlightReservations.getOrDefault(info, 0);
		} else
			throw new NoSuchElementException("No such entity: " + info);

	}

	public static int getNumberAvailiblityForStartDate(Entity entity, LocalDate startDate) {
		LocalDate currentDate = startDate;

		String info = entity.toString() + " " + currentDate;

		if (entity instanceof Hotel) {
			return entity.getNumberAvailability() - singleHotelReservations.getOrDefault(info, 0);
		}

		else if (entity instanceof Taxi) {
			return entity.getNumberAvailability() - singleTaxiReservations.getOrDefault(info, 0);
		}

		else if (entity instanceof Flight) {
			return entity.getNumberAvailability() - singleFlightReservations.getOrDefault(info, 0);
		} else
			throw new NoSuchElementException("No such entity: " + info);

	}
}
