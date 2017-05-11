package com.concentrate.search.hotswap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.concentrate.search.hotswap.bean.AlgorithmCoeffBean;
import com.concentrate.search.hotswap.builder.AlgorithmBuilder;
import com.concentrate.search.hotswap.builder.OutterResourceClassLoader;
import com.concentrate.search.hotswap.config.Constant;
import com.concentrate.search.hotswap.impl.ListPageAlgorithmDefaultSortImpl;
import com.concentrate.search.hotswap.param.SearchParam;

/**
 * 
 * 〈一句话功能简述〉<br> 
 * 算法工厂
 *
 * @author 12091669
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ListPageAlgorithmFactory {
    
	private final static Logger LOGGER = LoggerFactory.getLogger(ListPageAlgorithmFactory.class);
	
    public static Map<String,AlgorithmCoeffBean> algorithms = new ConcurrentHashMap<String,AlgorithmCoeffBean>();
    
    public static Map<String, AlgorithmCoeffBean> keywordAlgorithms = new ConcurrentHashMap<String, AlgorithmCoeffBean>();

    public static AlgorithmCoeffBean globalAlgorithm;
    
    public static OutterResourceClassLoader loader = new OutterResourceClassLoader();
    
    public static String KEYWORDCOEFF_FILENAME = "listPageKeywordCoeff.txt";
    public static String DEFAULTCOEFF_FILENAME = "listPageDefaultCoeff.txt";
    
    public static String KEYWORDCOEFF_PATH = AlgorithmBuilder.HOTSWAP_PATH + "txt/"+KEYWORDCOEFF_FILENAME;
    public static String DEFAULTCOEFF_PATH = AlgorithmBuilder.HOTSWAP_PATH + "txt/"+DEFAULTCOEFF_FILENAME;
    
    public static String DEFAULTSORT_ALGORITHM = "默认排序值";
	public static Map<String, Float> defaultCoeffientMap = new HashMap<String, Float>();
	
	private static Algorithm defaultAlgorithm = new ListPageAlgorithmDefaultSortImpl();
    
    static{
    	defaultCoeffientMap.put("k1", 1.0f);
    	defaultCoeffientMap.put("k2", 0.0f);
    	defaultCoeffientMap.put("k3", 0.0f);
    	defaultCoeffientMap.put("k4", 0.0f);
    	initAlgorithmsMap();
    	initKeywordAlgorithmaMap();
    }
    
    public synchronized static boolean initKeywordAlgorithmaMap() {
    	boolean success = false;
    	Map<String, AlgorithmCoeffBean> tempKeywordAlgorithms = new HashMap<String, AlgorithmCoeffBean>();
    	File file = new File(KEYWORDCOEFF_PATH);
		try {
			BufferedReader reader = null;
			if (file.exists()) {
				reader = new BufferedReader(new InputStreamReader(new  FileInputStream(file),"UTF-8"));
				String line;
				LOGGER.info("读取文件" + KEYWORDCOEFF_PATH + "开始。");
				while ((line = reader.readLine()) != null) {
					if(line.endsWith(",")) {
						line = line.substring(0, line.length() - 1);
					}
					String[] eles = line.split(",", -1);
					if (eles.length == 4) {
						String keyword = eles[0];
						String algorithmName = eles[1];
						String coeffKeyStr = eles[2];
						String coeffValuesStr = eles[3];
						updateKeywordAlgorithm(tempKeywordAlgorithms, keyword, algorithmName, coeffKeyStr, coeffValuesStr);
					} else {
						LOGGER.warn(line+"数据不是4列");
					}
				}
				LOGGER.info("读取文件" + KEYWORDCOEFF_PATH + "结束");
				success = true;
			} else {
				LOGGER.warn("文件" + KEYWORDCOEFF_PATH + "不存在！");
			}
		} catch (Exception e) {
			LOGGER.error("read "+KEYWORDCOEFF_PATH + " error!", e);
		} 
		
		if (success) {
			keywordAlgorithms.clear();
			keywordAlgorithms.putAll(tempKeywordAlgorithms);
		}
		return success;
    }
    
    public synchronized static boolean initAlgorithmsMap() {
    	boolean success = false;
    	globalAlgorithm = null;
    	algorithms.clear();
    	algorithms.put(DEFAULTSORT_ALGORITHM, new AlgorithmCoeffBean(DEFAULTSORT_ALGORITHM, defaultAlgorithm, defaultCoeffientMap));
    	
    	File file = new File(DEFAULTCOEFF_PATH);
		try {
			BufferedReader reader = null;
			if (file.exists()) {
				reader = new BufferedReader(new InputStreamReader(new  FileInputStream(file),"UTF-8"));
				String line;
				LOGGER.info("读取文件" + DEFAULTCOEFF_PATH + "开始。");
				while ((line = reader.readLine()) != null) {
					if(line.endsWith(",")) {
						line = line.substring(0, line.length() - 1);
					}
					String[] eles = line.split(",", -1);
					if (eles.length >= 7) {
						String algoName = eles[0];
						String className = eles[1];
						String isGolbal = eles[2];
						String coeffKeyStr = eles[3];
						String coeffValuesStr = eles[4];
						updateAlgorithm(algoName, AlgorithmBuilder.PACKAGE_PATH+"."+className, isGolbal, coeffKeyStr, coeffValuesStr);
					} else {
						LOGGER.warn(line+"数据不是7列");
					}
				}
				LOGGER.info("读取文件" + DEFAULTCOEFF_PATH + "结束");
				success = true;
			} else {
				LOGGER.warn("文件" + DEFAULTCOEFF_PATH + "不存在！");
			}
		} catch (Exception e) {
			LOGGER.error("read "+DEFAULTCOEFF_PATH + " error!", e);
		} 
		
		return success;
    }
    
    // 适用于手动同步刷新公式（增量）
    public synchronized static boolean refreshAlgorithm() {
    	File file = new File(DEFAULTCOEFF_PATH);
    	boolean success = false;
		try {
			BufferedReader reader = null;
			if (file.exists()) {
				List<String> classNamesInFile = new ArrayList<String>();
				List<String> algoNamesInFile = new ArrayList<String>();
				reader = new BufferedReader(new InputStreamReader(new  FileInputStream(file),"UTF-8"));
				String line;
				LOGGER.info("读取文件" + DEFAULTCOEFF_PATH + "开始。");
				while ((line = reader.readLine()) != null) {
					if(line.endsWith(",")) {
						line = line.substring(0, line.length() - 1);
					}
					String[] eles = line.split(",", -1);
					if (eles.length == 7) {
						String algoName = eles[0];
						String className = eles[1];
						String isGolbal = eles[2];
						String coeffKeyStr = eles[3];
						String coeffValuesStr = eles[4];
						String isUpdateClass = eles[5];
						String isUpdateCoeff = eles[6];
						
						algoNamesInFile.add(algoName);
						classNamesInFile.add(className+".class");
						
						AlgorithmCoeffBean algoBean = algorithms.get(algoName);
						// 如果该公式在内存不存在，需要实例化，或者该公式算法被更新后也需要重新实例化
						if (algoBean == null || "1".equals(isUpdateClass)) {
							updateAlgorithm(algoName, AlgorithmBuilder.PACKAGE_PATH+"."+className, isGolbal, coeffKeyStr, coeffValuesStr);
						} else if("1".equals(isUpdateCoeff)) {
							// 仅仅更新系数
							Map<String, Float> coeffMap = generateCoeffMap(coeffKeyStr, coeffValuesStr);
							algoBean.setCoeffientMap(coeffMap);
						}
					} else {
						LOGGER.warn(line+"数据不是7列");
					}
				}
				
				for (String key : algorithms.keySet()) {
					if (!algoNamesInFile.contains(key)) {
						if (key.equals(DEFAULTSORT_ALGORITHM)) {
						    if (!(algorithms.get(key).getAlgorithm() instanceof ListPageAlgorithmDefaultSortImpl)) {
						    	algorithms.put(DEFAULTSORT_ALGORITHM, new AlgorithmCoeffBean(DEFAULTSORT_ALGORITHM, defaultAlgorithm, defaultCoeffientMap));
						    }
							continue;
						}
						//该公式已被删除
						deleteAlgorithm(key);
					}
				}
				success = true;
				LOGGER.info("读取文件" + DEFAULTCOEFF_PATH + "结束");
				
				// 清除旧class文件
		    	try {
		    		File packageFile = new File(AlgorithmBuilder.HOTSWAP_PATH+AlgorithmBuilder.PACKAGE_PATH.replaceAll("\\.", "/"));
		        	if(packageFile.exists() && packageFile.isDirectory()) {
		        		File[] classFiles = packageFile.listFiles();
		        		for (File classFile : classFiles) {
		        			String fileName = classFile.getName();
		        			if (fileName.startsWith(Constant.LIST_PAGE_CLASS_PREFIX) && classFile.isFile() && !classNamesInFile.contains(fileName)) {
		        				classFile.delete();
		        			}
		        		}
		        	}
				} catch (Exception e) {
					LOGGER.error("清除旧class文件错误!", e);
				}
			} else {
				LOGGER.warn("文件" + DEFAULTCOEFF_PATH + "不存在！");
			}
		} catch (Exception e) {
			LOGGER.error("read "+DEFAULTCOEFF_PATH + " error!", e);
		} 
		return success;
    }

    public static boolean updateAlgorithm(String algorithmName,String className, String isGlobal, String coeffKeyStr, String coeffValueStr){
    	boolean success = false;
    	String classPath = className.replaceAll("\\.", "/");
    	String filePath = AlgorithmBuilder.HOTSWAP_PATH + classPath + ".class";
    	Class<Algorithm> clas = null;
    	try {
    		clas = loader.loadOutterClass(filePath, className);
    	} catch (Error e) {
    		LOGGER.error("load class error!", e);
		}
       
        Algorithm tmp = null;
        try {
        	if(clas == null) {
    			clas = (Class<Algorithm>) Class.forName(className, false, loader);
        	}
        	tmp = clas.newInstance();
        } catch (Exception e) {
        	LOGGER.error("class instance error!", e);
        } 
        if(tmp!=null){
        	Map<String, Float> coeffMap = generateCoeffMap(coeffKeyStr, coeffValueStr);
			
        	AlgorithmCoeffBean algoBean = algorithms.get(algorithmName);
        	if(algoBean != null) {
        		algoBean.setAlgorithm(tmp);
        		algoBean.setCoeffientMap(coeffMap);
        	} else {
        		algoBean = new AlgorithmCoeffBean(algorithmName,tmp,coeffMap);
        	}
        	algorithms.put(algorithmName,algoBean);
        	
			if ("1".equals(isGlobal)) {
				globalAlgorithm = algoBean;
			} else if(globalAlgorithm != null && globalAlgorithm.getAlgorithmName().equals(algoBean.getAlgorithmName())){
				globalAlgorithm = null;
			}
			success = true;
			LOGGER.info("更新公式. algorithmName: "+algorithmName + ", classname: " + className + ", isGlobal: " + 1 + ", coeffMap: " + (coeffMap == null ? null : coeffMap.toString()));
        }
        return success;
    }
    
    public static boolean updateKeywordAlgorithm(Map<String, AlgorithmCoeffBean> tempKeywordAlgorithms, String keyword,String algorithmName, String coeffKeyStr, String coeffValueStr){
    	boolean success = false;
    	AlgorithmCoeffBean algoBean = algorithms.get(algorithmName);
    	if(algoBean != null && algoBean.getAlgorithm() != null) {
			Map<String, Float> coeffMap = generateCoeffMap(coeffKeyStr, coeffValueStr);
			tempKeywordAlgorithms.put(keyword, new AlgorithmCoeffBean(algorithmName, algoBean.getAlgorithm(), coeffMap));
    		success = true;
    		LOGGER.info("更新列表页公式规则. keyword: "+keyword + ", algorithmName: " + algorithmName + ", coeffMap: " + (coeffMap == null ? null : coeffMap.toString()));
    	} else {
    		LOGGER.warn("更新列表页公式规则. AlgorithmCoeffBean: " + null);
    	}
    	return success;
    }
    
    private static Map<String, Float> generateCoeffMap(String coeffKeyStr, String coeffValueStr) {
    	Map<String, Float> coeffMap = new HashMap<String, Float>();
		if (coeffKeyStr != null && !coeffKeyStr.equals("") && coeffValueStr != null && !coeffValueStr.equals("")) {
			String[] coeffKeys = coeffKeyStr.split("\\|");
			String[] coeffValues = coeffValueStr.split("\\|");
			for(int i=0; i<coeffKeys.length; i++) {
				coeffMap.put(coeffKeys[i], Float.valueOf(coeffValues[i]));
			}
		}
		return coeffMap;
    }
    
    public static float evalFloat(String keyword,SearchParam param){
    	AlgorithmCoeffBean algoCoeff = null;
    	if (keyword != null) {
    		algoCoeff = keywordAlgorithms.get(keyword);
    	}
    	if (algoCoeff == null) {
    		algoCoeff = globalAlgorithm;
    	}
    	if (algoCoeff == null) {
    		algoCoeff = algorithms.get(DEFAULTSORT_ALGORITHM);
    	}
    	Algorithm algo = (algoCoeff == null ? defaultAlgorithm : algoCoeff.getAlgorithm());
    	Map<String, Float> coeffMap = (algoCoeff == null ? defaultCoeffientMap : algoCoeff.getCoeffientMap());
    	if (LOGGER.isInfoEnabled()) {
    		LOGGER.info("keyword: "+keyword+", algorithmClass: "+algo.getClass().getName()+", coeff："+coeffMap + ", params: "+param.toString());
    	}
    	return evalFloat(algo, param, coeffMap);
    }
    
    public static AlgorithmCoeffBean getAlgorithm(String keyword) {
    	AlgorithmCoeffBean algoCoeff = null;
    	if (keyword != null) {
    		algoCoeff = keywordAlgorithms.get(keyword);
    	}
    	if (algoCoeff == null) {
    		algoCoeff = globalAlgorithm;
    	}
    	if (algoCoeff == null) {
    		algoCoeff = algorithms.get(DEFAULTSORT_ALGORITHM);
    	}

    	if (LOGGER.isInfoEnabled()) {
        	Algorithm algo = (algoCoeff == null ? defaultAlgorithm : algoCoeff.getAlgorithm());
        	Map<String, Float> coeffMap = (algoCoeff == null ? defaultCoeffientMap : algoCoeff.getCoeffientMap());
    		LOGGER.info("keyword: "+keyword+", algorithmClass: "+algo.getClass().getName()+", coeff："+coeffMap);
    	}
    	return algoCoeff;
    }
    
    public static float evalFloat(Algorithm algo,SearchParam param, Map<String, Float> coeffMap){
    	float rs = algo.evalFloat(param, coeffMap);
    	if (LOGGER.isInfoEnabled()) {
    		LOGGER.info("score: "+ rs);
    	}
    	return rs;
    }
    
    public static void deleteAlgorithm(String algorithmName) {
    	algorithms.remove(algorithmName);
    	if (globalAlgorithm != null && globalAlgorithm.getAlgorithmName().equals(algorithmName)) {
    		globalAlgorithm = null;
    	}
    }
    
    public static void deleteKeywordAlgorithm(String keyword) {
    	keywordAlgorithms.remove(keyword);
    }
    
    public static float test(String className, SearchParam param, Map<String, Float> coeffMap, String testPath) {
    	String classPath = className.replaceAll("\\.", "/");
    	String filePath = testPath + classPath + ".class";
        Class<Algorithm> clas = loader.loadOutterClass(filePath, className);
        Algorithm tmp = null;
        try {
            if(clas!=null){
                tmp = clas.newInstance();
            }
        } catch (InstantiationException e) {
        	LOGGER.error("class instance error!", e);
        } catch (IllegalAccessException e) {
        	LOGGER.error("class instance error!", e);
        }
       return tmp.evalFloat(param, coeffMap);
    }
    
    public static void main(String[] args) {
//    	Algorithm ah = new AlgorithmHotSaleImpl();
//    	System.out.println(ah instanceof AlgorithmDefaultSortImpl);
//    	System.out.println(ah instanceof AlgorithmHotSaleImpl);
//    	initAlgorithmsMap();
    	
    	System.out.println(0.19800*0.5+1.00000*0.5+0.00000*0.0);
    	System.out.println(0.19800*0.5+0.52920*0.3+0.00000*0.0);
	}
}
