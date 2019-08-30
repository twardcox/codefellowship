package com.fellowship.cox.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DemoApplicationTests {

	@Autowired
	MockMvc mockMvc;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testErrorRoute_containsSomethingwentwrong() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.get("/error"))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().string(
				org.hamcrest.Matchers.containsString("<h1>Something went wrong! </h1>")));
	}

	@Test
	public void testIndexRoute_containsCodefellowship() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.get("/"))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().string(
				org.hamcrest.Matchers.containsString("<h1>Codefellowship</h1>")));
	}

	@Test
	public void testRegisterRoute_containsBiography() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.get("/signup"))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().string(
				org.hamcrest.Matchers.containsString("<label for=\"bio\">Biography:</label>")));
	}

	@Test
	public void testLoginRoute_containsUserName() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.get("/login"))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().string(
				org.hamcrest.Matchers.containsString("<h2 class=\"form-signin-heading\">Please sign in</h2>")));
	}


}
