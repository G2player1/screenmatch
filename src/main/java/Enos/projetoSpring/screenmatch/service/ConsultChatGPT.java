package Enos.projetoSpring.screenmatch.service;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

public class ConsultChatGPT {

    public static String getTranslation(String text,String language){
        OpenAiService service = new OpenAiService("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");
        CompletionRequest request = CompletionRequest.builder()
                .model("gpt-3.5-turbo-instruct")
                .prompt("translate to " + language + " the text: " + text)
                .maxTokens(1000)
                .temperature(0.7)
                .build();
        return service.createCompletion(request).getChoices().getFirst().getText();
    }
}
