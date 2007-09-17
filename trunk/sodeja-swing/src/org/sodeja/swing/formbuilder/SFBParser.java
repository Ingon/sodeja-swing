package org.sodeja.swing.formbuilder;

import static org.sodeja.parsec.ParsecUtils.apply;
import static org.sodeja.parsec.ParsecUtils.oneOrMoreSep;
import static org.sodeja.parsec.ParsecUtils.thenParser;
import static org.sodeja.parsec.ParsecUtils.thenParser3;
import static org.sodeja.parsec.ParsecUtils.thenParser4;
import static org.sodeja.parsec.ParsecUtils.zeroOrMore;
import static org.sodeja.parsec.standart.StandartParsers.alphaDigitsUnderscore;
import static org.sodeja.parsec.standart.StandartParsers.literal;

import java.util.List;

import org.sodeja.collections.ListUtils;
import org.sodeja.functional.Function1;
import org.sodeja.functional.Function2;
import org.sodeja.functional.Function3;
import org.sodeja.functional.Function4;
import org.sodeja.functional.Pair;
import org.sodeja.parsec.Parser;

class SFBParser {
	
	private String currentPackage;
	
	private Parser<String, String> TEXT = alphaDigitsUnderscore("TEXT");

	private Parser<String, FormObjectField> FIELD = thenParser4("FIELD", TEXT, literal(":"), TEXT, literal(";"), 
		new Function4<FormObjectField, String, String, String, String>() {
			@Override
			public FormObjectField execute(String p1, String p2, String p3, String p4) {
				return new FormObjectField(p1, p3);
			}});
	
	private Parser<String, List<FormObjectField>> FIELDS = zeroOrMore("FIELDS", FIELD);
	
	private Parser<String, FormObject> OBJECT = thenParser4("OBJECT", TEXT, literal("{"), FIELDS, literal("}"), 
		new Function4<FormObject, String, String, List<FormObjectField>, String>() {
			@Override
			public FormObject execute(String p1, String p2, List<FormObjectField> p3, String p4) {
				return new FormObject(currentPackage + p1, p3);
			}});
	
	private Parser<String, List<FormObject>> OBJECTS = zeroOrMore("OBJECTS", OBJECT);
	
	private Parser<String, List<String>> PACKAGE_NAME = oneOrMoreSep("PACKAGE_NAME", TEXT, literal("."));
	
	private Parser<String, String> PACKAGE = thenParser3("PACKAGE", literal("package"), PACKAGE_NAME, literal(";"), 
		new Function3<String, String, List<String>, String>() {
			@Override
			public String execute(String p1, List<String> p2, String p3) {
				currentPackage = ListUtils.foldl(p2, "", new Function2<String, String, String>() {
					@Override
					public String execute(String p1, String p2) {
						return p1 + p2 + ".";
					}});
				return currentPackage;
			}});
	
	private Parser<String, List<FormObject>> PACKAGE_OBJECTS = thenParser("PACKAGE_OBJECTS", PACKAGE, OBJECTS, 
		new Function2<List<FormObject>, String, List<FormObject>>() {
			@Override
			public List<FormObject> execute(String p1, List<FormObject> p2) {
				return p2;
			}});
	
	private Parser<String, List<FormObject>> ROOT = apply("ROOT_APPLY", zeroOrMore("ROOT", PACKAGE_OBJECTS), 
		new Function1<List<FormObject>, List<List<FormObject>>>() {
			@Override
			public List<FormObject> execute(List<List<FormObject>> p) {
				return ListUtils.flattern(p);
			}});
	
	public List<FormObject> parse(List<String> tokens) {
		List<Pair<List<FormObject>, List<String>>> parseResults = ROOT.execute(tokens);
		for(Pair<List<FormObject>, List<String>> result : parseResults) {
			if(result.second.isEmpty()) {
				return result.first;
			}
		}
		
		throw new RuntimeException("Unable to parse");
	}
}
