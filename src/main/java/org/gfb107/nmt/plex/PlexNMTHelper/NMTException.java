package org.gfb107.nmt.plex.PlexNMTHelper;

public class NMTException extends Exception {

	public static enum TYPE {
		NMT, SERVER, CONNECTION, INTERNAL
	}

	private TYPE type;
	private Exception e;

	public TYPE getType() {
		return type;
	}

	public Exception getException() {
		return e;
	}

	public NMTException( Exception e, TYPE type ) {
		this.type = type;
		this.e = e;
	}

}
