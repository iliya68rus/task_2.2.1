package hiber.dao;

import hiber.model.Car;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class CarDaoImp implements CarDao {

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
            List<Car> carList = session
                    .createQuery("from Car car where car.model = :model and car.series = :series")
                    .setParameter("model", model)
                    .setParameter("series", series)
                    .getResultList();
            session.getTransaction().commit();
            car = carList.get(0);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return car;
    }
}
