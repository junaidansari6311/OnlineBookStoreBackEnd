package com.codebrewers.onlinebookstore;

import com.codebrewers.onlinebookstore.properties.FileProperties;
import com.codebrewers.onlinebookstore.repository.ICouponRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class OnlinebookstoreApplicationTests {

	@MockBean
	FileProperties fileProperties;


	@Test
	void contextLoads() {
	}

}
