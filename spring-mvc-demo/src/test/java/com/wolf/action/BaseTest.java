package com.wolf.action;

import com.wolf.springmvc.response.MessageResponse;
import com.wolf.springmvc.web.WebMvcConfigurationAdapter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Created by wolf on 17/1/2.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebMvcConfigurationAdapter.class})
@WebAppConfiguration("src/main/resources")
public class BaseTest {
    MockMvc mockMvc;

    @Autowired
    WebApplicationContext context;

    @Autowired
    MockHttpSession session;

    @Autowired
    MockHttpServletRequest request;

    @Before
    public void setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    /**
     * 测试返回json格式
     * @throws Exception
     */
    @Test
    public void testJsonConverter() throws Exception{
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/iResponse"))
                                       .andExpect(MockMvcResultMatchers.status().isOk());
        MvcResult mvcResult = actions.andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/anno/normalPage"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("page"))
                .andExpect(forwardedUrl("/WEB-INF/classes/views/page.jsp"))
                .andExpect(model().attribute("testMsg", "junit测试信息"));
    }
}
