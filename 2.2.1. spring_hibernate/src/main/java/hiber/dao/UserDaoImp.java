package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional
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
    public User findUserByCar(Car car) {
//        User user = null;
//        try {
//            Session session = sessionFactory.openSession();
//            session.beginTransaction();
//            List<User> userList = session
//                    .createQuery("from User user where user.car = :car")
//                    .setParameter("car", car)
//                    .getResultList();
//            session.getTransaction().commit();
//            user = userList.get(0);
//        } catch (RuntimeException e) {
//        }
//        return user;

        TypedQuery<User> query = sessionFactory
                .getCurrentSession()
                .createQuery("from User user where user.car = :car")
                .setParameter("car", car);
        return query.getSingleResult();
    }

}
