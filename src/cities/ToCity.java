package cities;

import entity.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public final class ToCity implements City{
	private static Map<String, HashSet<Entity>> toCityNameToEntity = new HashMap<>();

	private ToCity() {
		throw new UnsupportedOperationException("This class cannot be instantiated.");
	}

	protected static void addEntityToCity(Entity entity) {
		try {
			String city = entity.getToCity();
			if (toCityNameToEntity.containsKey(city)) {
				toCityNameToEntity.get(city).add(entity);
			} else {
				HashSet<Entity> entityList = new HashSet<Entity>();
				entityList.add(entity);
				toCityNameToEntity.put(city, entityList);
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
		if (toCityNameToEntity != null) return toCityNameToEntity.get(city);
		else return null;
	}

	public static HashSet<String> getToCities() {
		return new HashSet<>(toCityNameToEntity.keySet());
	}
}
