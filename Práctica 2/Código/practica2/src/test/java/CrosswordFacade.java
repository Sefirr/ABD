import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import es.ucm.abd.practica2.dao.AbstractCrosswordDAO;
import es.ucm.abd.practica2.dao.CrosswordDao;
import es.ucm.abd.practica2.model.Contains;
import es.ucm.abd.practica2.model.Crossword;
import es.ucm.abd.practica2.model.Orientation;
import es.ucm.abd.practica2.model.Word;


public class CrosswordFacade implements AbstractCrosswordFacade<Crossword, Word> {

	@Override
	public Crossword newCrossword(String title, Date date) {
		return new Crossword(title,date);
	}

	@Override
	public Word newDefinition(String sequence, String hint, String[] tags) {
	
		return new Word(sequence,hint,tags);
	}

	@Override
	public void addWordToCrossword(Crossword crossword, Word word, int row,
			int column, Orientation orientation) {
		crossword.getContains().add(new Contains(crossword,word,row,column,orientation)) ;
		
	}

	@Override
	public String getAnswerOfWord(Word word) {
		
		return word.getTitle();
	}

	@Override
	public String[] getTagsOfWord(Word word) {
		List<String> tags =word.getTags();
		String[] ret = new String[tags.size()];
		for(int i=0; i<tags.size();i++)
		   ret[i]=tags.get(i);
		return ret;
	}

	@Override
	public String getHintOfWord(Word word) {
		return word.getWording();
	}

	@Override
	public String getTitleOfCrossword(Crossword crossword) {
		return crossword.getTitle();
	}

	@Override
	public Date getDateOfCrossword(Crossword crossword) {
		return crossword.getDate();
	}

	
	@Override
	public List<Object[]> getWordsOfCrossword(Crossword crossword) {
		List<Contains> c = crossword.getContains();
		List<Object[]> ret= new LinkedList<Object[]>();
		for(Contains x: c)
		{ String word=x.getWord().getTitle();
		  Integer row=x.getX();
		  Integer column=x.getY();
		  Orientation o=x.getOrientation();
		  ret.add(new Object[]{word,row,column,o});	
		}
		return ret;

	}

	@Override
	public AbstractCrosswordDAO<Crossword, Word> createDAO() {
		return new CrosswordDao();
	}


}
