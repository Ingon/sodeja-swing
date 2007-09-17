package org.sodeja.swing.formbuilder;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import junit.framework.TestCase;

public class FormBuilderTest extends TestCase {
	public void testParse() throws IOException {
		SFBLexer lexer = new SFBLexer(new FileReader("test/org/sodeja/swing/formbuilder/test.sfb"));
		List<String> lexicalTokens = lexer.tokenize();
		
		SFBParser parser = new SFBParser();
		List<FormObject> result = parser.parse(lexicalTokens);
		
		assertNotNull(result);
		assertEquals(1, result.size());
		
		FormObject sresult = result.get(0);
		assertEquals(TestBackingObject.class.getName(), sresult.name);
	}
}
