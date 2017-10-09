/*********************************************
 * @author -> Usama Sajid, Tausif Alam       *
 *                                           *
 * *******************************************/
package SongLib.Controller;

import java.util.Comparator;

public class songComparator implements Comparator<Song>{

	@Override
	public int compare(Song o1, Song o2) {
		if(!o1.getName().equals(o2.getName()))
		return o1.getName().toUpperCase().compareTo(o2.getName().toUpperCase());
		else
			return o1.getAuthor().toUpperCase().compareTo(o2.getAuthor().toUpperCase());	
		
	}

}