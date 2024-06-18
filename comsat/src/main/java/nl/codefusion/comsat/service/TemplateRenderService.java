package nl.codefusion.comsat.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.codefusion.comsat.models.ContactModel;
import nl.codefusion.comsat.models.TemplateModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TemplateRenderService {
    private final String usernameMatcher = "<span data-placeholdertype=\"username\" class=\"placeholder\">username</span>";
    private final String fullnameMatcher = "<span data-placeholdertype=\"fullname\" class=\"placeholder\">fullname</span>";

    public String renderTemplate(ContactModel contact, TemplateModel template) throws JsonProcessingException {
        List<BodyEntry> entries = List.of(new ObjectMapper().readValue(template.getBody(), BodyEntry[].class));

        BodyEntry selectedEntry = entries.getFirst();

        for (BodyEntry entry : entries) {
            if (entry.getLanguage().equalsIgnoreCase(contact.getLanguage())) {
                selectedEntry = entry;
            }
        }
        
        String parsedBody = selectedEntry.getBody()
                .replace(usernameMatcher, contact.getNickname())
                .replace(fullnameMatcher, contact.getFirstName())
                .replace("<p>", "")
                .replace("</p>", "");


        return parsedBody;
    }

}

@Data
@NoArgsConstructor
class BodyEntry {
    private String language;
    private String body;
}
