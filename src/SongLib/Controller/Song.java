/**********************************************
 * @author -> Usama Sajid, Tausif Alam        *
 *                                            * 
 * ************* Song Data ********************
 * Song object, getters and setters           *
 *                                            *
 **********************************************/
package SongLib.Controller;

public class Song {
	
	private String name, author, album;
	private int year;
	
	
	public Song(String name, String author)
	{
		this.setName(name);
		this.setAuthor(author);
	}
	
	public Song(String name, String author, String album)
	{
		this.setName(name);
		this.setAuthor(author);
		this.setAlbum(album);
	}
	
	public Song(String name, String author, int year)
	{
		this.setName(name);
		this.setAuthor(author);
		this.setYear(year);
	}
	
	public Song(String name, String author, int year, String album)
	{
		this.setName(name);
		this.setAuthor(author);
		this.setYear(year);
		this.setAlbum(album);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
	public String ToString()
	{
		return name + "  " + author + "  " + album + "  " + year;
		
	}
	

}