package com.concentrate.search.hotswap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.concentrate.search.hotswap.bean.ChannelSortFunction;
import com.concentrate.search.hotswap.bean.SortFunctionCoeffBean;
import com.concentrate.search.hotswap.builder.AlgorithmBuilder;
import com.concentrate.search.hotswap.builder.OutterResourceClassLoader;
import com.concentrate.search.hotswap.config.Constant;
import com.concentrate.search.hotswap.impl.channel.YpdsListDefaultSortImpl;
import com.concentrate.search.hotswap.impl.channel.YpdsSearchDefaultSortImpl;
import com.concentrate.search.hotswap.param.SortParam;

/**
 * 
 * 〈一句话功能简述〉<br> 
 * 算法工厂
 *
 * @author 12091669
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class SortFunctionFactory {
    
	private final static Logger LOGGER = LoggerFactory.getLogger(SortFunctionFactory.class);
	
	public static Map<String, Map<String, ChannelSortFunction>> channelFuncMaps = new ConcurrentHashMap<String, Map<String, ChannelSortFunction>>();
    
    public static OutterResourceClassLoader loader = new OutterResourceClassLoader();
    
    public static String KEYWORDCOEFF_FILENAME = "keywordCoeff.txt";
    public static String DEFAULTCOEFF_FILENAME = "defaultCoeff.txt";
    
    public static String KEYWORDCOEFF_PATH = "txt/"+KEYWORDCOEFF_FILENAME;
    public static String DEFAULTCOEFF_PATH = "txt/"+DEFAULTCOEFF_FILENAME;
    
    // 一品多商频道，此频道需要设置默认公式
    public static String YPDS_CHANNEL = "ypds";
    public static Map<String, Float> defaultSearchCoeffientMap = new HashMap<String, Float>();
    public static Map<String, Float> defaultListCoeffientMap = new HashMap<String, Float>();
    private static SortFunctionCoeffBean ypdsSearchSfcb = null;
    private static SortFunctionCoeffBean ypdsListSfcb = null;
    
    static{
    	defaultSearchCoeffientMap.put("A", 0.4f);
    	defaultSearchCoeffientMap.put("B", 0.4f);
    	defaultSearchCoeffientMap.put("C", 0.2f);
    	
    	defaultListCoeffientMap.put("A", 0.4f);
    	defaultListCoeffientMap.put("B", 0.4f);
    	defaultListCoeffientMap.put("C", 0.2f);
    	
    	ypdsSearchSfcb = new SortFunctionCoeffBean(YPDS_CHANNEL, AlgorithmBuilder.SEARCH_SCOPE_TYPE, YpdsSearchDefaultSortImpl.class.getName(), new YpdsSearchDefaultSortImpl(), defaultSearchCoeffientMap);
    	ypdsListSfcb = new SortFunctionCoeffBean(YPDS_CHANNEL, AlgorithmBuilder.LIST_SCOPE_TYPE, YpdsListDefaultSortImpl.class.getName(), new YpdsListDefaultSortImpl(), defaultListCoeffientMap);
    	
    	initAlgorithmsMap();
    	initKeywordAlgorithmaMap();
    }
    
    public synchronized static boolean initKeywordAlgorithmaMap() {
    	boolean success = false;
    	
    	File channelDir = new File(AlgorithmBuilder.CHANNEL_PATH);
		if(channelDir.exists() && channelDir.isDirectory()) {
			File[] channels = channelDir.listFiles();
			for (File channel : channels) {
				if(!channel.isDirectory()) {
					continue;
				}
				String channelName = channel.getName();
				
				refreshKeywordAlgorithmaMap(channelName, AlgorithmBuilder.DEFAULT_SCOPE_TYPE);
				refreshKeywordAlgorithmaMap(channelName, AlgorithmBuilder.SEARCH_SCOPE_TYPE);
				refreshKeywordAlgorithmaMap(channelName, AlgorithmBuilder.LIST_SCOPE_TYPE);
				refreshKeywordAlgorithmaMap(channelName, AlgorithmBuilder.VIRTUAL_LIST_SCOPE_TYPE);
			}
		} else {
			LOGGER.warn(AlgorithmBuilder.CHANNEL_PATH+" is not exists!");
		}
		return success;
    }
    
    // 适用于手动同步刷新自定义关键词公式
    public synchronized static boolean refreshKeywordAlgorithmaMap(String channel, String scopeType) {
    	File file = new File(AlgorithmBuilder.CHANNEL_PATH+channel+"/"+scopeType+"/"+KEYWORDCOEFF_PATH);
    	Map<String, SortFunctionCoeffBean> tempKeywordAlgorithms = new HashMap<String, SortFunctionCoeffBean>();
    	boolean success = false;
		try {
			BufferedReader reader = null;
			if (file.exists()) {
				ChannelSortFunction csf = getChannelSortFunc(channel, scopeType);
				if(csf == null) {
					LOGGER.warn("频道："+channel+", 页面类型："+scopeType+"在内存不存在！请检查是否已刷新频道公式！");
					return false;
				}
				reader = new BufferedReader(new InputStreamReader(new  FileInputStream(file),"UTF-8"));
				String line;
				LOGGER.info("读取文件" + file.getPath() + "开始。");
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
						updateKeywordAlgorithm(csf, tempKeywordAlgorithms, keyword, algorithmName, coeffKeyStr, coeffValuesStr);
					} else {
						LOGGER.warn(line+"数据不是4列");
					}
				}
				
				csf.getKeywordAlgorithms().clear();
				csf.getKeywordAlgorithms().putAll(tempKeywordAlgorithms);
				
				LOGGER.info("读取文件" + file.getPath() + "结束");
				success = true;
			} else {
				LOGGER.warn("文件" + file.getPath() + "不存在！");
			}
		} catch (Exception e) {
			LOGGER.error("read "+file.getPath() + " error!", e);
		} 
		
		return success;
    }
    
    public synchronized static boolean initAlgorithmsMap() {
    	boolean success = false;
    	channelFuncMaps.clear();
    	
    	File channelDir = new File(AlgorithmBuilder.CHANNEL_PATH);
		if(channelDir.exists() && channelDir.isDirectory()) {
			File[] channels = channelDir.listFiles();
			for (File channel : channels) {
				if(!channel.isDirectory()) {
					continue;
				}
				String channelName = channel.getName();
				
				Map<String, ChannelSortFunction> csfMap = new HashMap<String, ChannelSortFunction>();
				channelFuncMaps.put(channelName, csfMap);
				
				success &= parseAlgorithmData(csfMap, channelName, AlgorithmBuilder.DEFAULT_SCOPE_TYPE);
				success &= parseAlgorithmData(csfMap, channelName, AlgorithmBuilder.SEARCH_SCOPE_TYPE);
				success &= parseAlgorithmData(csfMap, channelName, AlgorithmBuilder.LIST_SCOPE_TYPE);
				success &= parseAlgorithmData(csfMap, channelName, AlgorithmBuilder.VIRTUAL_LIST_SCOPE_TYPE);
			}
		} else {
			LOGGER.warn(AlgorithmBuilder.CHANNEL_PATH+" is not exists!");
		}
		
		//商品平铺搜索页设置默认公式
		setDefaultSortFunction(ypdsSearchSfcb);
		//商品平铺列表页设置默认公式
		setDefaultSortFunction(ypdsListSfcb);
		return success;
    }
    
    /**
     * 设置频道默认公式
     * @param sfcb
     */
    private static void setDefaultSortFunction(SortFunctionCoeffBean sfcb) {
    	Map<String, ChannelSortFunction> csfMap = channelFuncMaps.get(sfcb.getChannelName());
    	if(csfMap == null) {
			csfMap = new HashMap<String, ChannelSortFunction>();
			ChannelSortFunction csf = new ChannelSortFunction(sfcb.getChannelName(), sfcb.getScopeType());
			csf.setGlobalAlgorithm(sfcb);
			csf.getAlgorithms().put(sfcb.getFuncName(), sfcb);
			csfMap.put(csf.getScopeType(), csf);
			channelFuncMaps.put(sfcb.getChannelName(), csfMap);
		} else {
			ChannelSortFunction csf = csfMap.get(sfcb.getScopeType());
			if(csf == null) {
				csf = new ChannelSortFunction(sfcb.getChannelName(), sfcb.getScopeType());
				csf.setGlobalAlgorithm(sfcb);
				csf.getAlgorithms().put(sfcb.getFuncName(), sfcb);
				csfMap.put(csf.getScopeType(), csf);
			}else {
				csf.getAlgorithms().put(sfcb.getFuncName(), sfcb);
				if(csf.getGlobalAlgorithm() == null) {
					csf.setGlobalAlgorithm(sfcb);
				}
			}
		}
    }
    
    public static boolean parseAlgorithmData(Map<String, ChannelSortFunction> csfMap, String channelName, String scopeType) {
    	boolean success = false;
		ChannelSortFunction csf = new ChannelSortFunction(channelName, scopeType);
		csfMap.put(scopeType, csf);
		
		String filePath = AlgorithmBuilder.CHANNEL_PATH+channelName+"/"+scopeType+"/"+DEFAULTCOEFF_PATH;
		File file = new File(filePath);
		try {
			BufferedReader reader = null;
			if (file.exists()) {
				reader = new BufferedReader(new InputStreamReader(new  FileInputStream(file),"UTF-8"));
				String line;
				LOGGER.info("读取文件" + filePath + "开始。");
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
						updateAlgorithm(csf, algoName, AlgorithmBuilder.PACKAGE_PATH+"."+className, isGolbal, coeffKeyStr, coeffValuesStr);
					} else {
						LOGGER.warn(line+"数据不是7列");
					}
				}
				LOGGER.info("读取文件" + filePath + "结束");
				success = true;
			} else {
				LOGGER.warn("文件" + filePath + "不存在！");
			}
		} catch (Exception e) {
			LOGGER.error("read "+filePath + " error!", e);
		} 
		return success;
    }
    
    // 适用于手动同步刷新公式（增量）
    public synchronized static boolean refreshAlgorithm(String channel, String scopeType) {
    	String scopePath = AlgorithmBuilder.CHANNEL_PATH+channel+"/"+scopeType+"/";
    	File file = new File(scopePath+DEFAULTCOEFF_PATH);
    	boolean success = false;
		try {
			BufferedReader reader = null;
			if (file.exists()) {
				Map<String, ChannelSortFunction> csfMap = channelFuncMaps.get(channel);
				if(csfMap == null) {
					csfMap = new HashMap<String, ChannelSortFunction>();
					channelFuncMaps.put(channel, csfMap);
				}
				ChannelSortFunction csf = csfMap.get(scopeType);
				if(csf == null) {
					csf = new ChannelSortFunction(channel, scopeType);
					csfMap.put(scopeType, csf);
				}
				
				List<String> algoNamesInFile = new ArrayList<String>();
				List<String> classNamesInFile = new ArrayList<String>();
				reader = new BufferedReader(new InputStreamReader(new  FileInputStream(file),"UTF-8"));
				String line;
				LOGGER.info("读取文件" + file.getPath() + "开始。");
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
						
						SortFunctionCoeffBean algoBean = csf.getAlgorithms().get(algoName);
						// 如果该公式在内存不存在，需要实例化，或者该公式算法被更新后也需要重新实例化
						if (algoBean == null || "1".equals(isUpdateClass)) {
							updateAlgorithm(csf, algoName, AlgorithmBuilder.PACKAGE_PATH+"."+className, isGolbal, coeffKeyStr, coeffValuesStr);
						} else if("1".equals(isUpdateCoeff)) {
							// 仅仅更新系数
							Map<String, Float> coeffMap = generateCoeffMap(coeffKeyStr, coeffValuesStr);
							algoBean.setCoeffientMap(coeffMap);
						}
					} else {
						LOGGER.warn(line+"数据不是7列");
					}
				}
				
				for (String key : csf.getAlgorithms().keySet()) {
					if (!algoNamesInFile.contains(key)) {
						//该公式已被删除
						deleteAlgorithm(csf, key);
					}
				}
				success = true;
				LOGGER.info("读取文件" + file.getPath() + "结束");
				
				// 清除旧class文件
		    	try {
		    		File packageFile = new File(scopePath+AlgorithmBuilder.PACKAGE_PATH.replaceAll("\\.", "/"));
		        	if(packageFile.exists() && packageFile.isDirectory()) {
		        		File[] classFiles = packageFile.listFiles();
		        		for (File classFile : classFiles) {
		        			String fileName = classFile.getName();
		        			if (fileName.startsWith(Constant.CLASS_PREFIX) && classFile.isFile() && !classNamesInFile.contains(fileName)) {
		        				classFile.delete();
		        			}
		        		}
		        	}
				} catch (Exception e) {
					LOGGER.error("清除旧class文件错误!", e);
				}
			} else {
				LOGGER.warn("文件" + file.getPath() + "不存在！");
			}
			
			if(ypdsSearchSfcb.getChannelName().equals(channel) && ypdsSearchSfcb.getScopeType().equals(scopeType)) {
				//商品平铺搜索页设置默认公式
				setDefaultSortFunction(ypdsSearchSfcb);
			}

			if(ypdsListSfcb.getChannelName().equals(channel) && ypdsListSfcb.getScopeType().equals(scopeType)) {
				//商品平铺列表页设置默认公式
				setDefaultSortFunction(ypdsListSfcb);
			}
		} catch (Exception e) {
			LOGGER.error("read "+file.getPath() + " error!", e);
		} 
		return success;
    }

    
    public static boolean updateAlgorithm(ChannelSortFunction csf, String algorithmName,String className, String isGlobal, String coeffKeyStr, String coeffValueStr){
    	boolean success = false;
    	String classPath = className.replaceAll("\\.", "/");
    	String filePath = AlgorithmBuilder.CHANNEL_PATH+csf.getChannelName()+"/"+csf.getScopeType()+"/" + classPath + ".class";
    	
    	SortFunction tmp = null;
    	boolean isLoadClass = true;
    	SortFunctionCoeffBean algoBean = csf.getAlgorithms().get(algorithmName);
    	if(algoBean != null) {
    		SortFunction fun = algoBean.getFunc();
    		if(fun != null) {
    			String jvmClassName = fun.getClass().getName();
        		if(className.equals(jvmClassName)) {//如果jvm中已存在该class，不用再次加载
        			isLoadClass = false;
        			tmp=fun;
        		}
    		}
    	}
    	
    	Class<SortFunction> clas = null;
    	
    	if(isLoadClass) {
    		try {
        		clas = (Class<SortFunction>) loader.loadOutterClazz(filePath, className);
        	} catch (Error e) {
        		LOGGER.error("load class error!", e);
    		}
    		
    		try {
            	if(clas == null) {
        			clas = (Class<SortFunction>) Class.forName(className, false, loader);
            	}
            	tmp = clas.newInstance();
            } catch (Exception e) {
            	LOGGER.error("class instance error!", e);
            } 
    	}
        
        if(tmp!=null){
        	Map<String, Float> coeffMap = generateCoeffMap(coeffKeyStr, coeffValueStr);
        	if(algoBean != null) {
        		algoBean.setFunc(tmp);
        		algoBean.setCoeffientMap(coeffMap);
        	} else {
        		algoBean = new SortFunctionCoeffBean(csf.getChannelName(), csf.getScopeType(), algorithmName,tmp,coeffMap);
        		csf.getAlgorithms().put(algorithmName,algoBean);
        	}
        	
        	
			if ("1".equals(isGlobal)) {
				csf.setGlobalAlgorithm(algoBean);
			} else if(csf.getGlobalAlgorithm() != null && csf.getGlobalAlgorithm().getFuncName().equals(algoBean.getFuncName())){
				csf.setGlobalAlgorithm(null);
			}
			success = true;
			LOGGER.info("更新公式. algorithmName: "+algorithmName + ", classname: " + className + ", isGlobal: " + isGlobal + ", coeffMap: " + (coeffMap == null ? null : coeffMap.toString()));
        }
        return success;
    }
    
    public static boolean updateKeywordAlgorithm(ChannelSortFunction csf, Map<String, SortFunctionCoeffBean> tempKeywordAlgorithms, String keyword,String algorithmName, String coeffKeyStr, String coeffValueStr){
    	boolean success = false;
    	SortFunctionCoeffBean algoBean = csf.getAlgorithms().get(algorithmName);
    	if(algoBean != null && algoBean.getFunc() != null) {
			Map<String, Float> coeffMap = generateCoeffMap(coeffKeyStr, coeffValueStr);
			tempKeywordAlgorithms.put(keyword, new SortFunctionCoeffBean(csf.getChannelName(), csf.getScopeType(), algorithmName, algoBean.getFunc(), coeffMap));
    		success = true;
    		LOGGER.info("更新关键字公式规则. keyword: "+keyword + ", algorithmName: " + algorithmName + ", coeffMap: " + (coeffMap == null ? null : coeffMap.toString()));
    	} else {
    		LOGGER.warn("更新关键字公式规则. SortFunctionCoeffBean: " + null);
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
    
    public static float evalFloat(String channelName, String scopeType, String keyword,SortParam param){
    	SortFunctionCoeffBean algoCoeff = getFunc(channelName, scopeType, keyword);
    	SortFunction algo = (algoCoeff == null ? null : algoCoeff.getFunc());
    	Map<String, Float> coeffMap = (algoCoeff == null ? null : algoCoeff.getCoeffientMap());
    	
    	if (algo != null) {
    		if (LOGGER.isInfoEnabled()) {
        		LOGGER.info("channel: "+channelName+", scopeType: "+scopeType+", keyword: "+keyword+", algorithmClass: "+algo.getClass().getName()+", coeff："+coeffMap + ", params: "+param.toString());
        	}
            return evalFloat(algo, param, coeffMap);
    	} else {
    		if (LOGGER.isInfoEnabled()) {
        		LOGGER.info("Not found SortFunction! channel: "+channelName+", scopeType: "+scopeType+", keyword: "+keyword);
        	}
        	return 0;
    	}
    }
    
    /**
     * 获取公式，如果指定channel下未能获取到相应公式，则去global下重新获取一次
     * @param channelName
     * @param scopeType
     * @param keyword
     * @return
     */
    public static SortFunctionCoeffBean getFunc(String channelName, String scopeType, String keyword) {
    	SortFunctionCoeffBean algoCoeff = null;
    	ChannelSortFunction rsf = getChannelSortFunc(channelName, scopeType);
    	
    	if(rsf != null) {
    		if (keyword != null) {
        		algoCoeff = rsf.getKeywordAlgorithms().get(keyword);
        	}
        	if (algoCoeff == null) {// 关键词获取不到自定义公式，则获取该频道页面下全局公式
        		algoCoeff = rsf.getGlobalAlgorithm();
        	}
    	} else {
    		if (LOGGER.isInfoEnabled()) {
    			LOGGER.info("Not found ChannelSortFunction! channel: "+channelName+", scopeType: "+scopeType);
        	}
    	}
    	
    	// 如果指定频道未能获取到公式，则取全局频道的公式
    	if (algoCoeff == null) {
    		channelName = AlgorithmBuilder.GLOBAL_CHANNEL;
    		ChannelSortFunction globalRsf = getChannelSortFunc(channelName, scopeType);
    		if(globalRsf != null) {
        		if (keyword != null) {
            		algoCoeff = globalRsf.getKeywordAlgorithms().get(keyword);
            	}
            	if (algoCoeff == null) {// 关键词获取不到自定义公式，则获取该频道页面下全局公式
            		algoCoeff = globalRsf.getGlobalAlgorithm();
            	}
        	} else {
        		if (LOGGER.isInfoEnabled()) {
        			LOGGER.info("Not found ChannelSortFunction! channel: "+channelName+", scopeType: "+scopeType);
            	}
        	}
    	}
    	
		SortFunction algo = (algoCoeff == null ? null : algoCoeff.getFunc());

		if (algo != null) {
    		if (LOGGER.isInfoEnabled()) {
    			Map<String, Float> coeffMap = (algoCoeff == null ? null : algoCoeff.getCoeffientMap());
        		LOGGER.info("channel: "+channelName+", scopeType: "+scopeType+", keyword: "+keyword+", algorithmClass: "+algo.getClass().getName()+", coeff："+coeffMap);
        	}
    	} else {
    		if (LOGGER.isInfoEnabled()) {
        		LOGGER.info("Not found SortFunction! channel: "+channelName+", scopeType: "+scopeType+", keyword: "+keyword);
        	}
    	}
    	return algoCoeff;
    }
    
    public static float evalFloat(SortFunction algo,SortParam param, Map<String, Float> coeffMap){
    	float rs = algo.evalFloat(param, coeffMap);
    	if (LOGGER.isInfoEnabled()) {
    		LOGGER.info("score: "+ rs);
    	}
        return rs;
    }
    
    public static void deleteAlgorithm(ChannelSortFunction csf, String algorithmName) {
    	csf.getAlgorithms().remove(algorithmName);
    	if (csf.getGlobalAlgorithm() != null && csf.getGlobalAlgorithm().getFuncName().equals(algorithmName)) {
    		csf.setGlobalAlgorithm(null);
    	}
    }
    
    public static float test(String className, SortParam param, Map<String, Float> coeffMap, String testPath) {
    	String classPath = className.replaceAll("\\.", "/");
    	String filePath = testPath + classPath + ".class";
        Class<SortFunction> clas = (Class<SortFunction>) loader.loadOutterClazz(filePath, className);
        SortFunction tmp = null;
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
    
    public static ChannelSortFunction getChannelSortFunc(String channel, String scopeType) {
		Map<String, ChannelSortFunction> csfMap = channelFuncMaps.get(channel);
		ChannelSortFunction csf = null;
		if(csfMap != null) {
			csf = csfMap.get(scopeType);
		}
    	return csf;
    }
    
    public static void main(String[] args) {
//    	Algorithm ah = new AlgorithmHotSaleImpl();
//    	System.out.println(ah instanceof AlgorithmDefaultSortImpl);
//    	System.out.println(ah instanceof AlgorithmHotSaleImpl);
//    	initAlgorithmsMap();
    	
    	System.out.println(0.19800*0.5+1.00000*0.5+0.00000*0.0);
    	System.out.println(0.19800*0.5+0.52920*0.3+0.00000*0.0);
    	
    	List<String> list = new ArrayList<String>();
        Collections.sort(list, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				try {
					int a = Integer.valueOf(o1.substring(1));
					int b = Integer.valueOf(o2.substring(1));
					return a-b;
				} catch(Exception e) {
					return 0;
				}

			}
    	});
        
        System.out.println(SortFunctionFactory.class.getName());
    }
}
