package pliesveld.bridge;

import org.apache.wicket.util.tester.WicketTester;
import org.apache.wicket.util.tester.FormTester;
import org.junit.Before;
import org.junit.Test;
import pliesveld.bridge.web.HomePage;

// TODO: add testing models https://ci.apache.org/projects/wicket/guide/6.x/guide/testing.html#testing_1

/**
 * Simple test using the WicketTester
 */
public class TestHomePage
{
	private WicketTester tester;

	@Before
	public void setUp()
	{
		tester = new WicketTester(new WicketApplication());
	}

	/*
	 * Form compnent-ids:
	 * 	seat : select
	 * 	suit : select
	 * 	penalty : select
	 * 	level : input type=range
	 * 	tricks : input type=range
	 */
	@Test
	public void homepageRendersSuccessfully()
	{
		//start and render the test page
		tester.startPage(HomePage.class);

		//assert rendered page class
		tester.assertRenderedPage(HomePage.class);
	}


	@Test
	public void homepageFormSubmittedSuccessfully()
	{
		//start and render the test page
		tester.startPage(HomePage.class);

		//assert rendered page class
		tester.assertRenderedPage(HomePage.class);


		tester.assertLabel("score-panel:score_team1","0");
		tester.assertLabel("score-panel:score_team2","0");

		//create form tester
		FormTester formTester = tester.newFormTester("form-panel:auctionForm",false);

		formTester.select("seat",0);
		formTester.select("suit",0);
		formTester.select("penalty",0);
		formTester.setValue("level","1");
		formTester.setValue("tricks","0");

		formTester.submit();

	}

	@Test
	public void homepageComponentStatusSuccessfully()
	{
		//start and render the test page
		tester.startPage(HomePage.class);

		// available: WicketTester.assertEnabled() / assertDisabled()  - is component enabled or not
		// visible:   WicketTester.assertVisable() / assertInvisible() - is component visibility
		// required:  WicketTester.assertRequired() - checks if a form component is required
		//
		tester.assertEnabled("auctionForm:seat");
		tester.assertEnabled("auctionForm:suit");
		tester.assertEnabled("auctionForm:penalty");
		tester.assertEnabled("auctionForm:level");
		tester.assertEnabled("auctionForm:tricks");

		tester.assertVisible("auctionForm:seat");
		tester.assertVisible("auctionForm:suit");
		tester.assertVisible("auctionForm:penalty");
		tester.assertVisible("auctionForm:level");
		tester.assertVisible("auctionForm:tricks");

		tester.assertRequired("auctionForm:seat");
		tester.assertRequired("auctionForm:suit");
		tester.assertRequired("auctionForm:penalty");
		tester.assertRequired("auctionForm:level");
		tester.assertRequired("auctionForm:tricks");


	}
}
