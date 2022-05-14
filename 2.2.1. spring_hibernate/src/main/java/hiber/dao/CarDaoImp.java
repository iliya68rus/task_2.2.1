package hiber.dao;

import hiber.model.Car;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.TypedQuery;
import java.util.List;

public class CarDaoImp implements CarDao{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(Car car) {
        sessionFactory.getCurrentSession().save(car);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Car> listCars() {
        TypedQuery<Car> query = sessionFactory.getCurrentSession().createQuery("from Car");
        return query.getResultList();
    }

    @Override
    public Car findCarByModelAndNumber(String model, int series) {
        Car car = null;
        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            car = (Car) session
                    .createQuery("from Car car where car.model = :model and car.series = :series")
                    .setParameter("model", model)
                    .setParameter("series", series)
                    .getSingleResult();
            session.getTransaction().commit();
        } catch (RuntimeException e) {
        }
        return car;
    }
}
