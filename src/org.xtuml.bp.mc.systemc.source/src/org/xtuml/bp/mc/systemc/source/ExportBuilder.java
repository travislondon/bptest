package org.xtuml.bp.mc.systemc.source;

import org.xtuml.bp.mc.AbstractExportBuilder;

public class ExportBuilder extends AbstractExportBuilder {

	// The shared instance
	private static ExportBuilder singleton;

	public ExportBuilder() {
		super(Activator.getDefault(), MCNature.getDefault());
	}

	/**
	 * Returns the shared instance. Creates if it has not yet been created
	 * 
	 * @return the shared instance
	 */
	public static ExportBuilder getDefault() {
		if (ExportBuilder.singleton == null) {
			ExportBuilder.singleton = new ExportBuilder();
		}
		return ExportBuilder.singleton;
	}
}
