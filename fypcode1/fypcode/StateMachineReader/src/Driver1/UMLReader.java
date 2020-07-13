/*This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package Driver1;

import java.util.Map;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.mapping.ecore2xml.Ecore2XMLPackage;
import org.eclipse.emf.mapping.ecore2xml.util.Ecore2XMLResource;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.resource.UML212UMLResource;
import org.eclipse.uml2.uml.resource.UML22UMLResource;
import org.eclipse.uml2.uml.resource.UMLResource;

public class UMLReader {
	
	protected static final ResourceSet RESOURCE_SET = new ResourceSetImpl();

	protected static void registerResourceFactories() {
		Map<String, Object> extensionFactoryMap = Resource.Factory.Registry.INSTANCE
				.getExtensionToFactoryMap();
		extensionFactoryMap.put(UMLResource.FILE_EXTENSION,
				UMLResource.Factory.INSTANCE);
		extensionFactoryMap.put(Ecore2XMLResource.FILE_EXTENSION,
				Ecore2XMLResource.Factory.INSTANCE);
		extensionFactoryMap.put(UML22UMLResource.FILE_EXTENSION,
				UML22UMLResource.Factory.INSTANCE);
		extensionFactoryMap.put(UMLResource.FILE_EXTENSION,
				UML22UMLResource.Factory.INSTANCE);
		extensionFactoryMap.put(UMLResource.FILE_EXTENSION,
				UML22UMLResource.Factory.INSTANCE);
		extensionFactoryMap.put(UMLResource.UML_PRIMITIVE_TYPES_LIBRARY_URI,
				UML22UMLResource.Factory.INSTANCE);
	}

	protected static void registerPackages(ResourceSet resourceSet) {
		Map<String, Object> packageRegistry = resourceSet.getPackageRegistry();
		packageRegistry.put(EcorePackage.eNS_URI, EcorePackage.eINSTANCE);
		packageRegistry.put(Ecore2XMLPackage.eNS_URI,
				Ecore2XMLPackage.eINSTANCE);
		packageRegistry.put(UMLPackage.eNS_URI, UMLPackage.eINSTANCE);
		packageRegistry.put(UML212UMLResource.UML_METAMODEL_NS_URI,
				UMLPackage.eINSTANCE);
		packageRegistry.put("http://www.eclipse.org/uml2/2.0.0/UML",
				UMLPackage.eINSTANCE);
	}
	
	public Model loadModel(String uri) {
		URI model = URI.createURI(uri);
		String transDir = "Addons/";
		registerPackages(RESOURCE_SET);
		registerResourceFactories();
		URIConverter.URI_MAP.put(
				URI.createURI("platform:/plugin/org.eclipse.uml2.uml/"),
				URI.createURI("jar:file:" + transDir
						+ "org.eclipse.uml2.uml_3.1.2.v201010261927.jar!/"));
		
		Resource resource = null;
		Model _model = null;
		try {
			resource = RESOURCE_SET.getResource(model, true);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		_model = (Model) EcoreUtil.getObjectByType(
				resource.getContents(), UMLPackage.Literals.MODEL);
		return _model;
	}
	
	public Profile loadProfile(String uri) {
		URI model = URI.createURI(uri);
		String transDir = "Addons/";
		registerPackages(RESOURCE_SET);
		registerResourceFactories();
		URIConverter.URI_MAP.put(
				URI.createURI("platform:/plugin/org.eclipse.uml2.uml/"),
				URI.createURI("jar:file:" + transDir
						+ "org.eclipse.uml2.uml_3.1.2.v201010261927.jar!/"));
		
		Resource resource = null;
		try {
			resource = RESOURCE_SET.getResource(model, true);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		Profile _profile = (Profile) EcoreUtil.getObjectByType(
				resource.getContents(), UMLPackage.Literals.PROFILE);
		return _profile;
	}
}
