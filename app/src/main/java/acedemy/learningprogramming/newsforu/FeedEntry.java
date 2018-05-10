package acedemy.learningprogramming.newsforu;

/**
 * Created by win on 09-05-2018.
 */

public class FeedEntry {
    private String name;
    private String artist;
    private String releaseDate;
    private String summary;
    private String url;



    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary)

    {
        this.summary = summary;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

//    @Override
//    public String toString() {
//        return "name='" + name + '\n' +
//                ", artist='" + artist + '\n' +
//                ", releaseDate='" + releaseDate + '\n'
//                ;
//    }
}
