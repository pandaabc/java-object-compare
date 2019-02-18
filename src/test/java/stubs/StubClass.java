package stubs;

import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StubClass {

	Set<Integer> intSet;
	Map<String, List<String>> resMap;
	String test1;
	int int1;
	double double1;
	Long long1;
	boolean boolean1;
	
	public void isAbc() {
		
	}
	
	public String getStringTest() {
		return "stringtest";
	}
	
}
