/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package leap.lang.resource;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import leap.lang.Args;
import leap.lang.Classes;
import leap.lang.Collections2;
import leap.lang.Exceptions;
import leap.lang.New;
import leap.lang.exception.NestedIOException;
import leap.lang.net.Urls;
import leap.lang.path.AntPathMatcher;
import leap.lang.path.PathMatcher;

/**
 * spring style resource utils.
 */
public class Resources {
	
	static final PathMatcher matcher = new AntPathMatcher();
	
	static ResourceLoader  loader  = new DefaultResourceLoader();
	static ResourceScanner scanner = new DefaultResourceScanner(loader);
	
	private static final FileResource userDir; 
	
	static {
		userDir = Resources.createFileResource(System.getProperty("user.dir"));
	}
	
	public static PathMatcher getPathMatcher(){
		return matcher;
	}
	
	public static void setClassLoader(ClassLoader classLoader){
		Args.notNull(classLoader,"classLoader");
		Resources.loader  = new DefaultResourceLoader(classLoader);
		Resources.scanner = new DefaultResourceScanner(loader);
	}
	
	public static String getContent(String resource){
		Resource r = getResource(resource);
		if(null == r || !r.exists()){
			return null;
		}
		return r.getContent();
	}
	
	/**
	 * Returns the {@link FileResource} represents the directory of system property "user.dir".
	 */
	public static FileResource getUserDir() {
		return userDir;
	}
	
	/**
	 * @see {@link DefaultResourceLoader#getResource(String)}
	 */
	public static Resource getResource(String name){
		return loader.getResource(name);
	}
	
	/**
	 * Creates a new {@link FileResource}.
	 */
	public static FileResource createFileResource(File file) {
		Args.notNull(file,"file");
		return new SimpleFileResource(file);
	}
	
	/**
	 * Create a new {@link FileResource} of the given file path.
	 */
	public static FileResource createFileResource(String path){
		Args.notEmpty(path,"path");
		return new SimpleFileResource(new File(path));
	}
	
	/**
	 * get the resource in the given class's package.
	 */
	public static Resource getResource(Class<?> clazz,String resourceNameInPackage) throws NestedIOException {
		Args.notNull(clazz,"clazz");
		Args.notNull(resourceNameInPackage,"resource name must not be null");
		
		String resourceLocation = Urls.CLASSPATH_ONE_URL_PREFIX + Classes.getPackageResourcePath(clazz) + "/" + resourceNameInPackage;
		
        return loader.getResource(resourceLocation);
	}
	
	public static Resource getResource(Resource current,String path) throws NestedIOException{
		Args.notNull(current,"current resource");
		Args.notNull(path,"resource path");
		try {
	        if(path.indexOf(':') > 0 || path.startsWith("/")){
	        	return Resources.getResource(path);
	        }else{
	        	return current.createRelative(path);
	        }
        } catch (IOException e) {
        	throw new NestedIOException(e);
        }
	}
	
	/**
	 * scan all the resources match the given location pattern.
	 * 
	 * @see ResourceScanner#scan(String)
	 * 
	 * @throws NestedIOException if an {@link IOException} throwed by underlying scanner.
	 */
	public static ResourceSet scan(String locationPattern) throws NestedIOException {
		try {
	        return new SimpleResourceSet(scanner.scan(locationPattern));
        } catch (IOException e) {
        	throw new NestedIOException(e);
        }
	}
	
	public static ResourceSet scanPackage(String basePackage) {
		String location = "classpath*:" + basePackage.replace('.','/') + "/**/*";
		return scan(location);
	}
	
	/**
	 * scan all the resources match the given location patterns.
	 */
	public static ResourceSet scan(String... locationPatterns) throws NestedIOException {
		if(null == locationPatterns || locationPatterns.length == 0){
			return new SimpleResourceSet(new Resource[]{});
		}
        if(locationPatterns.length > 1){
        	final ExecutorService executorService = Executors.newFixedThreadPool(locationPatterns.length);
        	
        	final List<Resource>  result  = new CopyOnWriteArrayList<Resource>();
        	final List<Future<?>> futures = New.arrayList();
        	
        	for(int i=0;i<locationPatterns.length;i++){
        		final String locationPattern = locationPatterns[i];
        		futures.add(executorService.submit(new Runnable() {
					@Override
					public void run() {
						try {
			            	final DefaultResourceScanner resourceScanner = new DefaultResourceScanner(loader);
			            	resourceScanner.setExecutorService(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));							
                            Collections2.addAll(result, resourceScanner.scan(locationPattern));
                            resourceScanner.getExecutorService().shutdownNow();
                        } catch (IOException e) {
                        	throw new NestedIOException(e);
                        }
					}
				}));
        	}
        	
        	for(Future<?> future : futures){
        		try {
	                future.get();
                } catch (Exception e) {
                	Exceptions.uncheckAndThrow(e);
                }
        	}
        	
        	executorService.shutdownNow();
        	
        	return new SimpleResourceSet(result.toArray(new Resource[result.size()]));
        }else{
        	return scan(locationPatterns[0]);
        }
	}
	
	public static ResourceSet scan(Resource rootDirResource,String subPattern) throws NestedIOException{
		Args.notNull(rootDirResource,"rootDirResource");
		Args.notEmpty(subPattern, "subPattern");
		try {
	        return new SimpleResourceSet(scanner.scan(rootDirResource,subPattern));
        } catch (IOException e) {
        	throw Exceptions.wrap(e);
        }
	}
	
	public static String extractRootDirPath(String locationPattern){
		return scanner.extractRootDirPath(locationPattern);
	}
	
	protected Resources(){
		
	}
}