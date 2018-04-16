package com.epam.aviasales.services.impl;

import com.epam.aviasales.domain.Airplane;
import com.epam.aviasales.repositories.AirplaneRepository;
import com.epam.aviasales.repositories.implMock.AirplaneRepositoryImplMock;
import com.epam.aviasales.services.AirplaneService;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AirplaneServiceImpl implements AirplaneService {

  private static final AirplaneService instance = new AirplaneServiceImpl();

  public static AirplaneService getInstance() {
    return instance;
  }

  private static final AirplaneRepository airplaneRepository = AirplaneRepositoryImplMock
      .getInstance();

  @Override
  public List<Airplane> getAirplanes() {
    return airplaneRepository.getAirplanes(1, 20);
  }

  @Override
  public List<Airplane> getAirplanes(int page, int count) {
    return airplaneRepository.getAirplanes(page, count);
  }

  @Override
  public Airplane getAirportByName(String name) {
    return airplaneRepository.getAirplaneByName(name);
  }

  @Override
  public Airplane getAirportById(Long id) {
    return airplaneRepository.getAirplaneById(id);
  }
}
