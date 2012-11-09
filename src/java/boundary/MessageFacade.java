/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boundary;

import entities.Message;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ejb.*;
/**
 *
 * @author rwaszcza
 */
@Stateless
public class MessageFacade /*extends AbstractFacade<entities.Message>*/ {
    @PersistenceContext(unitName = "SimpleEE6AppPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }
    
        public void create(Message entity) {
        em.persist(entity);
    }

    public void edit(Message entity) {
        em.merge(entity);
    }

    public void remove(Message entity) {
        em.remove(getEntityManager().merge(entity));
    }

    public Message find(Object id) {
        return em.find(Message.class, id);
    }

    public List<Message> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Message.class));
        return em.createQuery(cq).getResultList();
    }

    public List<Message> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Message.class));
        javax.persistence.Query q = em.createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<Message> rt = cq.from(Message.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        javax.persistence.Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
}
