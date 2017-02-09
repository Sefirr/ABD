package es.ucm.abd.practica2.dao;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import es.ucm.abd.practica2.model.Crossword;
import es.ucm.abd.practica2.model.Word;

public class CrosswordDao implements AbstractCrosswordDAO<Crossword, Word> {

	private SessionFactory sf;

	public CrosswordDao() {
	}

	@Override
	public void setSessionFactory(SessionFactory f) {
		sf = f;

	}

	@Override
	public int insertCrossword(Crossword crossword) {
		Session session = sf.openSession();
		Transaction tr = session.beginTransaction();
		session.save(crossword);
		int id = crossword.getCrosswordId();
		tr.commit();
		session.close();
		return id;
	}

	@Override
	public void insertWord(Word word) {
		Session session = sf.openSession();
		Transaction tr = session.beginTransaction();
		session.save(word);
		tr.commit();
		session.close();

	}

	@Override
	public Crossword findCrosswordById(int id) {
		Crossword crossword = new Crossword();
		Session session = sf.openSession();
		session.load(crossword, id);
		session.close();
		return crossword;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getCrosswordData(String str) {
		Session session = sf.openSession();
		List<Object[]> ret = new LinkedList<Object[]>();
		String queryString = "Select c.crosswordId,c.title,c.date, COUNT(w)"
				+ " FROM Crossword AS c LEFT JOIN c.contains AS w "
				+ "WHERE c.title LIKE :title GROUP BY c.title";
		Query query = session.createQuery(queryString);
		query.setString("title", "%" + str + "%");
		ret = query.list();
		session.close();
		return ret;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Word> findWordsByTags(String[] tags) {
		Session session = sf.openSession();
		List<Word> ret = new LinkedList<Word>();
		String queryString = "";
		Query query;
		queryString = "FROM Word";
		if (tags.length != 0) {

			String[] set = new String[tags.length];
			int h = 0;
			for (String i : tags) {
				set[h] = "'" + i + "' MEMBER OF w.tags ";
				h++;
			}
			String finalset = StringUtils.join(set, " AND ");
			queryString += " as w WHERE " + finalset;

		}
		query = session.createQuery(queryString);
		ret = query.list();
		session.close();
		return ret;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Word> getMatchingWords(CharConstraint[] constraints) {
		List<Word> ret = new LinkedList<Word>();
		Session session = sf.openSession();
		String queryString = "FROM Word";
		Query query;

		if (constraints.length != 0) {
			String[] set = new String[constraints.length];
			for (int i = 0; i < constraints.length; i++) {
				set[i] = "SUBSTRING(w.title," + (constraints[i].getPosition())
						+ ",1) = " + "'" + constraints[i].getCharacter() + "'";
			}

			String finalset = StringUtils.join(set, " OR ");
			queryString += " as w WHERE " + finalset;

		}
		query = session.createQuery(queryString);
		ret = query.list();

		for (int i = 0; i < ret.size(); i++) {
			for (int j = 0; j < (constraints.length); j++) {
				Word w = ret.get(i);
				char[] titulo = w.getTitle().toCharArray();
				if (titulo.length - 1 == constraints[j].getPosition() - 2) {
					ret.remove(i);

				}
			}
		}

		session.close();
		return ret;
	}

}