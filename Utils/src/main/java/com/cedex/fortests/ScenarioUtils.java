package com.cedex.fortests;



import jsystem.framework.scenario.JTest;
import jsystem.framework.scenario.ScenarioHelpers;
import com.cedex.jsystem.ReporterLight;
public class ScenarioUtils implements ReporterLight {



	  /**
	   * Function replays current Full Scenario name (with root / parent
	   * scenarios)
	   * 
	   * 
	   * 
	   */
	  public static String getScenarioFullName(JTest test) {
			String scenarioFullname = "";

			// String testHierarchyInPresentableFormat = "/" +
			// "temp-->sce_1-->UTIL: Sleeping for -1 seconds";
			String testHierarchyInPresentableFormat = ScenarioHelpers.getTestHierarchyInPresentableFormat(test);

			testHierarchyInPresentableFormat = testHierarchyInPresentableFormat.replaceAll("-->", "/");
			// testHierarchyInPresentableFormat.replaceAll("For - Loop over \"[0-9|;]+\" setting \"myVar\" parameter",
			// "For-Loop");
			report.report("testHierarchyInPresentableFormat=" + testHierarchyInPresentableFormat);
			String[] testHierarchyInPresentableFormatArr = testHierarchyInPresentableFormat.split("\\/");

			for (int i = 0; i < testHierarchyInPresentableFormatArr.length - 1; i++) {
				  if (testHierarchyInPresentableFormatArr[i].contains("For - Loop over")) {
						scenarioFullname = scenarioFullname + "For-Loop" + "/";
				  } else {
						scenarioFullname = scenarioFullname + testHierarchyInPresentableFormatArr[i] + "/";
				  }
			}
			scenarioFullname = scenarioFullname.substring(0, scenarioFullname.length() - 1);
			report.report("Scenario Full Name : " + scenarioFullname);
			//
			// }
			return scenarioFullname;
	  }
}
