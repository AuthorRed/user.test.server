package cn.author.tradewebtest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.task.TaskExecutor;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.scheduling.annotation.Async;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TradeWebTestApplicationTests {
	@Test
	public void testUUID() {
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
	}
}

