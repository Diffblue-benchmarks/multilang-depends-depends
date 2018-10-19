package depends.entity.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import depends.entity.ContainerEntity;
import depends.entity.Entity;
import depends.entity.TypeInfer;

public class FileEntity extends ContainerEntity{
	HashMap<String,String> importedNames = new HashMap<>();
	private ArrayList<Entity> resolvedImportedEntities = new ArrayList<>();

	public List<Entity> getResolvedImportedEntities() {
		return resolvedImportedEntities;
	}
	public FileEntity(String fullName, int fileId) {
		super(fullName, null,fileId);
		setQualifiedName(fullName);
	}
	public void addImport(String importedTypeOrPackage) {
		String lastName = importedTypeOrPackage;
        if (lastName.indexOf(".") > 0)
        	lastName = lastName.substring(lastName.lastIndexOf(".")+1);
        importedNames.put(lastName, importedTypeOrPackage);
	}
	public String getImport(String lastName) {
		return importedNames.get(lastName);
	}
	@Override
	public String getQualifiedName() {
		if (this.getParent()==null)
			return "";
		if (this.getParent() instanceof PackageEntity)
			return this.getParent().getQualifiedName();
		else
			return super.getQualifiedName();
	}
	public Collection<String> imports() {
		return importedNames.values();
	}
	@Override
	public void inferLocalLevelTypes(TypeInfer typeInferer) {
		for (String item:importedNames.values()) {
			if (typeInferer.isBuiltInTypePrefix(item)) continue;
			List<Entity> importedEntities = typeInferer.resolveImportEntity(item);
			this.resolvedImportedEntities.addAll(importedEntities);
		}		
		super.inferLocalLevelTypes(typeInferer);
	}
}