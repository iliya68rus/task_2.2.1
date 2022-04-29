package hiber.dao;

import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    public User findUserByCarModelAndNumber(String model, int series) {
        User user = null;
        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            user = (User) session
                    .createQuery("from User user where user.car.model = :model and user.car.series = :series")
                    .setParameter("model", model)
                    .setParameter("series", series)
                    .getSingleResult();
            session.getTransaction().commit();
        } catch (RuntimeException e) {
        }
        return user;
    }

}
