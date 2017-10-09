/***************************************************
 * @author -> Usama Sajid, Tausif Alam
 * 
 * ************* Song Player ***********************
 * 
 * HashMap // stores song data 
 * class has backend data structures implementation
 * add, delete, edit methods to store Song in to map
 *  
 ***************************************************/
package SongLib.Controller;
import java.util.HashMap;
import java.util.Map;

public class SongPlayer {
	
	Map<String, Song> map;
	Song temp, newlyUpdated, updateTemp;
	
	
	public SongPlayer()
	{
		map = new HashMap<String, Song>();
	}
	
	/*
	 * Adds new Song to map
	 * @param - Song song
	 * @return - boolean if song was added or not
	 *  used to pass alert message
	 */
	
	public boolean addSong(Song song)
	{
		temp = song;
		if(song.getName() == null || song.getName() == "" || song.getAuthor() == null || song.getAuthor() == "" )
		{
			
			return false;
		}
		Song checkSong = map.get(song.getName()+song.getAuthor());
		//song not in map
		if(checkSong == null)
		{
			map.put(song.getName().trim().toUpperCase()+song.getAuthor().trim().toUpperCase(), song);
			return true;
		}
		else
		{
			//song already in map, can't add again
			if(song.getAuthor().equals(checkSong.getAuthor()))
			{
				return false; 
			}
			map.put(song.getName().trim().toUpperCase()+song.getAuthor().trim().toUpperCase(), song);
			return true;
			
		}
	}
	
	public boolean deleteSong(Song song)
	{
		temp = song;
		Song delete = map.get(song.getName()+song.getAuthor());
		if(delete == null) return false;
		else
		{
			map.remove(song.getName()+song.getAuthor());
		}
		return true;
	}
	
	
	public Song update(Song song,String name, String author, int year, String album)
	{
		updateTemp = song;
		Song toUpdate = map.get(song.getName()+song.getAuthor());
		if(toUpdate!=null)
		{
			if(deleteSong(song))
			{
				if(name!=null && !name.equals(""))
				toUpdate.setName(name.trim().toUpperCase());
				if(author!=null && !author.equals(""))
				toUpdate.setAuthor(author.trim().toUpperCase());
				if(year!=0)
				toUpdate.setYear(year);
				if(album!=null && !album.equals(""))
				toUpdate.setAlbum(album.trim().toUpperCase());
				if(map.get(toUpdate.getName()+toUpdate.getAuthor()) == null )
				{
					newlyUpdated = toUpdate;
					map.put(toUpdate.getName().trim().toUpperCase()+toUpdate.getAuthor().trim().toUpperCase(), toUpdate);
					map.remove(song);
					return toUpdate;
				}
				else return null;
			}
			else return null;
		}
		return null;
	}

}