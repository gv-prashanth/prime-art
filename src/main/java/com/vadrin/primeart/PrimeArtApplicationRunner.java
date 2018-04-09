package com.vadrin.primeart;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class PrimeArtApplicationRunner implements ApplicationRunner {

	@Autowired
	PrimalityTester primalityTester;

	@Autowired
	WebDriver driver;

	@Value("${prime-art.alpertron.url}")
	private String alpertronUrl;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println("Begin processing the image");
		driver.get(alpertronUrl);
		int incremental = 2;
		while (incremental < 10) {
			String input = "10010107777777777777707777777777777777777707777777777777777777771010010777777777777702077777777777777777702077777777777777777777100100077777777777777077777777777777777777077777777777777777777777777777777777777777050777777777777777777050777777777777777777777777777777777777777055107777777777777777015507777777777777777777777777777777777777705550777777777777777705550777777777777777777777777777777777777770000377777770077777773000077777777777777777777777777777777777770433307777770880777777033340777777777777777777777777777877777777700000777777100177777700000777777777777777777777787770777777777770999077777105501777770999077777777777077777777777777017777777777099907777105555017777099907777777777107777777777777704777777777709990771705300350717710000777777777740777777777777701077777777870008005005111111500500800077777777770107777777777770107777777777109900051100000011500099907777777777010777777777777724777777777700000111505111160511100100777777777732777777777777770077777877770993015001150051100510399077777777770077777777777777077877777771099300011110880111100039901777787777707777777777777704777777770000000111111500511111200000007877777740777877770007005601100110550599011111111111111110995055011001106500700070656065555005500555009001111116005111111009005550055005555606560555555555555555555500900111508898880511100900555555555555555555500000055000000000000909011190888888091110909000000000000550000001111105501111111111090901110888888880111090901111111111055011111951110550111599511109090111800800800811109090111599511105501115908511055011680085110303011188888888881110303011590085120650115800801100001108008011053501118888888889111053501108008011000011080080110550110800801090000111888888888811100009010801801105501108008021055011080080110555011100003300001110565011080080110550110800801105501108008011059503400000000099933059501108008011055011080333110550113333331109330555565555555555503390113333331205501133300000055000000000000959099999999999999990959000000000000550000001111505505111111111094901111112111111111094901111111115055051111111110550111111111109290111900010000911109290111111111105501111211111000011111121110959012108889888801110959011111111110000111118511105501115891111095901110800880080111195901111985111055011158005110650115000811109290111008888880011109290111800051105501150008311055011380801100559011101888888001110955001108083110550113800831105501138080110555550110088888800110555550110808311056011390003110561113000011055555011008888880011055555011000031105501130011110555501111111105555501100888888001105555501111111105555011121111055550111111110555550110088888800110555550111111110550000000000005555000000000055555000008888880000055555000000000055012039"
					+ Integer.toString(incremental);
			String result = primalityTester.test(input);
			System.out.println(result + " :" + input);
			incremental++;
		}
		driver.close();
		driver.quit();
		System.out.println("Finished processing the image");
	}

}