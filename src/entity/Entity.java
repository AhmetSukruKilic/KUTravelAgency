package entity;

import java.time.LocalDate;

public interface Entity {
	String getFromCity();
	String getToCity();
	int getNumberAvailability();
	String getEntityShortUniqueInfo();
	String toString();
	String getEntityShortUniqueInfoWithPlaces();
	String getEntityForOption(LocalDate startDate, LocalDate endDate);
	String getEntityForOptionGo(LocalDate startDate);
	String getEntityForOptionReturn(LocalDate endDate);
	String getEntityForOptionGoBack(LocalDate startDate, LocalDate endDate);

}
