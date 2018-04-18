package com.epam.aviasales.repositories.impl;

import com.epam.aviasales.domain.Flight;
import com.epam.aviasales.repositories.FlightRepository;
import com.epam.aviasales.util.HibernateUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Query;
import org.hibernate.Session;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FlightRepositoryImpl implements FlightRepository {

  private static volatile FlightRepository instance;

  public static FlightRepository getInstance() {
    FlightRepository localInstance = instance;
    if (localInstance == null) {
      synchronized (FlightRepositoryImpl.class) {
        localInstance = instance;
        if (localInstance == null) {
          instance = localInstance = new FlightRepositoryImpl();
        }
      }
    }

    return localInstance;
  }

  @Override
  public void addFlight(Flight flight) {
    Session session = HibernateUtil.getSessionFactory().openSession();

    session.beginTransaction();

    session.save(flight);

    session.getTransaction().commit();
    session.close();
  }

  @Override
  public boolean isExist(Long id) {
    return getFlightById(id) != null;
  }

  @Override
  public void deleteFlight(Long id) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();

    session.delete(getFlightById(id));

    session.getTransaction().commit();
    session.close();
  }

  @Override
  public Flight getFlightById(Long id) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();

    Query query = session.createQuery("from Flight where id=:id");
    query.setParameter("id", id);
    List list = query.list();

    session.getTransaction().commit();
    session.close();

    return list.size() > 0 ? (Flight) list.get(0) : null;
  }

  @Override
  public List<Flight> getFlights(Long fromId, Long toId) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();

    Query query = session.createQuery("from Flight where id>=:from and id<=:to");
    query.setParameter("from", fromId);
    query.setParameter("to", toId);
    List list = query.list();

    session.getTransaction().commit();
    session.close();

    return (List<Flight>) list;
  }

  @Override
  public List<Flight> getFlights(Long airportIdFrom, Long airportIdTo, LocalDate date) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();

    Query query = session.createQuery("from Flight where fromAirport.id=:fromAirport and "
        + "toAirport.id=:toAirport and departureTime >= :minDepartureTime and departureTime <= :maxDepartureTime");
    query.setParameter("fromAirport", airportIdFrom);
    query.setParameter("toAirport", airportIdTo);
    query.setParameter("minDepartureTime", LocalDateTime.of(date, LocalTime.of(0, 0)));
    query.setParameter("maxDepartureTime", LocalDateTime.of(date, LocalTime.of(23, 59)));
    List list = query.list();
    List<Flight> flights = (List<Flight>) list;

    session.getTransaction().commit();
    session.close();

    return (List<Flight>) list;
  }

  @Override
  public void updateFlight(Flight flight) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();
    Query query =
        session.createQuery(
            "update Flight set freeSeatEconomy = :economySeats, freeSeatBusiness = :businessSeats where id = :id");
    query.setParameter("economySeats", flight.getFreeSeatEconomy());
    query.setParameter("businessSeats", flight.getFreeSeatBusiness());
    query.setParameter("id", flight.getId());
    query.executeUpdate();

    session.getTransaction().commit();
    session.close();
  }

  @Override
  public List<Flight> getFlights() {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();

    Query query = session.createQuery("from Flight");
    List list = query.list();

    session.getTransaction().commit();
    session.close();

    return (List<Flight>) list;
  }

  @Override
  public List<Flight> getFlightsPage(int page, int count) {
    throw new UnsupportedOperationException();
  }
}
