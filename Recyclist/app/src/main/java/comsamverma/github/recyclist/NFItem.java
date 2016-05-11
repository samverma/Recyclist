package comsamverma.github.recyclist;


public class NFItem {
    private final String title;
    private final String link;

    public NFItem(String title, String link) {
        this.title = title;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }
}
