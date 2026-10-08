package com.inkwell.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AiService {

    private final ChatClient chatClient;

    public AiService(ChatClient.Builder builder){
        this.chatClient= builder.build();
    }

    public String summarizePost(String content){
        String prompt = "Summarize this blog post in 2-3 sentences: "+content;

        return chatClient.prompt(prompt).call().content();

    }
}
