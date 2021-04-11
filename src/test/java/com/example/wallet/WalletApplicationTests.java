package com.example.wallet;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.wallet.controller.WalletController;
import com.example.wallet.service.WalletService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(WalletController.class)
public class WalletApplicationTests {

    

	@Autowired
    private MockMvc mockMvc;

	/*
	 * @Autowired private WebApplicationContext context;
	 */
    @MockBean // auto inject helloRepository
    private WalletService walletService;

	/*
	 * @BeforeEach public void setUp() {
	 * mockMvc=MockMvcBuilders.webAppContextSetup(context).build() ; }
	 */
    
    @Test    
    public void testGetCoins()throws Exception{ 
    	
    ObjectMapper mapper = new ObjectMapper();
   
    List<Integer> lst=Arrays.asList(new Integer[] {1,2,3,4,5});     
    when(walletService.getWalletBalance()).thenReturn(lst);
    
    MvcResult mvcresult=mockMvc.perform(get("/coins").contentType(
    MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();
    String result=mvcresult.getResponse().getContentAsString();
    ArrayList<Integer> Arralst= mapper.readValue(result, ArrayList.class);
  //  Assert.assertEquals(5,Arralst.size()); 
    }
    

    @Test    
    public void testIntializeWallet()throws Exception{ 
   
    List<Integer> lst=Arrays.asList(new Integer[] {1,2,3,4,5});     
    when(walletService.updateWallet(Mockito.anyList())).thenReturn("success");
    
    MvcResult mvcresult=mockMvc.perform(post("/initalize").content(lst.toString()).contentType(
    MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();
    String result=mvcresult.getResponse().getContentAsString();
   
   // Assert.assertEquals("success",result); 
    }
    
    
    @Test    
    public void testpayMoney()throws Exception{        
      
    when(walletService.payMoney(Mockito.anyInt())).thenReturn(Arrays.asList(new Integer[] {1,2,3,4,5}));
  
    MvcResult mvcresult=mockMvc.perform(post("/payMoney").content(String.valueOf(5)).contentType(
    MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();
    String result=mvcresult.getResponse().getContentAsString();
   
   // Assert.assertEquals("Success",result); 
    }
}