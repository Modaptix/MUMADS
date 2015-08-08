package org.modaptix.mumads.dsl.mpadl.util.interfaces;

public interface IMpadlLocator
{
	void iterateFileResourcesByProject(final String name, final IMpadlLocatorVisitor visitor);
}
