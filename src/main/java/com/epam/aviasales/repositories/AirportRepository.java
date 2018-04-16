package com.epam.aviasales.repositories;

import com.epam.aviasales.domain.Airport;
import java.util.List;

public interface AirportRepository {

  List<Airport> getAirports();

  List<Airport> getAirports(int page, int count);

  Airport getByName(String name);

  Airport getById(Long id);
}
