package index;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

public class PostDao {

	// Injected database connection:
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("post.odb");
    private EntityManager em = emf.createEntityManager();
    public static PostDao instance;
    
    public static PostDao getInstance() {
    	if (instance == null) {
    		instance = new PostDao();
    	}
    	
    	return instance;
    }
 
    public void persist(Post post) {
        em.persist(post);
        System.out.println(String.format("Saved %s", post.word));
    }
    
    public List<Post> getAllPosts() {
        TypedQuery<Post> query = em.createQuery(
            "SELECT g FROM Post g ORDER BY g.id", Post.class);
        return query.getResultList();
    }
    
}
