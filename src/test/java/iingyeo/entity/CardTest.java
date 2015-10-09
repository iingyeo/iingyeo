package iingyeo.entity;


import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by taemyung on 2015. 10. 9..
 */
@Slf4j
public class CardTest {

    @Test
    public void testFilterTags() throws Exception {

        // Given
        String tag1 = "tag1";
        String tag2 = "testTag";
        String tag3 = "잉여";

        StringBuilder sb = new StringBuilder();
        sb.append("test text").append("\n");
        sb.append("#").append(tag1).append(" 하하하하").append("\n");
        sb.append("#").append(tag2).append(" 하하하하").append("\n");
        sb.append("test text").append("\n");
        sb.append("iingyeo ").append("#").append(tag3).append(" 하하하하").append("\n");

        Card card = new Card();
        card.setText(sb.toString());

        // When
        Set<String> tags = card.filterTags();

        log.debug("tags : {}", tags);

        // Then
        assertThat(tags.size(), is(3));
        assertThat(tags.containsAll(Arrays.asList(tag1, tag2, tag3)), is(true));

    }
}
