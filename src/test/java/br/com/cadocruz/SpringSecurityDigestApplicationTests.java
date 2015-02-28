package br.com.cadocruz;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.cadocruz.config.SpringSecurityDigestApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringSecurityDigestApplication.class)
@WebAppConfiguration
public class SpringSecurityDigestApplicationTests {

	@Test
	public void contextLoads() {
	}

}
