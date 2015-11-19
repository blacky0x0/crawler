package com.blacky.crawler.service;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * User: blacky
 * Date: 18.11.15
 */

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
})
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public abstract class AbstractServiceTest {

}
