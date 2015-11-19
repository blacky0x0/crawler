package com.blacky.crawler.web;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.annotation.PostConstruct;

/**
 * User: blacky
 * Date: 18.11.15
 */
@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-mvc.xml"
})
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class AbstractRestTest {
    private static final CharacterEncodingFilter CHARACTER_ENCODING_FILTER = new CharacterEncodingFilter();

    static
    {
        CHARACTER_ENCODING_FILTER.setEncoding("UTF-8");
        CHARACTER_ENCODING_FILTER.setForceEncoding(true);
    }

    @Autowired
    private WebApplicationContext context;

    protected MockMvc mockMvc;

    @PostConstruct
    void postConstruct() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilter(CHARACTER_ENCODING_FILTER)
                .build();
    }

}