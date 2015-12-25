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
package leap.htpl.ast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import leap.core.web.assets.Asset;
import leap.core.web.assets.AssetBundleBuilder;
import leap.core.web.assets.AssetManager;
import leap.core.web.assets.AssetSource;
import leap.htpl.HtplContext;
import leap.htpl.HtplDocument;
import leap.htpl.HtplEngine;
import leap.htpl.HtplRenderable;
import leap.htpl.HtplTemplate;
import leap.htpl.HtplWriter;
import leap.lang.Strings;

public class Bundle extends NodeContainer implements HtplRenderable {
	
    private String  path;
	private boolean compiling;
	private boolean assetContextNotFound;
	
    public Bundle(String path) {
        super();
        this.path = path;
    }
	
    public Bundle(String path, List<Node> childNodes) {
        super(childNodes);
        this.path = path;
    }

    public boolean isCompiled() {
		return null != compiled;
	}
	
	public boolean isCompiling() {
		return compiling;
	}
	
	
    @Override
    protected void doWriteTemplate(Appendable out) throws IOException {
        out.append("<!--#bundle");
        if(!Strings.isEmpty(path)){
            out.append(" ").append(path);
        }
        out.append("-->");
        super.doWriteTemplate(out);
        out.append("<!--#endbundle-->");
    }

    @Override
    protected Node doProcess(HtplEngine engine, HtplDocument doc, ProcessCallback callback) throws Throwable {
        AssetSource assetSource = engine.getAssetSource();
        if(null == assetSource) {
            assetContextNotFound = true;
            return super.doProcess(engine, doc, callback);
        }
        
        AssetBundleBuilder bundle = new AssetBundleBuilder();
        
        if(null != childNodes) {
            List<Node> newChildNodes = new ArrayList<Node>();
            
            for(Node node : childNodes) {
                if(node.isElement()) {
                    Element e = (Element)node;
                    
                    if(e.isElement("link")) {
                        String type = e.getAttributeValue("type");
                        String rel  = e.getAttributeValue("rel");
                        if(type.equalsIgnoreCase("text/css") && "stylesheet".equalsIgnoreCase(rel)) {
                            if(!bundle.hasType()) {
                                bundle.cssType();
                            }
                            
                            if(bundle.isCssType()) {
                                String src = e.getAttributeValue("href");
                                Asset asset = resolveAsset(engine, doc, src);
                                if(null != asset) {
                                    bundle.addAsset(asset);
                                    continue;
                                }
                            }
                        }
                    } if(e.isElement("script")) {
                        String type = e.getAttributeValue("type");
                        if(type.equalsIgnoreCase("text/javascript")) {
                            if(!bundle.hasType()) {
                                bundle.jsType();
                            }
                            
                            if(bundle.isJsType()) {
                                String src = e.getAttributeValue("src");
                                Asset asset = resolveAsset(engine, doc, src);
                                if(null != asset) {
                                    bundle.addAsset(asset);
                                    continue;
                                }
                            }
                        }
                    }
                }

                if(node instanceof Text && ((Text) node).isBlank()) {
                    continue;
                }

                newChildNodes.add(node);
            }
            
            this.childNodes = newChildNodes;
        }
        
        if(bundle.hasAssets()) {
            bundle.setPath(resolveBundlePath(engine, doc));
            
            AssetManager manager = engine.getAssetManager();
            Asset asset = manager.createBundleAssetOverrided(bundle.build());
            
            Element e;
            if(bundle.isCssType()) {
                e = new Element("", "link");
                e.setAttribute("type", "text/css");
                e.setAttribute("rel", "stylesheet");
                e.setAttribute("href",manager.prefixWithAssetsPath(asset.getPath()));
                e.setSelfClosing(true);
            }else{
                e = new Element("", "script");
                e.setAttribute("type", "text/javascript");
                e.setAttribute("src", manager.prefixWithAssetsPath(asset.getPath()));
            }
            
            childNodes.add(0,e);
        }

        return super.doProcess(engine, doc, callback);
    }
    
    protected String resolveBundlePath(HtplEngine engine, HtplDocument doc) {
        return engine.getAssetManager().prefixWithoutAssetsPath(path);
    }
    
    protected Asset resolveAsset(HtplEngine engine, HtplDocument doc, String path) throws Throwable {
        return engine.getAssetSource().getAsset(engine.getAssetManager().prefixWithoutAssetsPath(path), null);
    }
    
	@Override
    public void render(HtplTemplate tpl, HtplContext context, HtplWriter writer) throws IOException, IllegalStateException {
        if(assetContextNotFound){
            writer.append("<!--bundle failed, assets context not found, check is the web module exists?-->");
        }
	    super.render(tpl, context, writer);
    }

	@Override
	protected Node doDeepClone(Node parent) {
		return new Bundle(path, deepCloneChildNodes());
	}
}