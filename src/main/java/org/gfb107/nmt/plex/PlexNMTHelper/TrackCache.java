package org.gfb107.nmt.plex.PlexNMTHelper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import nu.xom.ParsingException;
import nu.xom.ValidityException;

import org.apache.http.client.ClientProtocolException;

public class TrackCache {
	private static Logger logger = Logger.getLogger( TrackCache.class.getName() );
	private Map< String, Track > map;
	private PlexNMTHelper helper;
	private String unknownPath;

	public TrackCache( PlexNMTHelper helper ) throws ClientProtocolException, ValidityException, IllegalStateException, IOException, ParsingException {
		this.helper = helper;

		load();
	}

	public void clear() {
		helper = null;
		map.clear();
	}

	public void load() throws ClientProtocolException, ValidityException, IllegalStateException, IOException, ParsingException {
		logger.info( "Building track cache" );

		List< Track > tracks = helper.getServer().getKnownTracks();

		int size = tracks.size() * 4 / 3;

		Map< String, Track > tempMap = new HashMap< String, Track >( size );

		for ( Track track : tracks ) {
			tempMap.put( track.getPlayFile(), track );
		}

		logger.info( "Found " + tracks.size() + " tracks" );
		map = tempMap;
	}

	public void add( Track track ) {
		map.put( track.getFile(), track );
	}

	public Track get( String path ) throws ClientProtocolException, ValidityException, IllegalStateException, IOException, ParsingException {
		Track track = map.get( path );

		if ( track == null ) {
			if ( !path.equals( unknownPath ) ) {
				load();
				track = map.get( path );
				if ( track == null ) {
					unknownPath = path;
				} else {
					unknownPath = null;
				}
			}
		} else {
			unknownPath = null;
		}

		return track;
	}
}
