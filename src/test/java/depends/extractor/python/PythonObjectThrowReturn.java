package depends.extractor.python;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import depends.deptypes.DependencyType;
import depends.entity.FunctionEntity;

public class PythonObjectThrowReturn extends PythonParserTest {
    @Before
    public void setUp() {
    	super.init();
    }

	
	@Test
	public void could_parse_throws() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/python-code-examples/throw_return.py",
	    	    };
	    
	    for (String src:srcs) {
		    PythonFileParser parser = createParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
	    FunctionEntity func = (FunctionEntity)repo.getEntity("t1");
	    this.assertContainsRelation(func, DependencyType.THROW, "Bar");
	}
	
	
	@Test
	public void could_parse_return() throws IOException {
		String[] srcs = new String[] {
	    		"./src/test/resources/python-code-examples/throw_return.py",
	    	    };
	    
	    for (String src:srcs) {
		    PythonFileParser parser = createParser(src);
		    parser.parse();
	    }
	    inferer.resolveAllBindings();
	    FunctionEntity func = (FunctionEntity)repo.getEntity("t2");
	    this.assertContainsRelation(func, DependencyType.RETURN, "Bar");
	}


}
