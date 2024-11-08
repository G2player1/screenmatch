package Enos.projetoSpring.screenmatch.service;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

public class ConsultChatGPT {

    public static String getTranslation(String text,String language){
        OpenAiService service = new OpenAiService("${CHATGPT_APIKEY}");
        CompletionRequest request = CompletionRequest.builder()
                .model("gpt-3.5-turbo-instruct")
                .prompt("translate to " + language + " the text: " + text)
                .maxTokens(1000)
                .temperature(0.7)
                .build();
        return service.createCompletion(request).getChoices().getFirst().getText();
    }
}
