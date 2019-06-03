package org.nhc;

import org.avaje.agentloader.AgentLoader;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import org.nhc.domain.BookNames;
import org.nhc.domain.Verses;

import java.util.List;

/** 
* Bible Tester. 
* 
* @author <Authors name> 
* @since <pre>六月 1, 2019</pre> 
* @version 1.0 
*/ 
public class BibleTest { 

@Before
public void before() throws Exception {
    // Load the agent into the running JVM process
    if (!AgentLoader.loadAgentFromClasspath("ebean-agent", "debug=1")) {
    }
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: fetch(BookNames bookName, int chapter, int verse) 
* 
*/ 
@Test
public void testFetch() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: between(BookNames bookName, int chapter, int from, int to) 
* 
*/ 
@Test
public void testBetween() throws Exception {
    Bible bible = new Bible();

    Verses fetch = bible.fetch(BookNames.numMap().get(3), 3, 3);

    System.out.println(fetch);

    List<Verses> between = bible.between(BookNames.numMap().get(3), 3, 3, 5);

    System.out.println(between);
}

/** 
* 
* Method: main(String[] args) 
* 
*/ 
@Test
public void testMain() throws Exception { 
//TODO: Test goes here... 
} 


/** 
* 
* Method: loadVersion(String version) 
* 
*/ 
@Test
public void testLoadVersion() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = Bible.getClass().getMethod("loadVersion", String.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

/** 
* 
* Method: dataSource(String version) 
* 
*/ 
@Test
public void testDataSource() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = Bible.getClass().getMethod("dataSource", String.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

} 
