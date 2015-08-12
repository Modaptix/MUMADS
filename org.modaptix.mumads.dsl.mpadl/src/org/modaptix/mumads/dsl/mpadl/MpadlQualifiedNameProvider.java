package org.modaptix.mumads.dsl.mpadl;

import org.eclipse.xtext.naming.DefaultDeclarativeQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.modaptix.mumads.dsl.mpadl.mpadl.Instruction;

public class MpadlQualifiedNameProvider extends	DefaultDeclarativeQualifiedNameProvider
{
	protected QualifiedName qualifiedName(Instruction instruction)
	{
		return QualifiedName.create(instruction.getName());
	}
	
	/*protected QualifiedName qualifiedName(RegisterPhysical register)
	{
		if (register.getIndex() != null)
			return QualifiedName.create(register.getName()+"["+register.getIndex().getName()+"]");
					
		return QualifiedName.create(register.getName());
	}

	protected QualifiedName qualifiedName(RegisterMmap register)
	{
		if (register.getIndex() != null)
			return QualifiedName.create(register.getName()+"["+register.getIndex().getName()+"]");
					
		return QualifiedName.create(register.getName());
	}*/
}
