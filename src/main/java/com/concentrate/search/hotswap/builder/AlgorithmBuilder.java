package com.concentrate.search.hotswap.builder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.concentrate.search.hotswap.AlgorithmFactory;
import com.concentrate.search.hotswap.param.SearchParam;
import com.concentrate.search.hotswap.param.SortParam;

public class AlgorithmBuilder {
    
	public static String HOTSWAP_PATH = "/opt/search/hotswap/";
	public static String PACKAGE_PATH = "com.concentrate.search.hotswap";
	public static String TEMPLATE_NAME = "AlgorithmTemplate.txt";
	public static String SORT_FUNC_TEMPLATE_NAME = "SortFunctionTemplate.txt";
	public static String JAR_VERSION = "hotswap-1.3.3.jar";
	public static String CHANNEL_PATH = HOTSWAP_PATH + "channel/";
	public static String SEARCH_SCOPE_TYPE = "search";
	public static String LIST_SCOPE_TYPE = "list";
	public static String DEFAULT_SCOPE_TYPE = "default";
	public static String VIRTUAL_LIST_SCOPE_TYPE = "virtualList";
	public static String GLOBAL_CHANNEL = "global";
	
	private final static Logger LOGGER = LoggerFactory.getLogger(AlgorithmBuilder.class);
	
    /**
	 * 
	 * 功能描述: <br>
	 * 从绝对路径/opt/search/hotswap/目录中找，如果没找到，再从classpath中找模板
	 *
	 * @return
	 * @throws IOException
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
    public String getTemplate(String tempName) throws IOException{
        File f = new File(HOTSWAP_PATH+tempName);

        if(f==null || !f.exists()){
            try {
            	InputStream is = this.getClass().getClassLoader().getResourceAsStream(tempName);
            	return IOUtils.toString(is, "utf-8");
            } catch (Exception e) {
            	LOGGER.error("getTemplate error!", e);
            }
        }
        return FileUtils.readFileToString(f, "utf-8");
    }
    
    public String buildAlgorithm(String algorithmName, String expression) throws IOException{
        return buildAlgorithm(algorithmName, expression, HOTSWAP_PATH);
    }
    
    public String buildAlgorithm(String algorithmName, String expression, String path) throws IOException{
    	return buildAlgorithm(algorithmName, expression, path, TEMPLATE_NAME);
    }
    
    public String buildAlgorithm(String algorithmName, String expression, String path, String tempName) throws IOException{
        boolean result = false;
        String template =  getTemplate(tempName);
        String dateString = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        if(template!=null&&expression!=null){
			File parentPath = new File(path);
			if(!parentPath.exists()) {
				parentPath.mkdirs();
			}
			
        	String className = algorithmName+dateString;
            template = template.replace("${expression}", expression).replace("${className}", className);
            File f = new File(path+className + ".java");
			if (!f.exists()) {
				f.createNewFile();
			}
			FileWriter fw = new FileWriter(f);
			fw.write(template);
			fw.flush();
			fw.close();
            result = doCompileClass(path+f.getName(), path);
            LOGGER.info(algorithmName + " build " + result + ", classname:" + className);
            if(result) {
            	return className;
            }
        }
        return null;
    }
    
    private String buildClassPath() {  
        StringBuilder sb = new StringBuilder();  
        URLClassLoader parentClassLoader = (URLClassLoader) getClass().getClassLoader();
        for (URL url : parentClassLoader.getURLs()) {  
            String p = url.getFile();  
            sb.append(p);  
            sb.append(File.pathSeparator);  
        }  
        return sb.toString();  
    }  
    
    public boolean doCompileClass(String filePath) {
        return doCompileClass(filePath, HOTSWAP_PATH);
    }

    public boolean doCompileClass(String filePath, String targetPath) {
		boolean mark = true;
		try {
			File f = new File(filePath);
			JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
			StandardJavaFileManager fileManager = compiler
					.getStandardFileManager(null, null, null);
			Iterable iterable = fileManager.getJavaFileObjects(f);
			
//			StringBuilder sb = new StringBuilder();
//			URLClassLoader urlClassLoader = (URLClassLoader) Thread.currentThread().getContextClassLoader();
//			for (URL url : urlClassLoader.getURLs()){
//			    sb.append(url.getFile().replace("%20", " ")).append(File.pathSeparator);
//			}
//			String classpath = this.getClass().getResource("/").toURI().getPath();
//			classpath=classpath.replace("classes/", "") + "lib/"; //去掉class\  
			String classpath = HOTSWAP_PATH+"jar/"+JAR_VERSION;
			LOGGER.info("algorithm compile classpath:" + classpath);

			Iterable<String> options = Arrays.asList("-encoding","UTF-8","-d", targetPath, "-classpath", classpath);

			CompilationTask compilationTask = compiler.getTask(null,
					fileManager, null, options, null, iterable);
			compilationTask.call();
			fileManager.close();
		} catch (Exception e) {
			LOGGER.error("compile class error!", e);
			mark = false;
		}
        return mark;
    }
    
    public String test(String expression, SearchParam param, Map<String, Float> coeffMap, String testPath) {
    	String className = null;
		try {
			className = buildAlgorithm("test", expression, testPath);
		} catch (IOException e) {
			LOGGER.error("build algorithm error!", e);
		}
		String rs = null;
		if (className != null) {
			rs = AlgorithmFactory.test(PACKAGE_PATH+"."+className, param, coeffMap, testPath) + "";
		}
		return rs;
    }
    
    public static void main(String[] args) {
    	String algoName = "__test1";
    	AlgorithmBuilder builder = new AlgorithmBuilder();
    	String expression = "((coeffMap.get(\"k1\")*param.f1 + coeffMap.get(\"k2\")*param.f2) * 100)*0.5f";
    	SortParam param = new SortParam();
    	param.f1 = 2.0f;
    	param.f2 = 1.0f;
    	Map<String, Float> coeffMap = new HashMap<String, Float>();
    	coeffMap.put("k1", 0.5f);
    	coeffMap.put("k2", 0.5f);
//    	String className = null;
//    	try {
//    		className = builder.buildAlgorithm(expression);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    	
//    	AlgorithmFactory.updateAlgorithm(algoName, PACKAGE_PATH+"."+className);
//    	
//    	long start = System.currentTimeMillis();
//		Object result = null;
//		float f = AlgorithmFactory.evalFloat(algoName, param);
//		System.out.println(f+"");
//		int i = 0;
//		int times = 10000000;
//		long startTime = System.currentTimeMillis();
//		Algorithm algo = AlgorithmFactory.algorithms.get(algoName);
//		while (i++ < times) {
//			param.clickScore = 2.0f;
//	    	param.saleScore = 1.0f;
//	    	//float f = ((param.clickScore * param.saleScore) * 100)*0.5f - 400f;
//	    	//algo.evalFloat(param);
//	    	AlgorithmFactory.evalFloat(algoName, param);
//		}
//		System.out.println("dynaClassTest: "+(System.currentTimeMillis() - startTime));
//		
		
		String rs = null;
		try {
			rs = builder.buildAlgorithm(algoName, expression, AlgorithmBuilder.CHANNEL_PATH+"global/default/", AlgorithmBuilder.SORT_FUNC_TEMPLATE_NAME);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(rs);
    }
}
