package cities;

import entity.*;

import java.util.HashMap;
import java.util.HashSet;


public final class FromCity implements City {
	private static HashMap<String, HashSet<Entity>> fromCityNameToEntity = new HashMap<>();

	private FromCity() {
		throw new UnsupportedOperationException("This class cannot be instantiated.");
	}

	protected static void addEntityFromCity(Entity entity) {
		try {
			String city = entity.getFromCity();
			if (fromCityNameToEntity.containsKey(city)) {
				fromCityNameToEntity.get(city).add(entity);
			} else {
				HashSet<Entity> entityList = new HashSet<Entity>();
				entityList.add(entity);
				fromCityNameToEntity.put(city, entityList);
			}
		} catch (NullPointerException e) {
			System.out.println("Error: The entity or city name is null.");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("An unexpected error occurred while adding the entity.");
			e.printStackTrace();
		}
	}
	
	public static HashSet<Entity> getCitysEntities(String city) {
		if (fromCityNameToEntity != null) return fromCityNameToEntity.get(city);
		else return null;
	}

	/**
	 * @return the fromCityNameToEntity
	 */
	public static HashSet<String> getFromCities() {
		return new HashSet<>(fromCityNameToEntity.keySet());
	}
	
	
}
