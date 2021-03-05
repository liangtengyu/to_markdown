package com.liangtengyu.markdown;

import com.liangtengyu.markdown.service.SaveFileService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MarkDownApplication.class)
public class MarkDownApplicationTests {

	@Autowired
	SaveFileService saveFileService;

	@Test
	public void testsave() throws IOException {
		String testsavefile = saveFileService.saveToFile("4:56:19.834 [main] DEBUG org.springframework.test.context.BootstrapUtils - Instantiating CacheAwareContextLoaderDelegate from class [org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate]\n" +
				"14:56:19.839 [main] DEBUG org.springframework.test.context.BootstrapUtils - Instantiating BootstrapContext using constructor [public org.springframework.test.context.support.DefaultBootstrapContext(java.lang.Class,org.springframework.test.context.CacheAwareContextLoaderDelegate)]\n" +
				"14:56:19.853 [main] DEBUG org.springframework.test.context.BootstrapUtils - Instantiating TestContextBootstrapper for test class [com.liangtengyu.markdown.MarkDownApplicationTests] from class [org.springframework.boot.test.context.SpringBootTestContextBootstrapper]\n" +
				"14:56:19.861 [main] INFO org.springframework.boot.test.context.SpringBootTestContextBootstrapper - Neither @ContextConfiguration nor @ContextHierarchy found for test class [com.liangtengyu.markdown.MarkDownApplicationTests], using SpringBootContextLoader\n");
	}

}
