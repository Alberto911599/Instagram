import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

@ParseClassName("Post")
class Post extends ParseObject {

    public ParseFile getMedia() {
        return getParseFile("media");
    }

    public void setMedia(ParseFile parseFile) {
        put("media", parseFile);
    }
}