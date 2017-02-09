import es.ucm.abd.practica2.model.Crossword;
import es.ucm.abd.practica2.model.Word;


public class CrosswordTest extends CrosswordTestBase<Crossword, Word>{

	@Override
	protected AbstractCrosswordFacade<Crossword, Word> buildFacade() {
		return new CrosswordFacade();
	}



}
