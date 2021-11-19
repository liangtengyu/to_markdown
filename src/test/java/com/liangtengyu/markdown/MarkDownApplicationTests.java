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
	public void testsave() throws IOException { }

}
